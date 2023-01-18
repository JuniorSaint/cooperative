package br.com.cooperative.services;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.repositories.AgencyBankRepository;
import br.com.cooperative.repositories.BankRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
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
    @Mock
    private UsefulMethods usefulMethods;
    private AgencyBank agencyBankSave;
    private AgencyBank agencyBankUpdate;
    private AgencyBankResponse agencyBankResponse;
    private Bank bank;
    private Cooperative cooperative;
    private Pageable pageable;
    private PageImpl<AgencyBank> agencyBankPage;
    private PageImpl<AgencyBankResponse> agencyBankResponsePage;


    @BeforeEach
    void setUp() {
        agencyBankUpdate = AGENCY_BANK_UPDATE;
        agencyBankSave = AGENCY_BANK;
        agencyBankResponse = AGENCY_BANK_RESPONSE;
        bank = BANK_UPDATE;
        cooperative = COOPERATIVE;
        agencyBankPage = new PageImpl<>(List.of(agencyBankUpdate));
        agencyBankResponsePage = new PageImpl<>(List.of(agencyBankResponse));
    }

    @Test
    @DisplayName("Save - Should throw BadRequestException when bank is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void shouldThrowBadRequestExceptionWhenBankIsNull() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBankUpdate));
        agencyBankSave.setBank(null);
        BadRequestException response = Assertions.assertThrows(BadRequestException.class, () -> {
            service.saveUpdate(agencyBankSave);
        });
        Assertions.assertEquals(response.getMessage(), "It's not allowed register an agency bank without bank");
        verify(repository, never()).save(agencyBankUpdate);
    }

    @Test
    @DisplayName("Save - Should throw EntityNotFoundException when bank not found")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void shouldThrowEntityNotFoundExceptionWhenBankNotFound() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBankUpdate));
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
        when(repository.findById(any())).thenReturn(Optional.of(agencyBankUpdate));
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
        agencyBankSave.setCooperative(cooperative);
        agencyBankSave.setBank(bank);
        when(mapper.map(any(), eq(AgencyBankResponse.class))).thenReturn(agencyBankResponse);
        AgencyBankResponse response = service.saveUpdate(agencyBankSave);
        Assertions.assertNotNull(response);
        verify(repository, atLeastOnce()).save(agencyBankSave);
    }

    @Test
    @DisplayName("Find By Id - Should Throw EntityNotFoundException When Agency not found")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdShoudThowEntityNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException response = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(any());
        });
        Assertions.assertEquals(response.getMessage(), "Agency bank" + NOT_FOUND + "id: " + null);
        Assertions.assertEquals(response.getClass(), EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Find By Id - Should fetch an agency bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdFetchWithSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBankUpdate));
        AgencyBankResponse response = service.findById(any());
        Assertions.assertNotNull(response.getId());
    }

    @Test
    @DisplayName("Find All - Should fetch all agency bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAll() {
        when(usefulMethods.mapListIntoDtoList(any(), eq(AgencyBankResponse.class))).thenReturn(List.of(agencyBankResponse));
        List<AgencyBankResponse> response = service.findAll();
        Assertions.assertNotNull(response);
        verify(repository).findAll();
    }

    @Test
    void findAllWithPageAndSearch() {
        when(repository.findBySearch(anyString(), eq(pageable))).thenReturn(agencyBankPage);
        when(usefulMethods.mapEntityPageIntoDtoPage(agencyBankPage, AgencyBankResponse.class)).thenReturn(agencyBankResponsePage);
        Page<AgencyBankResponse> response = service.findAllWithPageAndSearch("Vaticano", pageable);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getTotalElements(), 1);
        Assertions.assertEquals(response.getSize(), 1);
    }

    @Test
    @DisplayName("Delete - Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBankUpdate));
        String response =  service.delete(any());
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
        Assertions.assertEquals(response.getClass(), EntityNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }
}