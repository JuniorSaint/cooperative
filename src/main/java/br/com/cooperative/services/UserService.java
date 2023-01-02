package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Role;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.repositories.CooperativeRepository;
import br.com.cooperative.repositories.RoleRepository;
import br.com.cooperative.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;

@Service
public class UserService   {
    @Autowired
    private UserRepository repository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private RoleRepository permissionRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UsefulMethods utils;
//    @Autowired
//    public PasswordEncoder passwordEncoder;

//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String email) {
//        Optional<User> user = repository.findByEmail(email);
//        if (user.isEmpty()) {
//            throw new EntityNotFoundException("User " + CP.NOT_FOUND + " email: " + email);
//        }
//        return mapper.map(user.get(), UserDetails.class);
//    }


    @Transactional
    public UserResponse save(User entity) {
        if (repository.existsByEmail(entity.getEmail())) {
            throw new BadRequestException(
                    "Already exist user with this email: " + entity.getEmail() + ", try with another one");
        }
        if (entity.getCooperative() == null) {
            throw new BadRequestException("Cooperative not informed, it's not allowed user without a cooperative");
        }
        verifyIfCooperativeExist(entity.getCooperative().getId());
//        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return mapper.map(repository.save(entity), UserResponse.class);
    }

    @Transactional
    public UserResponse update(User entity) {
        Optional<User> searchForUser = repository.findByEmail(entity.getEmail());
        if (searchForUser.isEmpty()) {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + " email: " + entity.getEmail());
        }
        if (entity.getCooperative() == null) {
            throw new BadRequestException("Cooperative not informed, it's not allowed user without a cooperative");
        }
        verifyIfCooperativeExist(entity.getCooperative().getId());
        entity.setPassword(searchForUser.get().getPassword());
        return mapper.map(repository.save(entity), UserResponse.class);
    }

//    public String changePassword(ChangePasswordRequest request) {
//        User user = findById(request.getId());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        repository.save(user);
//        return "The password was changed with success of the user: " + user.getEmail();
//    }

    @Transactional
    public String delete(UUID id) {
        findById(id);
        repository.deleteById(id);
        return "User" + DELETE_MESSAGE;
    }

    public User findById(UUID id) {
        Optional<User> response = repository.findById(id);
        if (response.isEmpty()) {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + " id:" + id);
        }
        return response.get();
    }

    public Page<UserResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        List<Role> role = (search.isBlank()) ? null : permissionRepository.findAllByRole(search.trim());
        return utils.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), role, pageable), UserResponse.class);
    }

    public List<UserResponse> findAllListed() {
        return utils.mapListIntoDtoList(repository.findAll(), UserResponse.class);
    }

    private boolean verifyIfCooperativeExist(UUID id) {
        if (cooperativeRepository.existsById(id)) {
            throw new EntityNotFoundException("Cooperative" + CP.NOT_FOUND + " id: " + id);
        }
        return true;
    }
}
