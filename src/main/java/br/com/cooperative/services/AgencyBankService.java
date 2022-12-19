package br.com.cooperative.services;

import br.com.cooperative.configs.Utils;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.models.request.AgencyBankRequest;
import br.com.cooperative.repositories.AgencyBankRepository;
import br.com.cooperative.repositories.BankRepository;
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
public class AgencyBankService {
    @Autowired
    private AgencyBankRepository repository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Utils utils;

    @Transactional
    public AgencyBankResponse saveUpdate(AgencyBankRequest request) {
        if (request.getId() != null) {
            findById(request.getId());
        }
        if (request.getBank().getId() == null) {
            throw new BadRequestException("It's not allowed register an agency bank without bank");
        }
        Optional<Bank> responseBank = bankRepository.findById(request.getBank().getId());
        if (responseBank.isEmpty()) {
            throw new EntityNotFoundException("Bank" + NOT_FOUND + " id:" + request.getBank().getId());
        }
        if (request.getCooperative() == null) {
            throw new BadRequestException("It's not allowed register an agency bank without a cooperative");
        }
        AgencyBank agencyBank = mapper.map(request, AgencyBank.class);
        responseBank.ifPresent(agencyBank::setBank);
        return mapper.map(repository.save(agencyBank), AgencyBankResponse.class);
    }

    @Transactional(readOnly = true)
    public AgencyBankResponse findById(UUID id) {
        Optional<AgencyBank> response = repository.findById(id);
        if (response.isEmpty()) {
            throw new EntityNotFoundException("Agency bank" + NOT_FOUND + "id: " + id);
        }
        return mapper.map(response.get(), AgencyBankResponse.class);
    }

    @Transactional(readOnly = true)
    public List<AgencyBankResponse> findAll() {
        return utils.mapListIntoDtoList(repository.findAll(), AgencyBankResponse.class);
    }

    @Transactional(readOnly = true)
    public Page<AgencyBankResponse> findAllWithPageAndSearch(String search, Pageable pageable) {
        return utils.mapEntityPageIntoDtoPage(repository.findBySearch(search.trim(), pageable), AgencyBankResponse.class);
    }

    @Transactional
    public String delete(UUID id) {
        findById(id);
        repository.deleteById(id);
        return "Agency bank" + DELETE_MESSAGE;
    }
}
