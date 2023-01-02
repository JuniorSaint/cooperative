package br.com.cooperative.controllers;

import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.request.AgencyBankRequest;
import br.com.cooperative.services.AgencyBankService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 60 * 60)
@RequestMapping("/v1/agencies-banks")
@Tag(name = "Agency Bank", description = "Manager Agency Bank")
public class AgenciesBanksController {
    @Autowired
    private AgencyBankService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<AgencyBankResponse> save(@RequestBody @Valid AgencyBankRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveUpdate(mapper.map(request, AgencyBank.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyBankResponse> update(@RequestBody @Valid AgencyBankRequest request, @PathVariable(value = "id") UUID id) {
        if(id.equals(null)){
            throw new BadRequestException("Id in update is madatory");
        }else {
        request.setId(id);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.saveUpdate(mapper.map(request, AgencyBank.class)));
    }

    @GetMapping
    public ResponseEntity<List<AgencyBankResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyBankResponse> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.delete(id));
    }

    @GetMapping("/seek")
    public ResponseEntity<Page<AgencyBankResponse>> findAllAgencyBankWithSearch(@RequestParam(value = "search", defaultValue = "") String search, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllWithPageAndSearch(search, pageable));
    }
}
