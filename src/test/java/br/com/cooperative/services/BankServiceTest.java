package br.com.cooperative.services;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.BankResponse;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.models.request.BankRequest;
import br.com.cooperative.repositories.BankRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
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
class BankServiceTest {
    @InjectMocks
    BankService service;
    @Mock
    private BankRepository repository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UsefulMethods usefulMethods;
    private Bank bank;
    private BankRequest bankRequest;
    private BankResponse bankResponse;
    private Pageable pageable;
    private List<Bank> listBank;
    private List<BankResponse> bankResponseList;
    private Bank bankUpdate;

    @BeforeEach
    void setUp() {
        bank = BANK;
        bankResponse = BANK_RESPONSE;
        bankRequest = BANK_REQUEST;
        listBank = List.of(bank);
        bankResponseList = List.of(bankResponse);
        bankUpdate = BANK_UPDATE;
    }

    @Test
    @DisplayName("Update - Should update with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void shouldUpdateWithSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(bankUpdate));
        when(mapper.map(any(), eq(BankResponse.class))).thenReturn(bankResponse);
        BankResponse response = service.saveUpdate(bankUpdate);
        Assertions.assertNotNull(response.getId());
        verify(repository, atLeastOnce()).save(bankUpdate);
    }

    @Test
    @DisplayName("Save - Should save with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSaveWithSuccess() {
        when(mapper.map(any(), eq(BankResponse.class))).thenReturn(bankResponse);
        BankResponse response = service.saveUpdate(bank);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
    }


    @Test
    @DisplayName("Find By Id - Should throw EntityNotFoundException")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdShouldThrowEntityNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException response = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(ID_EXIST);
        });
        Assertions.assertEquals(response.getMessage(), "Bank" + NOT_FOUND + "id: " + ID_EXIST);
        Assertions.assertEquals(response.getClass(), EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Find By Id - Should fetch a bank successfully")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdSuccessfully() {
        when(repository.findById(any())).thenReturn(Optional.of(bank));
        when(mapper.map(any(), any())).thenReturn(bankResponse);
        BankResponse response = service.findById(ID_EXIST);

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals(response.getClass(), BankResponse.class);
        verify(repository, times(1));
    }

    @Test
    @DisplayName("Find All - Should fetch all bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllShouldFetchAllBankWithSuccess() {
        when(repository.findAll()).thenReturn(List.of(bankUpdate));
        List<BankResponse> response = service.findAllBanks();
        Assertions.assertNotNull(response);
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

    @Test
    @DisplayName("Delete - Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(bank));
        String response = Assertions.assertDoesNotThrow(() -> service.delete(any()));
        Assertions.assertNotNull(response);
        verify(repository).deleteById(any());
    }

}