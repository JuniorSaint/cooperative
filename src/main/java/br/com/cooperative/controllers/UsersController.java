package br.com.cooperative.controllers;

import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.UserRequest;
import br.com.cooperative.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 60 * 60)
@RequestMapping("/v1/users")
@AllArgsConstructor
@Tag(name = "Users", description = "Manager users")
public class UsersController {
    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllListed());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.map(service.findById(id), UserResponse.class));
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserRequest request, @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(mapper.map(request, User.class), file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.delete(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest request, @PathVariable(value = "id") UUID id,
                                               @RequestParam("file") MultipartFile file ) throws IOException {
        if(id == null){
            throw new BadRequestException("In update id is mandatory");
        }else{
        request.setId(id);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(mapper.map(request, User.class), file));
    }

//    @PutMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(service.changePassword(request));
//    }

    @GetMapping("/seek")
    public ResponseEntity<Page<UserResponse>> findAllUserWithSearch(@RequestParam(value = "search", defaultValue = "") String search, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllWithPageAndSearch(search, pageable));
    }
}
