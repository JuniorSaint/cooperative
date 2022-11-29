package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.CooperativeResponse;
import br.com.cooperative.models.request.CooperativeRequest;
import br.com.cooperative.services.CooperativeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "*", maxAge = 60 * 60)
@RequestMapping("/v1/cooperatives")
@Tag(name = "Cooperative", description = "Manager cooperative")
public class CooperativesController {
    @Autowired
    private CooperativeService service;

    @PostMapping
    public ResponseEntity<CooperativeResponse> save(@RequestBody @Valid CooperativeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveUpdate(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CooperativeResponse> update(@RequestBody @Valid CooperativeRequest request, @PathVariable(value = "id") UUID id) {
        request.setId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.saveUpdate(request));
    }

    @GetMapping
    public ResponseEntity<List<CooperativeResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CooperativeResponse> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.delete(id));
    }

    @GetMapping("/seek")
    public ResponseEntity<Page<CooperativeResponse>> findAllUserWithSearch(@RequestParam(value = "search", defaultValue = "") String search, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllWithPageAndSearch(search, pageable));
    }
}