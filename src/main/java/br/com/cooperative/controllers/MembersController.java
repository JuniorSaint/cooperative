package br.com.cooperative.controllers;

import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.models.Response.MemberResponse;
import br.com.cooperative.models.entities.Member;
import br.com.cooperative.models.request.MemberRequest;
import br.com.cooperative.services.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 60 * 60)
@RequestMapping("/v1/members")
@Tag(name = "Member", description = "Manager member")
public class MembersController {
    @Autowired
    private MemberService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<MemberResponse> save(@RequestBody @Valid MemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveUpdate(mapper.map(request, Member.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@RequestBody @Valid MemberRequest request, @PathVariable(value = "id") UUID id) {
        if(id.equals(null)){
            throw new BadRequestException("In update id is mandatory");
        }else {
        request.setId(id);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.saveUpdate(mapper.map(request, Member.class)));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.delete(id));
    }

}