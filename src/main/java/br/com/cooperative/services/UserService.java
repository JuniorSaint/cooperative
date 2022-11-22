package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.entities.Role;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.ChangePasswordRequest;
import br.com.cooperative.models.request.UserRequest;
import br.com.cooperative.repositories.CooperativeRepository;
import br.com.cooperative.repositories.RoleRepository;
import br.com.cooperative.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private RoleRepository permissionRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Utils utils;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User " + CP.NOT_FOUND + " email: " + email);
        }
        return mapper.map(user.get(), UserDetails.class);
    }


    @Transactional
    public UserResponse save(UserRequest request) {
        Optional<User> searchForUser = repository.findByEmail(request.getEmail());
        if (searchForUser.isPresent()) {
            throw new BadRequestException(
                    "Already exist user with this email: " + request.getEmail() + ", try with another one");
        }
        if (request.getCooperative() == null) {
            throw new BadRequestException("Cooperative not informed, it's not allowed user without a cooperative");
        }
        Optional<Cooperative> cooperative = cooperativeRepository.findById(request.getCooperative().getId());
        if (cooperative.isEmpty()) {
            throw new EntityNotFoundException("Cooperative" + CP.NOT_FOUND + " id: " + request.getCooperative().getId());
        }
        User user = mapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        UserResponse result =  mapper.map(repository.save(user), UserResponse.class);
        return result;
    }

    @Transactional
    public UserResponse update(UserRequest request) {
        Optional<User> searchForUser = repository.findByEmail(request.getEmail());
        if (searchForUser.isEmpty()) {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + " email: " + request.getEmail());
        }
        if (request.getCooperative() == null) {
            throw new BadRequestException("Cooperative not informed, it's not allowed user without a cooperative");
        }
        Optional<Cooperative> cooperative = cooperativeRepository.findById(request.getCooperative().getId());
        if (cooperative.isEmpty()) {
            throw new EntityNotFoundException("Cooperative" + CP.NOT_FOUND + " id: " + request.getCooperative().getId());
        }
        User userEntity = new User();
        mapper.map(request, userEntity);
        userEntity.setPassword(searchForUser.get().getPassword());
        UserResponse result = mapper.map(repository.save(userEntity), UserResponse.class);
        return result;
    }

    public String changePassword(ChangePasswordRequest request) {
        Optional<User> user = repository.findById(request.getId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + "id:" + request.getId());
        }
        user.get().setPassword(passwordEncoder.encode(request.getPassword()));
        User userUpdate = repository.save(user.get());
        return "The password was changed with success of the user: " + userUpdate.getEmail();
    }

    @Transactional
    public String delete(UUID id) {
            findById(id);
            repository.deleteById(id);
            return "User" + DELETE_MESSAGE;
    }

    public UserResponse findById(UUID id) {
        Optional<User> response = repository.findById(id);
        if (response.isEmpty()) {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + " id:" + id);
        }
        UserResponse result = mapper.map(response.get(), UserResponse.class);
        return result;
    }

    public Page<UserResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        List<Role> role = (search.isBlank()) ? null : permissionRepository.findAllByRole(search.trim());
       return  utils.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), role, pageable), UserResponse.class);
    }

    public List<UserResponse> findAllListed() {
        List<User> response = repository.findAll();
        return   utils.mapListIntoDtoList(response, UserResponse.class);
    }

}
