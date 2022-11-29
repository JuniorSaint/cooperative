package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
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
        if (repository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(
                    "Already exist user with this email: " + request.getEmail() + ", try with another one");
        }
        if (request.getCooperative() == null) {
            throw new BadRequestException("Cooperative not informed, it's not allowed user without a cooperative");
        }
        verifyIfCooperativeExist(request.getCooperative().getId());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return mapper.map(repository.save(mapper.map(request, User.class)), UserResponse.class);
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
        verifyIfCooperativeExist(request.getCooperative().getId());
        User userEntity = new User();
        mapper.map(request, userEntity);
        userEntity.setPassword(searchForUser.get().getPassword());
        return mapper.map(repository.save(userEntity), UserResponse.class);
    }

    public String changePassword(ChangePasswordRequest request) {
        User user = mapper.map(findById(request.getId()), User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(user);
        return "The password was changed with success of the user: " + user.getEmail();
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
        return mapper.map(response.get(), UserResponse.class);
    }

    public Page<UserResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        List<Role> role = (search.isBlank()) ? null : permissionRepository.findAllByRole(search.trim());
        return utils.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), role, pageable), UserResponse.class);
    }

    public List<UserResponse> findAllListed() {
        List<User> response = repository.findAll();
        return utils.mapListIntoDtoList(response, UserResponse.class);
    }

    private boolean verifyIfCooperativeExist(UUID id) {
        if (cooperativeRepository.existsById(id)) {
            throw new EntityNotFoundException("Cooperative" + CP.NOT_FOUND + " id: " + id);
        }
        return true;
    }
}
