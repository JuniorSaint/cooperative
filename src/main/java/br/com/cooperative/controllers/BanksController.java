package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.BankResponse;
import br.com.cooperative.models.request.BankRequest;
import br.com.cooperative.services.BankService;
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
@RequestMapping("/v1/banks")
@Tag(name = "Bank", description = "Manager Bank")
public class BanksController {
    @Autowired
    private BankService service;

    @PostMapping
    public ResponseEntity<BankResponse> save(@RequestBody @Valid BankRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveUpdate(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponse> update(@RequestBody @Valid BankRequest request, @PathVariable(value = "id") UUID id) {
        request.setId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.saveUpdate(request));
    }

    @GetMapping
    public ResponseEntity<List<BankResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankResponse> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) throws Exception {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.delete(id));
    }
}
