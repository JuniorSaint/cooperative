package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.entities.Role;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.OnlyIdRequest;
import br.com.cooperative.models.request.RoleRequest;
import br.com.cooperative.models.request.UserRequest;
import br.com.cooperative.repositories.CooperativeRepository;
import br.com.cooperative.repositories.RoleRepository;
import br.com.cooperative.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
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
    private User user;


    private UUID existId;
    private UUID noExistId;
    private UUID dependencyId;
    private UUID idCooperative;


    @BeforeEach
    void setUp() {
        startUser();
        Mockito.doNothing().when(repository).deleteById(dependencyId);
    }

    @Test
    @DisplayName("")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void loadUserByUsername() {
    }

    @Test
    @DisplayName("")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void save() {
    }

    @Test
    @DisplayName("")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void update() {
    }

    @Test
    @DisplayName("")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void changePassword() {
    }

    @Test
    @DisplayName("")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
    }

    @Test
    @DisplayName("Delete should throw EntityNotFoundException")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void deleteDataIntegrityViolationException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.delete(dependencyId);
        });
    }

    @Test
    @DisplayName("Should return a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findById() {

    }


    @Test
    @DisplayName("Should not return a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdThrowException() {

    }

    @Test
    @DisplayName("")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithPageAndSearch() {
    }

    @Test
    @DisplayName("")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllListed() {

    }

    private void startUser() {
        LocalDate date = LocalDate.of(1970, 12, 21);

        existId = UUID.fromString("f2e5aeb0-c519-418e-814d-3683852d3d17");
        noExistId = UUID.fromString("07190383-eec3-4b02-a103-03344f5dd0f9");
        dependencyId = UUID.fromString("62d81192-9be3-46d5-9d93-4dc34468859b");
        idCooperative = UUID.fromString("62d81192-9be3-46a5-9d93-4dc34468859b");
        RoleRequest roleRequest = new RoleRequest();
        OnlyIdRequest onlyIdRequest = new OnlyIdRequest();
        Role role = new Role();
        Cooperative cooperative = new Cooperative();
        user = User.builder().id(existId).userName("Jose").email("junior@junior.com").password("123456").active(true).cpf("885.885.885-00").birthday(date).roles(List.of(role)).cooperative(cooperative).build();
        userRequest = UserRequest.builder().id(existId).userName("Jose").email("junior@junior.com").password("123456").active(true).cpf("885.885.885-00").roles(List.of(roleRequest)).cooperative(onlyIdRequest).build();
        userResponse = UserResponse.builder().id(existId).userName("Jose").email("junior@junior.com").active(true).cpf("885.885.885-00").birthday(date).roles(List.of(roleRequest)).cooperative(onlyIdRequest).age(51).build();
    }
}