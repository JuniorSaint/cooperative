package br.com.cooperative.controllers;

import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.models.Response.BankResponse;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.models.request.BankRequest;
import br.com.cooperative.services.BankService;
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
@RequestMapping("/v1/banks")
@Tag(name = "Bank", description = "Manager Bank")
public class BanksController {
    @Autowired
    private BankService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<BankResponse> save(@RequestBody @Valid BankRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveUpdate(mapper.map(request, Bank.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponse> update(@RequestBody @Valid BankRequest request, @PathVariable(value = "id") UUID id) {
        if (request.getId().equals(null)) {
            throw new BadRequestException("Id in update is mandatory");
        } else {
            request.setId(id);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.saveUpdate(mapper.map(request, Bank.class)));
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
