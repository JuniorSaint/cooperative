package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.repositories.CooperativeRepository;
import br.com.cooperative.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private ModelMapper mapper;
    @Autowired
    private UsefulMethods usefulMethods;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse save(User entity, MultipartFile file) throws IOException {
        if (repository.existsByEmail(entity.getEmail())) {
            throw new BadRequestException(
                    "Already exist user with this email: " + entity.getEmail() + ", try with another one");
        }
        if (entity.getCooperative() == null) {
            throw new BadRequestException("Cooperative not informed, it's not allowed user without a cooperative");
        }
        if(!file.isEmpty()){
            fileStorageService.storageFile(file);
            entity.setImageFileName(file.getOriginalFilename());
        }
        verifyIfCooperativeExist(entity.getCooperative().getId());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return mapper.map(repository.save(entity), UserResponse.class);
    }

    @Transactional
    public UserResponse update(User entity, MultipartFile file) throws IOException {
        Optional<User> searchForUser = repository.findByEmail(entity.getEmail());
        if (searchForUser.isEmpty()) {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + " email: " + entity.getEmail());
        }
        if (entity.getCooperative() == null) {
            throw new BadRequestException("Cooperative not informed, it's not allowed user without a cooperative");
        }
        if(!file.isEmpty()){
            fileStorageService.storageFile(file);
            entity.setImageFileName(file.getOriginalFilename());
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
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User" + CP.NOT_FOUND + " id:" + id));
    }

    public Page<UserResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        return usefulMethods.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), pageable), UserResponse.class);
    }

    public List<UserResponse> findAllListed() {
        return usefulMethods.mapListIntoDtoList(repository.findAll(), UserResponse.class);
    }

    private boolean verifyIfCooperativeExist(UUID id) {
        if (cooperativeRepository.findById(id).isPresent()) {
            return true;
        }
        throw new EntityNotFoundException("Cooperative" + CP.NOT_FOUND + " id: " + id);
    }
}
