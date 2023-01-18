package br.com.cooperative.services;

import br.com.cooperative.configs.UsefulMethods;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.BankResponse;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.repositories.BankRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private UsefulMethods usefulMethods;

    @Transactional
    public BankResponse saveUpdate(Bank entity) {
        BankResponse verifyIfBankExist = entity.getId() != null ? findById(entity.getId()) : null;

        return mapper.map(repository.save(entity), BankResponse.class);
    }

    @Transactional(readOnly = true)
    public BankResponse findById(UUID id) {
        return repository.findById(id)
                .map(result -> mapper.map(result, BankResponse.class))
                .orElseThrow(() -> new EntityNotFoundException("Bank" + NOT_FOUND + "id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Bank> findAllBanks() {
        return repository.findAll();
//        return usefulMethods.mapListIntoDtoList(repository.findAll(), BankResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank" + NOT_FOUND + "id: " + id));
        repository.deleteById(id);
        return "Bank" + DELETE_MESSAGE;
    }
}
