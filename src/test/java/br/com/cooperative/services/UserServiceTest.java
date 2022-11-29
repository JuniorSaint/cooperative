package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.entities.Role;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.ChangePasswordRequest;
import br.com.cooperative.models.request.UserRequest;
import br.com.cooperative.repositories.CooperativeRepository;
import br.com.cooperative.repositories.RoleRepository;
import br.com.cooperative.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private RoleRepository permissionRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private Utils utils;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserRequest userRequest;
    private UserResponse userResponse;
    private Cooperative cooperative;
    private Role role;
    private ChangePasswordRequest changePasswordRequest;
    private User user;
    private PageImpl<User> userPage;

    @BeforeEach
    void setUp() {
        user = USER;
        userRequest = USER_REQUEST;
        userResponse = USER_RESPONSE;
        cooperative = COOPERATIVE;
        changePasswordRequest = CHANGE_PASSWORD_REQUEST;
        userPage = new PageImpl<>(List.of(user));
        role = ROLE;
    }

    @Test
    @DisplayName("Should return user in loadUserByUsername with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void loadUserByUsername() {
        when(repository.findByEmail(any())).thenReturn(Optional.of(user));
        UserDetails response = service.loadUserByUsername(any());
        Assertions.assertEquals(response.getClass(), UserDetails.class);
        verify(repository, times(1)).existsByEmail(any());
    }

    @Test
    @DisplayName("Should throw in loadUserByUsername EntityNotFoundException")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void loadUserByUsernameShouldThrowEntityNotFoundException() {
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.loadUserByUsername(any());
        });
        verify(repository, times(1)).findByEmail(any());
    }

    @Test
    @DisplayName("Save - should save a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void save() {
        when(repository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        userRequest.setCooperative(ONLY_ID_REQUEST);
        when(repository.save(user)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userResponse);
        UserResponse response = service.save(userRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getClass(), UserResponse.class);
        verify(repository).save(any(User.class));
    }

    @Test
    @DisplayName("Save - Should throw BadRequestException when email already exist")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowExceptionIfEmailExist() {
        when(repository.existsByEmail(USER_REQUEST.getEmail())).thenReturn(true);
        BadRequestException res = Assertions.assertThrows(BadRequestException.class, () -> service.save(userRequest));
        Assertions.assertEquals(res.getClass(), BadRequestException.class);
        verify(repository, never()).save(user);
    }


    @Test
    @DisplayName("Save - Should throw BadRequestException when cooperative is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowExceptionWhenCooperativeIsNull() {
        userRequest.setCooperative(null);
        BadRequestException res = Assertions.assertThrows(BadRequestException.class, () -> {
            service.save(userRequest);
        });
        Assertions.assertEquals(res.getClass(), BadRequestException.class);
        verify(repository, never()).save(user);
    }

    @Test
    @DisplayName("Update - Should throw BadRequestException when email dont exist")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowExceptionIfEmailDontExist() {
        EntityNotFoundException res = Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(userRequest));
        Assertions.assertEquals(res.getMessage(), "User" + CP.NOT_FOUND + " email: " + userRequest.getEmail());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Update - Should throw BadRequestException when cooperative is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldThrowExceptionWhenCooperativeIsEmpty() {
        userRequest.setCooperative(null);
        BadRequestException res = Assertions.assertThrows(BadRequestException.class, () -> {
            service.update(userRequest);
        });
        Assertions.assertEquals(res.getClass(), BadRequestException.class);
        verify(repository, never()).save(user);
    }

    @Test
    @DisplayName("Update - Should update user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldUpdateUserWithSuccess() {
        when(repository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userResponse);
        UserResponse response = service.update(userRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getClass(), UserResponse.class);
        verify(repository).save(any(User.class));
    }

    @Test
    @DisplayName("Change Password - Should change password with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void changePassword() {
        when(service.findById(any())).thenReturn(userResponse);
        when(mapper.map(userResponse, User.class)).thenReturn(user);
        String response = service.changePassword(changePasswordRequest);
        Assertions.assertEquals(response, "The password was changed with success of the user: " + user.getEmail());
        Assertions.assertNotNull(response);
        verify(repository, times(1)).save(user);
    }

    @Test
    @DisplayName("Delete - Should delete user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        Assertions.assertDoesNotThrow(() -> service.delete(any()));
        Assertions.assertEquals("User" + DELETE_MESSAGE, "User" + DELETE_MESSAGE);
        verify(repository, times(1)).deleteById(any());

    }

    @Test
    @DisplayName("Find by Id - Should throw an exception when try find by Id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdThrowException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException resp = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(any());
        });
        Assertions.assertEquals(resp.getClass(), EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Find by Id - Should return a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdShouldReturnUserWithSuccess() {
        when(repository.findById(ID_EXIST)).thenReturn(Optional.of(user));
        when(mapper.map(any(), any())).thenReturn(userResponse);
        UserResponse response = service.findById(ID_EXIST);
        Assertions.assertEquals(response.getClass(), UserResponse.class);
        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("Should list with page user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithPageAndSearch() {
        when(permissionRepository.findAllByRole(any())).thenReturn(List.of(role));
        when(repository.findBySearch(anyString(), anyList(), (Pageable) any())).thenReturn(userPage);
        Assertions.assertDoesNotThrow(() -> {
            service.findAllWithPageAndSearch(anyString(), (Pageable) any());
        });
        verify(repository).findBySearch(anyString(), anyList(), (Pageable) any());
        verify(repository, times(1)).findBySearch(anyString(), anyList(), (Pageable) any());


    }

    @Test
    @DisplayName("Should list all user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllListed() {
        when(repository.findAll()).thenReturn(List.of(user));
        List<UserResponse> response = service.findAllListed();
        Assertions.assertNotNull(response);
        verify(repository, times(1)).findAll();
    }
}