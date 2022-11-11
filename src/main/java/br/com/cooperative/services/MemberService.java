package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.DataBaseException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.MemberResponse;
import br.com.cooperative.models.entities.Member;
import br.com.cooperative.models.request.MemberRequest;
import br.com.cooperative.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class MemberService {
    @Autowired
    private MemberRepository repository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Utils utils;

    @Transactional
    public MemberResponse saveUpdate(MemberRequest request) {
        if (request.getId() != null) {
            findById(request.getId());
        }
        return mapper.map(repository.save(mapper.map(request, Member.class)), MemberResponse.class);
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        Optional<Member> response = repository.findById(id);
        if (response.get() == null)
            throw new EntityNotFoundException("Member" + NOT_FOUND + "id: " + id);
        return mapper.map(response.get(), MemberResponse.class);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return utils.mapListIntoDtoList(repository.findAll(), MemberResponse.class);
    }

    @Transactional
    public String delete(Long id) {
        try {
            findById(id);
            repository.deleteById(id);
            return "Member" + DELETE_MESSAGE;
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }
}
