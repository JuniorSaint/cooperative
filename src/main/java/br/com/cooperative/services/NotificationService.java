package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.DataBaseException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.Response.NotificationResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.models.entities.Notification;
import br.com.cooperative.models.request.AgencyBankRequest;
import br.com.cooperative.models.request.NotificationRequest;
import br.com.cooperative.repositories.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;

    @Autowired
    private Utils utils;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public NotificationResponse save(NotificationRequest request) {
        return mapper.map(repository.save(mapper.map(request, Notification.class)), NotificationResponse.class);
    }

    @Transactional(readOnly = true)
    public NotificationResponse findById(Long id) {
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
    public String delete(Long id) {
        try {
            findById(id);
            repository.deleteById(id);
            return "Notification" + DELETE_MESSAGE;
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }
}
