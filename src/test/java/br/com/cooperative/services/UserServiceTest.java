package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.mock.EntitiesMock.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
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
    }

    @Test
    @DisplayName("Should return user in loadUserByUsername with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void loadUserByUsername() {
        when(repository.findByEmail(any())).thenReturn(Optional.of(user));
        UserDetails response = service.loadUserByUsername(any());
        verify(repository, times(1)).findByEmail(any());
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
    @DisplayName("Should save a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void save() {
        when(cooperativeRepository.findById(any())).thenReturn(Optional.ofNullable(cooperative));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        when(repository.save(any())).thenReturn(any());
        UserResponse response = service.save(userRequest);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw BadRequestException when email already exist")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowExceptionIfEmailExist() {


    }


    @Test
    @DisplayName("Update Should Throw Exception When Cooperative Is Empty")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldThrowExceptionWhenCooperativeIsEmpty() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.update(userRequest);
        });
    }

    @Test
    @DisplayName("Should change password with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void changePassword() {

    }


    @Test
    @DisplayName("Should delete user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        Assertions.assertEquals("User" + DELETE_MESSAGE, service.delete(any()), "User" + DELETE_MESSAGE);
        verify(repository, times(1)).deleteById(any());

    }

    @Test
    @DisplayName("Should return a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findById(UUID id) {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        UserResponse response = service.findById(any());
        verify(repository, times(1)).findById(any());
    }


    @Test
    @DisplayName("Should throw an exception when try find by Id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdThrowException() {
        doThrow(EntityNotFoundException.class).when(repository).findById(ID_NO_EXIST);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(ID_NO_EXIST);
        });
        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should list with page user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithPageAndSearch() {
        List<Role> role = permissionRepository.findAllByRole(any());
        when(repository.findBySearch(anyString(), role, (Pageable) any())).thenReturn(userPage);

        Page<UserResponse> response = service.findAllWithPageAndSearch(any(), any());
        Assertions.assertNotNull(response);
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