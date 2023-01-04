package br.com.cooperative.services;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.CooperativeResponse;
import br.com.cooperative.models.entities.Cooperative;
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
    private UsefulMethods usefulMethods;

    @Transactional
    public CooperativeResponse saveUpdate(Cooperative entity) {
        if (entity.getId() != null) {
            findById(entity.getId());
        }
        if (entity.getCooperativeType().toString().equals("BRANCH")) {
            if (entity.getMatrix() == null) {
                throw new BadRequestException("Matrix it's not allowed null, when is registering a branch");
            }
            Optional<Cooperative> response = repository.findById(entity.getMatrix().getId());
            if (response.isEmpty()) {
                throw new EntityNotFoundException("Matrix" + NOT_FOUND + " id: " + entity.getMatrix().getId());
            }
            if (response.get().getCooperativeType().toString().equals("BRANCH")) {
                throw new BadRequestException("The matrix that you're trying to register is a branch. - " + response.get().getName());
            }
            entity.setMatrix(response.get());
        }
        return mapper.map(repository.save(entity), CooperativeResponse.class);
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
        return usefulMethods.mapListIntoDtoList(repository.findAll(), CooperativeResponse.class);
    }

    @Transactional(readOnly = true)
    public Page<CooperativeResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        return usefulMethods.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), pageable), CooperativeResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        findById(id);
        repository.deleteById(id);
        return "Cooperative" + DELETE_MESSAGE;
    }
}
