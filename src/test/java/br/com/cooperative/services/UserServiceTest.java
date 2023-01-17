package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.ChangePasswordRequest;
import br.com.cooperative.models.request.UserRequest;
import br.com.cooperative.repositories.CooperativeRepository;
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

import java.util.List;
import java.util.Optional;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.mock.EntitiesMock.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private CooperativeRepository cooperativeRepository;

    @Mock
    private ModelMapper mapper;
    @Mock
    private UsefulMethods utils;

    private UserRequest userRequest;
    private UserResponse userResponse;
    private List<UserResponse> userResponseList;
    private List<User> userList;
    private Cooperative cooperative;
    private ChangePasswordRequest changePasswordRequest;
    private User user;
    private PageImpl<User> userPage;
    private PageImpl<UserResponse> userResponsePage;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        user = USER;
        userRequest = USER_REQUEST;
        userResponse = USER_RESPONSE;
        cooperative = COOPERATIVE;
        changePasswordRequest = CHANGE_PASSWORD_REQUEST;
        userPage = new PageImpl<>(List.of(user));
        userResponsePage = new PageImpl<>(List.of(userResponse));
        userResponseList = List.of(userResponse);
        userList = List.of(user);
        pageable = PageRequest.of(0, 20);
    }

    @Test
    @DisplayName("Save - Should throw BadRequestException when email already exist")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowExceptionIfEmailExist() {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        BadRequestException res = Assertions.assertThrows(BadRequestException.class, () -> service.save(user));
        Assertions.assertEquals(BadRequestException.class, res.getClass());
        Assertions.assertEquals("Already exist user with this email: " + user.getEmail() + ", try with another one", res.getMessage());
    }

    @Test
    @DisplayName("Save - Should throw BadRequestException when cooperative is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowExceptionWhenCooperativeIsNull() {
        user.setCooperative(null);
        BadRequestException res = Assertions.assertThrows(BadRequestException.class, () -> {
            service.save(user);
        });
        Assertions.assertEquals(BadRequestException.class, res.getClass());
        Assertions.assertEquals("Cooperative not informed, it's not allowed user without a cooperative", res.getMessage());
    }

    @Test
    @DisplayName("Save - Should throw EntityNotFoundException when cooperative not found")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowEntityNotFoundExceptionWhenCooperativeNotFound() {
        user.setCooperative(cooperative);

        EntityNotFoundException res = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.save(user);
        });
        Assertions.assertEquals(EntityNotFoundException.class, res.getClass());
        Assertions.assertEquals("Cooperative" + CP.NOT_FOUND + " id: " + cooperative.getId(), res.getMessage());
    }

    @Test
    @DisplayName("Save - Should save an user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSaveWithSuccess() {
        when(repository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        user.setCooperative(COOPERATIVE);
        UserResponse response = service.save(user);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponse.class, response.getClass());
        verify(repository, atLeastOnce()).save(user);
    }

    @Test
    @DisplayName("Update - Should throw BadRequestException when email doesn't exist")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldThrowExceptionIfEmailDontExist() {
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        EntityNotFoundException res = Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(user));
        Assertions.assertEquals("User" + CP.NOT_FOUND + " email: " + userRequest.getEmail(), res.getMessage());
    }


    @Test
    @DisplayName("Update - Should throw BadRequestException when cooperative is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldThrowExceptionWhenCooperativeIsEmpty() {
        when(repository.findByEmail(any())).thenReturn(Optional.of(user));
        user.setCooperative(null);
        BadRequestException res = Assertions.assertThrows(BadRequestException.class, () -> {
            service.update(user);
        });
        Assertions.assertEquals(BadRequestException.class, res.getClass());
        Assertions.assertEquals("Cooperative not informed, it's not allowed user without a cooperative", res.getMessage());
    }

    @Test
    @DisplayName("Update - Should update user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldUpdateUserWithSuccess() {
        when(repository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userResponse);
        UserResponse response = service.update(user);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponse.class, response.getClass());
        verify(repository).save(any(User.class));
    }

//    @Test
//    @DisplayName("Change Password - Should change password with success")
//    @EnabledForJreRange(min = JRE.JAVA_17)
//    void changePasswordShouldChangeWithSuccess() {
//        when(repository.findById(any())).thenReturn(Optional.of(user));
//        when(mapper.map(any(), eq(User.class))).thenReturn(user);
//        when(repository.save(any())).thenReturn(user);
//        String response = service.changePassword(changePasswordRequest);
//        Assertions.assertEquals(response, "The password was changed with success of the user: " + user.getEmail());
//        Assertions.assertNotNull(response);
//    }

    @Test
    @DisplayName("Delete - Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        Assertions.assertDoesNotThrow(() -> service.delete(any()));
        Assertions.assertEquals("User" + DELETE_MESSAGE, "User" + DELETE_MESSAGE);
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Delete - Should throw EntityNotFoundException")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void deleteShouldThrowEntityNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException response = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.delete(any());
        });
        Assertions.assertEquals(EntityNotFoundException.class, response.getClass());
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("FindById - Should throw an exception when try find by Id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdThrowException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException resp = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(ID_EXIST);
        });
        Assertions.assertEquals(EntityNotFoundException.class, resp.getClass());
        Assertions.assertEquals("User" + CP.NOT_FOUND + " id:" + ID_EXIST, resp.getMessage());
    }

    @Test
    @DisplayName("FindById - Should return a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdShouldReturnUserWithSuccess() {
        when(repository.findById(ID_EXIST)).thenReturn(Optional.of(user));
        when(mapper.map(any(), any())).thenReturn(userResponse);
        UserResponse response = service.findById(ID_EXIST);
        Assertions.assertEquals(UserResponse.class, response.getClass());
        Assertions.assertNotNull(response);
        verify(repository).findById(ID_EXIST);
    }

    @Test
    @DisplayName("Find all Pageable - Should fetch all pageable and filtered successfully")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithPageAndSearch() {
        when(repository.findBySearch(anyString(), eq(pageable))).thenReturn(userPage);
        when(utils.mapEntityPageIntoDtoPage(userPage, UserResponse.class)).thenReturn(userResponsePage);
        Page<UserResponse> responses = service.findAllWithPageAndSearch("Jose", pageable);
        Assertions.assertEquals(1, responses.getTotalElements());
        Assertions.assertEquals(1, responses.getSize());
        verify(repository).findBySearch(anyString(), any(Pageable.class));
        verify(repository, times(1)).findBySearch(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("findAllListed - Should fetch listed all user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllListed() {
        when(repository.findAll()).thenReturn(userList);
        when(utils.mapListIntoDtoList(userList, UserResponse.class)).thenReturn(userResponseList);
        List<UserResponse> response = service.findAllListed();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(UserResponse.class, response.get(0).getClass());
        verify(repository, times(1)).findAll();
    }

}