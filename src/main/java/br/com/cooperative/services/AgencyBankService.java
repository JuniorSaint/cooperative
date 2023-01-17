package br.com.cooperative.services;


import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.repositories.AgencyBankRepository;
import br.com.cooperative.repositories.BankRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class AgencyBankService {
    @Autowired
    private AgencyBankRepository repository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UsefulMethods usefulMethods;

    @Transactional
    public AgencyBankResponse saveUpdate(AgencyBank entity) {
        if (entity.getId() != null) {
            findById(entity.getId());
        }
        if (entity.getBank() == null) {
            throw new BadRequestException("It's not allowed register an agency bank without bank");
        }

        Bank responseBank = bankRepository.findById(entity.getBank().getId())
                .orElseThrow(() -> new EntityNotFoundException("Bank" + NOT_FOUND + " id:" + entity.getBank().getId()));

        if (entity.getCooperative() == null) {
            throw new BadRequestException("It's not allowed register an agency bank without a cooperative");
        }
        entity.setBank(responseBank);
        return mapper.map(repository.save(entity), AgencyBankResponse.class);
    }

    @Transactional(readOnly = true)
    public AgencyBankResponse findById(UUID id) {
        return repository.findById(id)
                .map(response -> mapper.map(response, AgencyBankResponse.class))
                .orElseThrow(() -> new EntityNotFoundException("Agency bank" + NOT_FOUND + "id: " + id));
    }

    @Transactional(readOnly = true)
    public List<AgencyBankResponse> findAll() {
        return usefulMethods.mapListIntoDtoList(repository.findAll(), AgencyBankResponse.class);
    }

    @Transactional(readOnly = true)
    public Page<AgencyBankResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        return usefulMethods.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), pageable), AgencyBankResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank" + NOT_FOUND + "id: " + id));
        repository.deleteById(id);
        return "Bank" + DELETE_MESSAGE;
    }
}
