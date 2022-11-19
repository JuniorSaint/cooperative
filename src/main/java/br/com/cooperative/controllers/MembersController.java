package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.MemberResponse;
import br.com.cooperative.models.request.MemberRequest;
import br.com.cooperative.services.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 60 * 60)
@RequestMapping("/v1/member")
@Tag(name = "Member", description = "Manager member")
public class MembersController {
    @Autowired
    private MemberService service;

    @PostMapping
    public ResponseEntity<MemberResponse> save(@RequestBody @Valid MemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveUpdate(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@RequestBody @Valid MemberRequest request, @PathVariable(value = "id") UUID id) {
        request.setId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.saveUpdate(request));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(service.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.delete(id));
    }

}