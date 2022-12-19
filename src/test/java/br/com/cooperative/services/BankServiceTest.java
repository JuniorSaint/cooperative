package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
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
    private Utils utils;
    private Bank bank;
    private BankRequest bankRequest;
    private BankResponse bankResponse;
    private Pageable pageable;
    private List<Bank> listBank;
    private List<BankResponse> bankResponseList;

    @BeforeEach
    void setUp() {
        bank = BANK;
        bankResponse = BANK_RESPONSE;
        bankRequest = BANK_REQUEST;
        listBank = List.of(bank);
        bankResponseList = List.of(bankResponse);
    }

    @Test
    void saveUpdate() {
        when(repository.findById(any())).thenReturn(Optional.of(bank));
        when(mapper.map(any(), eq(Bank.class))).thenReturn(bank);
        when(mapper.map(any(), eq(BankResponse.class))).thenReturn(bankResponse);
        BankResponse response = service.saveUpdate(bankRequest);
        Assertions.assertEquals(response.getClass(), BankResponse.class);
        Assertions.assertNotNull(response.getId());
        verify(repository, atLeastOnce()).save(any());
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
        verify(repository, times(1));
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
    void findAll() {
        when(repository.findAll()).thenReturn(listBank);
        when(utils.mapListIntoDtoList(listBank, BankResponse.class)).thenReturn(bankResponseList);
        List<BankResponse> responses = service.findAll();
        Assertions.assertNotNull(responses);
        Assertions.assertNotNull(responses.get(0).getId());
        Assertions.assertEquals(responses.get(0).getClass(), BankResponse.class);
        verify(repository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("Delete - Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(bank));
        Assertions.assertDoesNotThrow(() -> service.delete(any()));
        verify(repository).deleteById(any());
    }
}