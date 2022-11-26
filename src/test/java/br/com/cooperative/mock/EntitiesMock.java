package br.com.cooperative.mock;

import br.com.cooperative.models.Response.*;
import br.com.cooperative.models.entities.*;
import br.com.cooperative.models.request.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EntitiesMock {
    LocalDate date = LocalDate.of(1970, 12, 21);
    UUID ID_EXIST = UUID.fromString("f2e5aeb0-c519-418e-814d-3683852d3d17");
    UUID ID_NO_EXIST = UUID.fromString("07190383-eec3-4b02-a103-03344f5dd0f9");
    OnlyIdRequest ONLY_ID_REQUEST = OnlyIdRequest.builder().id(UUID.fromString("62d81792-9be3-46a5-9d93-4dc34468859b")).build();
    RoleRequest ROLE_REQUEST = RoleRequest.builder().id(ID_EXIST).role("ADMINISTRATOR").build();
    Role ROLE = Role.builder().id(ID_EXIST).role("ADMINISTRATOR").build();
    RoleResponse ROLE_RESPONSE = RoleResponse.builder().id(ID_EXIST).roles(Set.of()).build();
    User USER = User.builder().id(ID_EXIST).userName("Jose").email("junior@junior.com").password("123456").active(true).cpf("885.885.885-00")
            .birthday(date).roles(List.of(ROLE)).build();
    UserRequest USER_REQUEST = UserRequest.builder().id(ID_EXIST).userName("Jose").email("junior@junior.com").password("123456").active(true).cpf("885.885.885-00")
            .roles(List.of(ROLE_REQUEST)).cooperative(ONLY_ID_REQUEST).build();
    UserResponse USER_RESPONSE = UserResponse.builder().id(ID_EXIST).userName("Jose").email("junior@junior.com").active(true).cpf("885.885.885-00").birthday(date)
            .roles(List.of(ROLE_RESPONSE)).cooperative(ONLY_ID_REQUEST).age(51).build();
    Cooperative COOPERATIVE = Cooperative.builder().id(ID_EXIST).address(new Address()).cnpj("25.258.258/0001-20").contact(new Contact())
            .name("Cooperativa dos Produtores Rurais de Carandaí").users(Set.of(USER)).build();
    CooperativeRequest COOPERATIVE_REQUEST = CooperativeRequest.builder().id(ID_EXIST).address(new Address()).cnpj("25.258.258/0001-20").contact(new Contact())
            .name("Cooperativa dos Produtores Rurais de Carandaí").build();
    CooperativeResponse COOPERATIVE_RESPONSE = CooperativeResponse.builder().id(ID_EXIST).address(new AddressResponse()).cnpj("25.258.258/0001-20").contact(new ContactResponse())
            .name("Cooperativa dos Produtores Rurais de Carandaí").build();

   ChangePasswordRequest CHANGE_PASSWORD_REQUEST = ChangePasswordRequest.builder().id(ID_EXIST).password("123456").build();
}
