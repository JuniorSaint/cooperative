package br.com.cooperative.services;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.NotificationResponse;
import br.com.cooperative.models.entities.Notification;
import br.com.cooperative.models.request.NotificationRequest;
import br.com.cooperative.repositories.NotificationRepository;
import br.com.cooperative.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.cooperative.configs.CP.NOT_FOUND;
import static br.com.cooperative.mock.EntitiesMock.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
class NotificationServiceTest {
    @InjectMocks
    NotificationService service;
    @Mock
    private NotificationRepository repository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UsefulMethods utils;
    @Mock
    private UserRepository userRepository;
    private NotificationRequest notificationRequest;

    private Notification notification;

    private NotificationResponse notificationResponse;
    private List<NotificationResponse> notificationResponseList;
    private List<Notification> notificationList;
    private Pageable pageable;
    private PageImpl<Notification> notificationPage;
    private PageImpl<NotificationResponse> notificationPageResponse;
    private LocalDate date;


    @BeforeEach
    void setUp() {
        LocalDate date = LocalDate.of(1970, 12, 21);
        notification = NOTIFICATION;
        notificationRequest = NOTIFICATION_REQUEST;
        notificationResponse = NOTIFICATION_RESPONSE;
        notificationList = List.of(notification);
        notificationResponseList = List.of(notificationResponse);
        pageable = PageRequest.of(0, 20);
        notificationPage = new PageImpl<>(List.of(notification));
        notificationPageResponse = new PageImpl<>(List.of(notificationResponse));
    }

    @Test
    @DisplayName("Save - Should throw BadRequestException when body of message is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowExceptionBodyOfMessageIsNull() {
        notification.setBody(null);
        BadRequestException response = Assertions.assertThrows(BadRequestException.class, () -> {
            service.save(notification);
        });
        Assertions.assertEquals(response.getMessage(), "The body of message is not allowed null");
        verify(repository, never()).save(notification);
    }

    @Test
    @DisplayName("Save - Should save successfully")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSaveSuccessfully() {
        when(userRepository.findById(any())).thenReturn(Optional.of(USER));
        when(mapper.map(any(), eq(NotificationResponse.class))).thenReturn(notificationResponse);
        notification.setBody("Long text");
        NotificationResponse response = service.save(notification);
        Assertions.assertNotNull(response);
        verify(repository, atLeastOnce()).save(notification);
    }

    @Test
    void sendEmail() {
    }

    @Test
    @DisplayName("Find By Id - Should throw EntityNotFound when request not found")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdShouldThrowEntityNotFoundWhenRequestNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException response = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(ID_EXIST);
        });
        Assertions.assertEquals(response.getClass(), EntityNotFoundException.class);
        Assertions.assertEquals(response.getMessage(), "Notification" + NOT_FOUND + "id: " + ID_EXIST);
    }

    @Test
    @DisplayName("Find By Id - Should fetch notification by id successfully")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdShouldFetchNotificationByIdSuccessfully() {
        when(repository.findById(any())).thenReturn(Optional.of(notification));
        when(mapper.map(any(), eq(NotificationResponse.class))).thenReturn(notificationResponse);
        NotificationResponse response = service.findById(ID_EXIST);
        Assertions.assertEquals(response.getClass(), NotificationResponse.class);
        Assertions.assertEquals(response.getId(), ID_EXIST);
        verify(repository, atLeastOnce()).findById(ID_EXIST);
    }

    @Test
    @DisplayName("Find All - Should fetch all notifications successfully")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllShouldFetchAllNotificationsSuccessfully() {
        when(repository.findAll()).thenReturn(notificationList);
        when(utils.mapListIntoDtoList(notificationList, NotificationResponse.class)).thenReturn(notificationResponseList);
        List<NotificationResponse> responses = service.findAll();
        Assertions.assertNotNull(responses.get(0));
        verify(repository, atLeastOnce()).findAll();
    }

    @Test
    void findAllWithPageAndSearch() {
        when(repository.findNotificatioin(ID_EXIST, true, date, date, pageable))
                .thenReturn(notificationPage);
        when(utils.mapEntityPageIntoDtoPage(any(), eq(NotificationResponse.class)))
                .thenReturn(notificationPageResponse);
        Page<NotificationResponse> responses = service.findAllWithPageAndSearch(ID_EXIST, true, date, date, pageable);
        Assertions.assertNotNull(responses);
        Assertions.assertEquals(1, responses.getTotalElements());
        verify(repository).findNotificatioin(ID_EXIST, true, date, date, pageable);
    }

    @Test
    @DisplayName("Delete - Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(notification));
        Assertions.assertDoesNotThrow(() -> service.delete(any()));
        verify(repository).deleteById(any());
    }

    @Test
    @DisplayName("Delete - Should throw EntityNotFoundException")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void deleteShouldThrowEntityNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException response = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.delete(any());
        });
        Assertions.assertEquals(response.getClass(), EntityNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }
}