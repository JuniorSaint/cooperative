package br.com.cooperative.mock;

import br.com.cooperative.models.Response.*;
import br.com.cooperative.models.entities.*;
import br.com.cooperative.models.request.*;

import java.time.LocalDate;
import java.util.*;

public interface EntitiesMock {
    LocalDate date = LocalDate.of(1970, 12, 21);
    UUID ID_EXIST = UUID.fromString("f2e5aeb0-c519-418e-814d-3683852d3d17");
    UUID ID_NO_EXIST = UUID.fromString("07190383-eec3-4b02-a103-03344f5dd0f9");
    OnlyIdRequest ONLY_ID_REQUEST = OnlyIdRequest.builder().id(UUID.fromString("62d81792-9be3-46a5-9d93-4dc34468859b")).build();
    Contact CONTACT = Contact.builder().name("Jose Anuciação").email("anuciacao@geral.com").phone("32-99999-8888").build();
    ContactRequest CONTACT_REQUEST = ContactRequest.builder().name("Jose Anuciação").email("anuciacao@geral.com").phone("32-99999-8888").build();
    ContactResponse CONTACT_RESPONSE = ContactResponse.builder().name("Jose Anuciação").email("anuciacao@geral.com").phone("32-99999-8888").build();
    Address ADDRESS = Address.builder().street("Rua Belo Monte").number("152C").complemente("Altar").city("Fictício").uf("MG").zipCode("36.258-250").build();
    AddressRequest ADDRESS_REQUEST = AddressRequest.builder().street("Rua Belo Monte").number("152C").complemente("Altar").city("Fictício").uf("MG").zipCode("36.258-250").build();
    AddressResponse ADDRESS_RESPONSE = AddressResponse.builder().street("Rua Belo Monte").number("152C").complemente("Altar").city("Fictício").uf("MG").zipCode("36.258-250").build();
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
    Bank BANK = Bank.builder().id(ID_EXIST).code("5214").ispb("25456").cnpj("25.258.258/0001-20")
            .nameBank("Banco do Vaticano").url("vaticano@vaticano.com").build();
    BankRequest BANK_REQUEST = BankRequest.builder().id(ID_EXIST).code("5214").ispb("25456").cnpj("25.258.258/0001-20")
            .nameBank("Banco do Vaticano").url("vaticano@vaticano.com").build();
    BankResponse BANK_RESPONSE = BankResponse.builder().id(ID_EXIST).code("5214").ispb("25456").cnpj("25.258.258/0001-20")
            .nameBank("Banco do Vaticano").url("vaticano@vaticano.com").build();
    AgencyBank AGENCY_BANK = AgencyBank.builder().id(ID_EXIST).agency("Sucursal do Vaticano no Brasil").cnpj("25.258.258/0002-33").count("152462-2").active(true).bank(BANK).contact(CONTACT).address(ADDRESS).cooperative(COOPERATIVE).build();
    AgencyBankResponse AGENCY_BANK_RESPONSE = AgencyBankResponse.builder().id(ID_EXIST).agency("Sucursal do Vaticano no Brasil").cnpj("25.258.258/0002-33").count("152462-2").active(true).bank(BANK_RESPONSE).contact(CONTACT_RESPONSE).address(ADDRESS_RESPONSE).cooperative(COOPERATIVE_RESPONSE).build();
    AgencyBankRequest AGENCY_BANK_REQUEST = AgencyBankRequest.builder().id(ID_EXIST).agency("Sucursal do Vaticano no Brasil").cnpj("25.258.258/0002-33").count("152462-2").active(true).bank(ONLY_ID_REQUEST).contact(CONTACT_REQUEST).address(ADDRESS_REQUEST).cooperative(ONLY_ID_REQUEST).build();
    Notification NOTIFICATION = Notification.builder().id(ID_EXIST).body("Long text").user(USER).wasRead(false).build();
    NotificationResponse NOTIFICATION_RESPONSE = NotificationResponse.builder().id(ID_EXIST).body("Long text").user(new UserResponse()).wasRead(false).build();
    NotificationRequest NOTIFICATION_REQUEST = NotificationRequest.builder().id(ID_EXIST).body("Long text").user(ONLY_ID_REQUEST).wasRead(false).build();
    Parameter PARAMETER = Parameter.builder().id(ID_EXIST).active(true).minimumLoanValue(1000.0).maximumLoanAmount(10000.0).build();
    ParameterRequest PARAMETER_REQUEST = ParameterRequest.builder().id(ID_EXIST).active(true).minimumLoanValue(1000.0).maximumLoanAmount(10000.0).build();
    ParameterResponse PARAMETER_RESPONSE = ParameterResponse.builder().id(ID_EXIST).active(true).minimumLoanValue(1000.0).maximumLoanAmount(10000.0).build();
    ChangePasswordRequest CHANGE_PASSWORD_REQUEST = ChangePasswordRequest.builder().id(ID_EXIST).password("123456").build();

    Member MEMBER = Member.builder().id(ID_EXIST).name("John Doe").cpfCnpj("888.222.555-22").address(ADDRESS)
            .cooperative(COOPERATIVE).father("João Ninguém").nacionality("Brasileira").contact(CONTACT).build();
    MemberResponse MEMBER_RESPONSE = MemberResponse.builder().id(ID_EXIST).name("John Doe").cpfCnpj("888.222.555-22").address(ADDRESS)
            .cooperative(ONLY_ID_REQUEST).father("João Ninguém").nacionality("Brasileira").contact(CONTACT).build();
    MemberRequest MEMBER_REQUEST = MemberRequest.builder().id(ID_EXIST).name("John Doe").cpfCnpj("888.222.555-22").address(ADDRESS)
            .cooperative(ONLY_ID_REQUEST).father("João Ninguém").nacionality("Brasileira").contact(CONTACT).build();
}
