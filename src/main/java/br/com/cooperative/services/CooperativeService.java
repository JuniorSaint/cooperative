package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.CooperativeResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.request.CooperativeRequest;
import br.com.cooperative.repositories.CooperativeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class CooperativeService {
    @Autowired
    private CooperativeRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Utils utils;

    @Transactional
    public CooperativeResponse saveUpdate(CooperativeRequest request) {
        if (request.getId() != null) {
            findById(request.getId());
        }
        Cooperative cooperative = mapper.map(request, Cooperative.class);
        if (request.getCooperativeType().toString().equals("BRANCH")) {
            if (request.getMatrix() == null) {
                throw new BadRequestException("Matrix it's not allowed null, when is registering a branch");
            }
            Optional<Cooperative> response = repository.findById(request.getMatrix().getId());
            if (response.isEmpty()) {
                throw new EntityNotFoundException("Matriz" + NOT_FOUND + " id: " + request.getMatrix().getId());
            }
            if (response.get().getCooperativeType().toString().equals("BRANCH")) {
                throw new BadRequestException("The matrix that you're trying to register is a branch. - " + response.get().getName());
            }
            cooperative.setMatrix(response.get());
        }
        return mapper.map(repository.save(cooperative), CooperativeResponse.class);
    }

    @Transactional(readOnly = true)
    public CooperativeResponse findById(UUID id) {
        Optional<Cooperative> response = repository.findById(id);
        if (response.isEmpty())
            throw new EntityNotFoundException("Cooperative" + NOT_FOUND + "id: " + id);
        return mapper.map(response.get(), CooperativeResponse.class);
    }

    @Transactional(readOnly = true)
    public List<CooperativeResponse> findAll() {
        return utils.mapListIntoDtoList(repository.findAll(), CooperativeResponse.class);
    }

    @Transactional(readOnly = true)
    public Page<CooperativeResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        return utils.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), pageable), CooperativeResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        findById(id);
        repository.deleteById(id);
        return "Cooperative" + DELETE_MESSAGE;
    }
}
