package br.com.cooperative.services;

import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.repositories.AgencyBankRepository;
import br.com.cooperative.repositories.BankRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static br.com.cooperative.configs.CP.NOT_FOUND;
import static br.com.cooperative.mock.EntitiesMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
class AgencyBankServiceTest {
    @InjectMocks
    AgencyBankService service;
    @Mock
    private AgencyBankRepository repository;
    @Mock
    private BankRepository bankRepository;
    @Mock
    private ModelMapper mapper;
    private AgencyBank agencyBankSave;
    private AgencyBank agencyBankUpdate;
    private AgencyBankResponse agencyBankResponse;
    private Bank bank;


    @BeforeEach
    void setUp() {
        agencyBankUpdate = AGENCY_BANK_UPDATE;
        agencyBankSave = AGENCY_BANK;
        agencyBankResponse = AGENCY_BANK_RESPONSE;
        bank = BANK_UPDATE;
    }

    @Test
    @DisplayName("Save - Should throw BadRequestException when bank is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void shouldThrowBadRequestExceptionWhenBankIsNull() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBankUpdate));
        agencyBankUpdate.setBank(null);
        BadRequestException response = Assertions.assertThrows(BadRequestException.class, () -> {
            service.saveUpdate(agencyBankUpdate);
        });
        Assertions.assertEquals(response.getMessage(), "It's not allowed register an agency bank without bank");
        verify(repository, never()).save(agencyBankUpdate);
    }

    @Test
    @DisplayName("Save - Should throw EntityNotFoundException when bank not found")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void shouldThrowEntityNotFoundExceptionWhenBankNotFound() {
        agencyBankUpdate.setBank(bank);
        when(bankRepository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException response = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.saveUpdate(agencyBankUpdate);
        });
        Assertions.assertEquals(response.getMessage(), "Bank" + NOT_FOUND + " id:" + agencyBankUpdate.getBank().getId());
        verify(repository, never()).save(agencyBankUpdate);
    }

    @Test
    @DisplayName("Save - Should Throw BadRequestException When Cooperative Is Null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void shouldThrowBadRequestExceptionWhenCooperativeIsNull() {
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        agencyBankUpdate.setCooperative(null);
        BadRequestException response = Assertions.assertThrows(BadRequestException.class, () -> {
            service.saveUpdate(agencyBankUpdate);
        });
        Assertions.assertEquals(response.getMessage(), "It's not allowed register an agency bank without a cooperative");
        verify(repository, never()).save(agencyBankUpdate);
    }
    @Test
    @DisplayName("Save - Should save with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void shouldSaveWithSuccess() {
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        agencyBankUpdate.setCooperative(null);
        BadRequestException response = Assertions.assertThrows(BadRequestException.class, () -> {
            service.saveUpdate(agencyBankUpdate);
        });
        Assertions.assertEquals(response.getMessage(), "It's not allowed register an agency bank without a cooperative");
        verify(repository, never()).save(agencyBankUpdate);
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllWithPageAndSearch() {
    }

    @Test
    void delete() {
    }
}