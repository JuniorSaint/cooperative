package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.MailResponse;
import br.com.cooperative.models.Response.NotificationResponse;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Notification;
import br.com.cooperative.models.request.MailRequest;
import br.com.cooperative.models.request.NotificationRequest;
import br.com.cooperative.repositories.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;

    @Autowired
    private Utils utils;

    @Autowired
    private MailService service;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public NotificationResponse save(NotificationRequest request) {
        NotificationResponse response = mapper.map(repository.save(mapper.map(request, Notification.class)), NotificationResponse.class);
        UserResponse user = userService.findById(request.getUser().getId());
        var mailRequest = new MailRequest();
        mailRequest.setName(user.getUserName());
        mailRequest.setTo("junior.sendemail@gmail.com");
        mailRequest.setFrom(user.getEmail());
        mailRequest.setSubject("Envio de notificação");
//        sendEmail(mailRequest);
        return response;
    }

    public MailResponse sendEmail(MailRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("Name", request.getName());
        model.put("deliveryTime", LocalDate.now());
        return service.sendEmail(request, model);
    }

    @Transactional(readOnly = true)
    public NotificationResponse findById(UUID id) {
        Optional<Notification> response = repository.findById(id);
        if (response.isEmpty()) {
            throw new EntityNotFoundException("Notification" + NOT_FOUND + "id: " + id);
        }
        return mapper.map(response.get(), NotificationResponse.class);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> findAll() {
        return utils.mapListIntoDtoList(repository.findAll(), NotificationResponse.class);
    }

    @Transactional(readOnly = true)
    public Page<NotificationResponse> findAllWithPageAndSearch(Long idUser, Boolean wasRead, LocalDate dateInicial, LocalDate dateFinal, Pageable pageable) {
        return utils.mapEntityPageIntoDtoPage(repository.findNotificatioin(idUser, wasRead, dateInicial, dateFinal, pageable), NotificationResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        findById(id);
        repository.deleteById(id);
        return "Notification" + DELETE_MESSAGE;
    }
}
