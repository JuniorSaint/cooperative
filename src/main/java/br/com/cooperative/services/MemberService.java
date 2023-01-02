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
import java.util.Optional;
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
    private UsefulMethods utils;

    @Transactional
    public MemberResponse saveUpdate(Member entity) {
        if (entity.getId() != null) {
            findById(entity.getId());
        }
        return mapper.map(repository.save(entity), MemberResponse.class);
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(UUID id) {
        Optional<Member> response = repository.findById(id);
        if (response.isEmpty())
            throw new EntityNotFoundException("Member" + NOT_FOUND + "id: " + id);
        return mapper.map(response.get(), MemberResponse.class);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return utils.mapListIntoDtoList(repository.findAll(), MemberResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        findById(id);
        repository.deleteById(id);
        return "Member" + DELETE_MESSAGE;
    }
}
