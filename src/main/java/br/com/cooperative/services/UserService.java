package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.DataBaseException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.entities.Permission;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.ChangePasswordRequest;
import br.com.cooperative.models.request.UserRequest;
import br.com.cooperative.repositories.CooperativeRepository;
import br.com.cooperative.repositories.PermissionRepository;
import br.com.cooperative.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Utils utils;


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
        Optional<Cooperative> cooperative = cooperativeRepository.findById(request.getCooperative().getId());
        if (cooperative.isEmpty()) {
            throw new EntityNotFoundException("Cooperative" + CP.NOT_FOUND + " id: " + request.getCooperative().getId());
        }
        User userEntity = new User();
        mapper.map(request, userEntity);
        userEntity.setPassword(searchForUser.get().getPassword());
        return mapper.map(repository.save(userEntity), UserResponse.class);
    }

    public String changePassword(ChangePasswordRequest request) {
        Optional<User> user = repository.findById(request.getId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + "id:" + request.getId());
        }
        User userUpdate = repository.save(user.get());
        return "The password was changed with success of the user: " + userUpdate.getEmail();
    }

    @Transactional
    public String delete(Long id) {
        try {
            findById(id);
            repository.deleteById(id);
            return "User" + DELETE_MESSAGE;
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    public UserResponse findById(Long id) {
        Optional<User> response = repository.findById(id);
        if (response.isEmpty()) {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + " id:" + id);
        }
        return mapper.map(response.get(), UserResponse.class);
    }

    public Page<UserResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        List<Permission> permissions = (search.isBlank()) ? null : permissionRepository.findAllByDescription(search.trim());
        return utils.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), permissions, pageable), UserResponse.class);
    }

    public List<UserResponse> findAllListed() {
        List<User> response = repository.findAll();
        return utils.mapListIntoDtoList(response, UserResponse.class);
    }

}
