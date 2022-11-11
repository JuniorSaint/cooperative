package br.com.cooperative.controllers;

import javax.validation.Valid;

import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.request.AgencyBankRequest;
import br.com.cooperative.services.AgencyBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 60 * 60)
@RequestMapping("/v1/agency-bank")
@Tag(name = "Agency Bank", description = "Manager Agency Bank")
public class AgenciesBanksController {
    @Autowired
    private AgencyBankService service;

    @PostMapping
    public ResponseEntity<AgencyBankResponse> save(@RequestBody @Valid AgencyBankRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveUpdate(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyBankResponse> update(@RequestBody @Valid AgencyBankRequest request, @PathVariable(value = "id") Long id) {
        request.setId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.saveUpdate(request));
    }

    @GetMapping
    public ResponseEntity<List<AgencyBankResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyBankResponse> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.delete(id));
    }

    @GetMapping("/seek")
    public ResponseEntity<Page<AgencyBankResponse>> findAllUserWithSearch(@RequestParam(value = "search", defaultValue = "") String search, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllWithPageAndSearch(search, pageable));
    }
}
