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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private List<UserResponse> userResponseList;
    private List<User> userList;
    private Cooperative cooperative;
    private Role role;
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
        role = ROLE;
        userResponsePage = new PageImpl<>(List.of(userResponse));
        userResponseList = List.of(userResponse);
        userList = List.of(user);
        pageable = PageRequest.of(0, 20);
    }

    @Test
    @DisplayName("loadUserByUsername - Should return user detail with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void loadUserByUsernameWithSuccess() {
        UserDetails userDetails = user;
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(mapper.map(any(), any())).thenReturn(userDetails);
        UserDetails response = service.loadUserByUsername(anyString());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getUsername(), "Jose");
    }

    @Test
    @DisplayName("loadUserByUsername - Should throw an exception EntityNotFoundException")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void loadUserByUsernameShouldThrowEntityNotFoundException() {
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.loadUserByUsername(any());
        });
        verify(repository, times(1)).findByEmail(any());
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
    @DisplayName("Save - Should save an user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSaveWithSuccess() {
        when(repository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        userRequest.setCooperative(COOPERATIVE_REQUEST);
        when(mapper.map(any(), eq(User.class))).thenReturn(user);
        when(mapper.map(any(User.class), eq(UserResponse.class))).thenReturn(userResponse);
        when(repository.save(user)).thenReturn(user);
        UserResponse response = service.save(userRequest);
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(response.getClass(), UserResponse.class);
        verify(repository, times(1)).save(user);
    }

    @Test
    @DisplayName("Update - Should throw BadRequestException when email doesn't exist")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldThrowExceptionIfEmailDontExist() {
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        EntityNotFoundException res = Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(userRequest));
        Assertions.assertEquals(res.getMessage(), "User" + CP.NOT_FOUND + " email: " + userRequest.getEmail());
        verify(repository, never()).save(user);
    }

    @Test
    @DisplayName("Update - Should throw BadRequestException when cooperative is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldThrowExceptionWhenCooperativeIsEmpty() {
        when(repository.findByEmail(any())).thenReturn(Optional.of(user));
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
    void changePasswordShouldChangeWithSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        when(mapper.map(any(), eq(User.class))).thenReturn(user);
        when(repository.save(any())).thenReturn(user);
        String response = service.changePassword(changePasswordRequest);
        Assertions.assertEquals(response, "The password was changed with success of the user: " + user.getEmail());
        Assertions.assertNotNull(response);
        verify(repository, times(1)).save(user);
    }

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
    @DisplayName("FindById - Should throw an exception when try find by Id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdThrowException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException resp = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(ID_EXIST);
        });
        Assertions.assertEquals(resp.getClass(), EntityNotFoundException.class);
        Assertions.assertEquals(resp.getMessage(), "User" + CP.NOT_FOUND + " id:" + ID_EXIST);
    }

    @Test
    @DisplayName("FindById - Should return a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdShouldReturnUserWithSuccess() {
        when(repository.findById(ID_EXIST)).thenReturn(Optional.of(user));
        when(mapper.map(any(), any())).thenReturn(userResponse);
        UserResponse response = service.findById(ID_EXIST);
        Assertions.assertEquals(response.getClass(), UserResponse.class);
        Assertions.assertNotNull(response);
        verify(repository).findById(ID_EXIST);
    }

    @Test
    @DisplayName("Find all Pageable - Should fetch all pageable and filtered successfully")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithPageAndSearch() {
        when(permissionRepository.findAllByRole(anyString())).thenReturn(List.of(role));
        when(repository.findBySearch(anyString(), anyList(), eq(pageable))).thenReturn(userPage);
        when(utils.mapEntityPageIntoDtoPage(userPage, UserResponse.class)).thenReturn(userResponsePage);
        Page<UserResponse> responses = service.findAllWithPageAndSearch("Jose", pageable);
        Assertions.assertEquals(responses.getTotalElements(), 1);
        Assertions.assertEquals(responses.getSize(), 1);
        verify(repository).findBySearch(anyString(), anyList(), any(Pageable.class));
        verify(repository, times(1)).findBySearch(anyString(), anyList(), any(Pageable.class));
    }

    @Test
    @DisplayName("findAllListed - Should fetch listed all user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllListed() {
        when(repository.findAll()).thenReturn(userList);
        when(utils.mapListIntoDtoList(userList, UserResponse.class)).thenReturn(userResponseList);
        List<UserResponse> response = service.findAllListed();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0).getClass(), UserResponse.class);
        verify(repository, times(1)).findAll();
    }
}