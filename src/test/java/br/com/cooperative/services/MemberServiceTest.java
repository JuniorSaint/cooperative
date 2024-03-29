package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.MemberResponse;
import br.com.cooperative.models.entities.Member;
import br.com.cooperative.models.request.MemberRequest;
import br.com.cooperative.repositories.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static br.com.cooperative.mock.EntitiesMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
class MemberServiceTest {
    @InjectMocks
    MemberService service;
    @Mock
    private MemberRepository repository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UsefulMethods utils;
    private Member member;
    private MemberRequest memberRequest;
    private MemberResponse memberResponse;
    private List<Member> memberList;
    private List<MemberResponse> memberResponseList;
    private PageImpl<MemberResponse> memberResponsePage;
    private Member memberUpdate;

    @BeforeEach
    void setUp() {
        member = MEMBER;
        memberUpdate = MEMBER_UPDATE;
        memberResponse = MEMBER_RESPONSE;
        memberRequest = MEMBER_REQUEST;
        memberList = List.of(member);
        memberResponseList = List.of(memberResponse);
        memberResponsePage = new PageImpl<>(List.of(memberResponse));
    }

    @Test
    @DisplayName("Save - Should save with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void saveShouldSuccessfully() {
        when(mapper.map(any(), eq(MemberResponse.class))).thenReturn(memberResponse);
        MemberResponse response = service.saveUpdate(member);
        Assertions.assertNotNull(response);
        verify(repository, atLeastOnce()).save(member);
    }

    @Test
    @DisplayName("Update - Should update with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void updateShouldSuccessfully() {
        when(repository.findById(any())).thenReturn(Optional.of(member));
        when(mapper.map(any(), eq(MemberResponse.class))).thenReturn(memberResponse);
        MemberResponse response = service.saveUpdate(memberUpdate);
        Assertions.assertNotNull(response);
        verify(repository, atLeastOnce()).save(memberUpdate);
    }

    @Test
    @DisplayName("Find by Id - Should throw EntityNotFound member doesn't exist")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdThrowException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException resp = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(ID_EXIST);
        });
        Assertions.assertEquals(resp.getClass(), EntityNotFoundException.class);
        Assertions.assertEquals(resp.getMessage(), "Member" + CP.NOT_FOUND + "id: " + ID_EXIST);
        verify(repository, atLeastOnce()).findById(ID_EXIST);
    }

    @Test
    @DisplayName("Find by Id - Should fetch an member with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findByIdWithSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(member));
        when(mapper.map(any(), any())).thenReturn(memberResponse);
        MemberResponse response = service.findById(ID_EXIST);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getClass(), MemberResponse.class);
        verify(repository, atLeastOnce()).findById(any());

    }

    @Test
    @DisplayName("Find All - Should fetch all members listed with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllWithSuccess() {
        when(repository.findAll()).thenReturn(memberList);
        when(utils.mapListIntoDtoList(memberList, MemberResponse.class)).thenReturn(memberResponseList);
        List<MemberResponse> response = service.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0).getClass(), MemberResponse.class);
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Delete - Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
        when(repository.findById(any())).thenReturn(Optional.of(member));
        String response = Assertions.assertDoesNotThrow(() -> service.delete(any()));
        Assertions.assertNotNull(response);
        verify(repository).deleteById(any());
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