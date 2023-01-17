package br.com.cooperative.services;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.CooperativeResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.request.CooperativeRequest;
import br.com.cooperative.repositories.CooperativeRepository;
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
class CooperativeServiceTest {
    @InjectMocks
    CooperativeService service;
    @Mock
    private CooperativeRepository repository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UsefulMethods utils;
    private Cooperative cooperative;
    private CooperativeResponse cooperativeResponse;
    private CooperativeRequest cooperativeRequest;
    private List<Cooperative> cooperativeList;
    private List<CooperativeResponse> cooperativeResponseList;
    private Pageable pageable;
    private PageImpl<Cooperative> cooperativePage;
    private PageImpl<CooperativeResponse> cooperativeResponsePage;
    private Cooperative cooperativeBranch;


    @BeforeEach
    void setUp() {
        cooperative = COOPERATIVE;
        cooperativeResponse = COOPERATIVE_RESPONSE;
        cooperativeRequest = COOPERATIVE_REQUEST;
        cooperativeList = List.of(cooperative);
        cooperativeResponsePage = new PageImpl<>(List.of(cooperativeResponse));
        cooperativePage = new PageImpl<>(List.of(cooperative));
        cooperativeBranch = COOPERATIVE_BRANCH;
    }

    @Test
    @DisplayName("Save - Should throw a BadRequestException when matrix is null")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSaveBadRequestExceptionWhenMatrixIsNull() {
        when(repository.findById(any())).thenReturn(Optional.of(cooperativeBranch));
        cooperativeBranch.setMatrix(null);
        BadRequestException response = Assertions.assertThrows(BadRequestException.class, () -> {
            service.saveUpdate(cooperativeBranch);
        });
        Assertions.assertEquals(response.getClass(), BadRequestException.class);
        Assertions.assertEquals(response.getMessage(), "Matrix it's not allowed null, when is registering a branch");

    }

    @Test
    @DisplayName("Save - Should throw a EntityNotFoundException when cooperative not found")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowEntityNotFoundExceptionWhenCooperativeNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException response = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.saveUpdate(cooperativeBranch);
        });
        Assertions.assertEquals(response.getMessage(), "Matrix" + NOT_FOUND + " id: " + cooperativeBranch.getMatrix().getId());
        verify(repository, never()).save(cooperativeBranch);
    }

    @Test
    @DisplayName("Save - Should throw a EntityNotFoundException when cooperative not found")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldThrowBadRequestExceptionWhenMatrixEqualBranch() {
        when(repository.findById(any())).thenReturn(Optional.of(cooperativeBranch));
        when(repository.findById(cooperativeBranch.getMatrix().getId())).thenReturn(Optional.of(cooperativeBranch));

        BadRequestException response = Assertions.assertThrows(BadRequestException.class, () -> {
            service.saveUpdate(cooperativeBranch);
        });
        Assertions.assertEquals(response.getMessage(), "The matrix that you're trying to register is a branch. - Branch");
        verify(repository, never()).save(cooperativeBranch);
    }

    @Test
    @DisplayName("Save - Should save a cooperative with success Branch")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSaveWithSuccessBranch() {
        when(repository.findById(any())).thenReturn(Optional.of(cooperativeBranch));
        when(repository.findById(any())).thenReturn(Optional.of(cooperative));
        cooperativeBranch.setMatrix(cooperative);
        when(mapper.map(any(), eq(CooperativeResponse.class))).thenReturn(cooperativeResponse);
        CooperativeResponse response = service.saveUpdate(cooperativeBranch);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getClass(), CooperativeResponse.class);
        verify(repository, atLeastOnce()).save(cooperativeBranch);
    }

    @Test
    @DisplayName("Save - Should save a cooperative with success Matrix")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSaveWithSuccessMatrix() {
        cooperative.setId(null);
        when(mapper.map(any(), eq(CooperativeResponse.class))).thenReturn(cooperativeResponse);
        CooperativeResponse response = service.saveUpdate(cooperative);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getClass(), CooperativeResponse.class);
        verify(repository, atLeastOnce()).save(cooperative);
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
        Assertions.assertEquals(resp.getMessage(), "Cooperative" + NOT_FOUND + "id: " + ID_EXIST);
    }

    @Test
    @DisplayName("findAllListed - Should fetch listed all cooperatives with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllListed() {
        when(repository.findAll()).thenReturn(cooperativeList);
        when(utils.mapListIntoDtoList(any(), eq(CooperativeResponse.class))).thenReturn(cooperativeResponseList);
        List<CooperativeResponse> response = service.findAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Find all Pageable - Should fetch all pageable and filtered successfully")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithPageAndSearch() {
        when(repository.findBySearch(anyString(), eq(pageable))).thenReturn(cooperativePage);
        when(utils.mapEntityPageIntoDtoPage(cooperativePage, CooperativeResponse.class)).thenReturn(cooperativeResponsePage);
        Page<CooperativeResponse> responses = service.findAllWithPageAndSearch("Cooperativa", pageable);
        Assertions.assertEquals(responses.getTotalElements(), 1);
        Assertions.assertEquals(responses.getSize(), 1);
    }

    @Test
    @DisplayName("Delete - Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(cooperative));
        String response = Assertions.assertDoesNotThrow(() -> service.delete(any()));
        Assertions.assertNotNull(response);
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