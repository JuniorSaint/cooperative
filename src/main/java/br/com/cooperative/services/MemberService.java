package br.com.cooperative.services;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.MemberResponse;
import br.com.cooperative.models.entities.Member;
import br.com.cooperative.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class MemberService {
    @Autowired
    private MemberRepository repository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UsefulMethods usefulMethods;

    @Transactional
    public MemberResponse saveUpdate(Member entity) {
        if (entity.getId() != null) {
            findById(entity.getId());
        }
        return mapper.map(repository.save(entity), MemberResponse.class);
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(UUID id) {
        return repository.findById(id)
                .map(result -> mapper.map(result, MemberResponse.class))
                .orElseThrow(() -> new EntityNotFoundException("Member" + NOT_FOUND + "id: " + id));
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return usefulMethods.mapListIntoDtoList(repository.findAll(), MemberResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        return repository.findById(id)
                .map(result -> {
                    repository.deleteById(id);
                    return "Member" + DELETE_MESSAGE;
                })
                .orElseThrow(() -> new EntityNotFoundException("Member" + NOT_FOUND + "id: " + id));
    }
}
