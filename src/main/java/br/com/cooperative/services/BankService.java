package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.DataBaseException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.Response.BankResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.models.request.AgencyBankRequest;
import br.com.cooperative.models.request.BankRequest;
import br.com.cooperative.repositories.AgencyBankRepository;
import br.com.cooperative.repositories.BankRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.configs.CP.NOT_FOUND;

@Service
public class BankService {
    @Autowired
    private BankRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Utils utils;

    @Transactional
    public BankResponse saveUpdate(BankRequest request) {
        if (request.getId() != null) {
            findById(request.getId());
        }
        return mapper.map(repository.save(mapper.map(request, Bank.class)), BankResponse.class);
    }

    @Transactional(readOnly = true)
    public BankResponse findById(UUID id) {
       Optional<Bank> response = repository.findById(id);
        if (response.isEmpty())
            throw new EntityNotFoundException("Bank" + NOT_FOUND + "id: " + id);
        return mapper.map(response.get(), BankResponse.class);
    }

    @Transactional(readOnly = true)
    public List<BankResponse> findAll() {
        return utils.mapListIntoDtoList(repository.findAll(), BankResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        try {
            findById(id);
            repository.deleteById(id);
            return "Bank" + DELETE_MESSAGE;
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }
}
