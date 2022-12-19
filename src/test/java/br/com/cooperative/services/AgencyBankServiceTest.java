package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.models.request.AgencyBankRequest;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

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
    private ModelMapper mapper;
    @Mock
    private BankRepository bankRepository;

    @Mock
    private Utils utils;
    private AgencyBank agencyBank;
    private AgencyBankRequest agencyBankRequest;
    private AgencyBankResponse agencyBankResponse;
    private List<AgencyBank> agencyBankList;
    private List<AgencyBankResponse> agencyBankResponseList;
    private PageImpl<AgencyBankResponse> agencyBankResponsePage;
    private Bank bank;
    private Pageable pageable;
    private PageImpl<AgencyBank> agencyBankPage;


    @BeforeEach
    void setUp() {
        agencyBank = AGENCY_BANK;
        agencyBankRequest = AGENCY_BANK_REQUEST;
        agencyBankResponse = AGENCY_BANK_RESPONSE;
        agencyBankResponsePage = new PageImpl<>(List.of(agencyBankResponse));
        agencyBankPage = new PageImpl<>(List.of(agencyBank));
        agencyBankList = List.of(agencyBank);
        agencyBankResponseList = List.of(agencyBankResponse);
        bank = BANK;
        pageable = PageRequest.of(0, 20);
    }

    @Test
    @DisplayName("Save and Update - Should throw BadRequestException when bank is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowExceptionBankIsNull() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBank));
        agencyBankRequest.getBank().setId(null);
        BadRequestException response = Assertions.assertThrows(BadRequestException.class, () -> service.saveUpdate(agencyBankRequest));
        verify(repository, never()).save(agencyBank);
        Assertions.assertEquals(response.getClass(), BadRequestException.class);
    }

    @Test
    @DisplayName("Save and Update - Should throw EntityNotFoundException when bank not found")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowEntityNotFoundExceptionWhenBankNotFound() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBank));
        when(bankRepository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException response = Assertions.assertThrows(
                EntityNotFoundException.class, () -> service.saveUpdate(agencyBankRequest));
        verify(repository, never()).save(agencyBank);
        Assertions.assertEquals(response.getClass(), EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Save and Update - Should throw BadRequestException when cooperative is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowBadRequestExceptionWhenCooperativeIsNull() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBank));
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        agencyBankRequest.setCooperative(null);
        BadRequestException response = Assertions.assertThrows(
                BadRequestException.class, () -> service.saveUpdate(agencyBankRequest));
        verify(repository, never()).save(agencyBank);
        Assertions.assertEquals(response.getClass(), BadRequestException.class);
    }

    @Test
    @DisplayName("Save and Update - Should save an agency bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSaveWithSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBank));
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        when(mapper.map(any(), eq(AgencyBank.class))).thenReturn(agencyBank);
        when(mapper.map(any(), eq(AgencyBankResponse.class))).thenReturn(agencyBankResponse);
        AgencyBankResponse response = service.saveUpdate(agencyBankRequest);

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals(response.getClass(), AgencyBankResponse.class);
        verify(repository).save(agencyBank);
    }

    @Test
    @DisplayName("Find by Id - Should throw EntityNotFound agency doesn't exist")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdThrowException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException resp = Assertions.assertThrows(EntityNotFoundException.class, () -> service.findById(ID_EXIST));
        Assertions.assertEquals(resp.getClass(), EntityNotFoundException.class);
        Assertions.assertEquals(resp.getMessage(), "Agency bank" + CP.NOT_FOUND + "id: " + ID_EXIST);
    }

    @Test
    @DisplayName("Find by Id - Should fetch an agency bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdWithSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBank));
        when(mapper.map(any(), any())).thenReturn(agencyBankResponse);
        AgencyBankResponse response = service.findById(ID_EXIST);
        Assertions.assertEquals(response.getClass(), AgencyBankResponse.class);
        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("findAllListed - Should list all agency bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithSuccess() {
        when(repository.findAll()).thenReturn(agencyBankList);
        when(utils.mapListIntoDtoList(agencyBankList, AgencyBankResponse.class)).thenReturn(agencyBankResponseList);
        List<AgencyBankResponse> response = service.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(AgencyBankResponse.class, response.get(0).getClass());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Find all Pageable - Should fetch all pageable and filtered successfully")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithPageAndSearch() {
        when(repository.findBySearch(anyString(), any(Pageable.class))).thenReturn(agencyBankPage);
        when(utils.mapEntityPageIntoDtoPage(agencyBankPage, AgencyBankResponse.class)).thenReturn(agencyBankResponsePage);
        Page<AgencyBankResponse> responses = service.findAllWithPageAndSearch("Vaticano", pageable);
        Assertions.assertNotNull(responses);
        Assertions.assertEquals(1, responses.getTotalElements());
        Assertions.assertEquals(1, responses.getSize());
        verify(repository).findBySearch(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("Delete - Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(agencyBank));
        Assertions.assertDoesNotThrow(() -> service.delete(any()));
        verify(repository).deleteById(any());
    }
}