package br.com.cooperative.services;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.NotificationResponse;
import br.com.cooperative.models.entities.Notification;
import br.com.cooperative.repositories.NotificationRepository;
import br.com.cooperative.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;

    @Autowired
    private UsefulMethods usefulMethods;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public NotificationResponse save(Notification entity) {
        if (entity.getBody() == null) {
            throw new BadRequestException("The body of message is not allowed null");
        }
        NotificationResponse response = mapper.map(repository.save(entity), NotificationResponse.class);
//        sendEmailAfterSaveNotification(response.getUser().getId());
        return response;
    }

//    public MailResponse sendEmail(MailRequest request) {
//        Map<String, Object> model = new HashMap<>();
//        model.put("Name", request.getName());
//        model.put("deliveryTime", LocalDate.now());
//        return service.sendEmail(request, model);
//    }

    @Transactional(readOnly = true)
    public NotificationResponse findById(UUID id) {
        return repository.findById(id)
                .map(result -> mapper.map(result, NotificationResponse.class))
                .orElseThrow(() -> new EntityNotFoundException("Notification" + NOT_FOUND + "id: " + id));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> findAll() {
        return usefulMethods.mapListIntoDtoList(repository.findAll(), NotificationResponse.class);
    }

    @Transactional(readOnly = true)
    public Page<NotificationResponse> findAllWithPageAndSearch(UUID idUser, Boolean wasRead, LocalDate dateInicial, LocalDate dateFinal, Pageable pageable) {
        return usefulMethods.mapEntityPageIntoDtoPage(repository.findNotificatioin(idUser, wasRead, dateInicial, dateFinal, pageable), NotificationResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification" + NOT_FOUND + "id: " + id));
        repository.deleteById(id);
        return "Notification" + DELETE_MESSAGE;
    }

//    private void sendEmailAfterSaveNotification(UUID id){
//        User user = userRepository.findById(id).get();
//        var mailRequest = new MailRequest();
////        mailRequest.setName(user.getUsername());
//        mailRequest.setTo("junior.sendemail@gmail.com");
//        mailRequest.setFrom(user.getEmail());
//        mailRequest.setSubject("Envio de notificação");
//        sendEmail(mailRequest);
//    }
}
