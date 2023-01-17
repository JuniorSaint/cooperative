package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.ChangePasswordRequest;
import br.com.cooperative.repositories.CooperativeRepository;
import br.com.cooperative.repositories.RoleRepository;
import br.com.cooperative.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UsefulMethods usefulMethods;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse save(User entity) {
        entity.setEmail(entity.getEmail().toLowerCase());
        if (repository.findByEmail(entity.getEmail()).isPresent()) {
            throw new BadRequestException("Already exist user with this email: " + entity.getEmail() + ", try with another one");
        }
        ifCooperativeExist(entity.getCooperative());
        entity.setCpf(usefulMethods.justNumberAllowed(entity.getCpf()));
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return mapper.map(repository.save(entity), UserResponse.class);
    }

    @Transactional
    public UserResponse update(User entity) {
        ifCooperativeExist(entity.getCooperative());
        entity.setEmail(entity.getEmail().toLowerCase());
        return repository.findByEmail(entity.getEmail())
                .map(result -> {
                    entity.setCpf(usefulMethods.justNumberAllowed(entity.getCpf()));
                    entity.setPassword(result.getPassword());
                    return mapper.map(repository.save(entity), UserResponse.class);
                })
                .orElseThrow(
                        () -> new EntityNotFoundException("User" + CP.NOT_FOUND + " id: " + entity.getEmail()));
    }

    public String changePassword(ChangePasswordRequest request) {
        return repository.findById(request.getId())
                .map(result -> {
                    result.setPassword(passwordEncoder.encode(request.getPassword()));
                    repository.save(result);
                    return "The password was changed with success of the user: " + result.getEmail();
                }).orElseThrow(
                        () -> new EntityNotFoundException("User" + CP.NOT_FOUND + " id: " + request.getId()));
    }

    @Transactional
    public String delete(UUID id) {
        return repository.findById(id)
                .map(result -> {
                    repository.deleteById(id);
                    return "User" + DELETE_MESSAGE;
                })
                .orElseThrow(() -> new EntityNotFoundException("User" + NOT_FOUND + "id: " + id));
    }

    public UserResponse findById(UUID id) {
        return repository.findById(id)
                .map(result -> mapper.map(result, UserResponse.class))
                .orElseThrow(() -> new EntityNotFoundException("User" + CP.NOT_FOUND + " id:" + id));
    }

//    public Page<UserResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
//        return usefulMethods.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), pageable), UserResponse.class);
//    }

    public List<UserResponse> findAllListed() {
        return usefulMethods.mapListIntoDtoList(repository.findAll(), UserResponse.class);
    }

    private void ifCooperativeExist(Cooperative cooperative) {
        if (cooperative == null) {
            throw new BadRequestException("Cooperative not informed, it's not allowed user without a cooperative");
        }
        cooperativeRepository.findById(cooperative.getId()).orElseThrow(
                () -> new EntityNotFoundException("Cooperative" + CP.NOT_FOUND + " id: " + cooperative.getId()));
    }
}
