package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.NotificationResponse;
import br.com.cooperative.models.request.NotificationRequest;
import br.com.cooperative.services.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 60 * 60)
@RequestMapping("/v1/notifications")
@Tag(name = "Notification", description = "Manager notification")
public class NotificationsController {
    @Autowired
    private NotificationService service;
    String DateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    @PostMapping
    public ResponseEntity<NotificationResponse> save(@RequestBody @Valid NotificationRequest request) {
        request.setWasRead(false);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(request));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.delete(id));
    }

    @GetMapping("/seek")
    public ResponseEntity<Page<NotificationResponse>> findAllUserWithSearch(@RequestParam(value = "idUser", defaultValue = "0") UUID idUser,
                                                                            @RequestParam(value = "wasRead", defaultValue = "false") Boolean wasRead,
                                                                            @RequestParam(value = "dateInicial") LocalDate dateInicial,
                                                                            @RequestParam(value = "dateFinal") LocalDate dateFinal,
                                                                            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllWithPageAndSearch(idUser, wasRead, dateInicial, dateFinal, pageable));
    }
}
