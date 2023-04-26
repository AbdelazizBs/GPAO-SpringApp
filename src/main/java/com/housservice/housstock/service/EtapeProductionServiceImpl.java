package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.dto.EtapeProductionDto;
import com.housservice.housstock.repository.EtapeProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EtapeProductionServiceImpl implements EtapeProductionService {

    private EtapeProductionRepository etapeProductionRepository;

    private SequenceGeneratorService sequenceGeneratorService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;


    @Autowired
    public EtapeProductionServiceImpl(EtapeProductionRepository etapeProductionRepository, SequenceGeneratorService sequenceGeneratorService,
                                      MessageHttpErrorProperties messageHttpErrorProperties) {
        this.etapeProductionRepository = etapeProductionRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }


    @Override
    public EtapeProductionDto buildEtapeProductionDtoFromEtapeProduction(EtapeProduction etapeProduction) {
        if (etapeProduction == null) {
            return null;
        }
        EtapeProductionDto etapeProductionDto = new EtapeProductionDto();
        etapeProductionDto.setId(etapeProduction.getId());
        etapeProductionDto.setNomEtape(etapeProduction.getNomEtape());
        etapeProductionDto.setTypeEtape(etapeProduction.getTypeEtape());
        return etapeProductionDto;
    }


    private EtapeProduction buildEtapeProductionFromEtapeProductionDto(EtapeProductionDto etapeProductionDto) {
        EtapeProduction etapeProduction = new EtapeProduction();
        etapeProduction.setId("" + sequenceGeneratorService.generateSequence(EtapeProduction.SEQUENCE_NAME));
        etapeProduction.setNomEtape(etapeProductionDto.getNomEtape());
        etapeProduction.setTypeEtape(etapeProductionDto.getTypeEtape());
        return etapeProduction;
    }

    @Override
    public List<EtapeProductionDto> getAllEtapeProduction() {
        List<EtapeProduction> listEtapeProduction = etapeProductionRepository.findAll();
        return listEtapeProduction.stream()
                .map(etapeProduction -> buildEtapeProductionDtoFromEtapeProduction(etapeProduction))
                .filter(etapeProduction -> etapeProduction != null)
                .collect(Collectors.toList());

    }

    @Override
    public List<String> getNomEtapes() {
        List<EtapeProduction> listEtapeProduction = etapeProductionRepository.findAll();
        return listEtapeProduction.stream().map(EtapeProduction::getNomEtape)
                .collect(Collectors.toList());


    }

    @Override
    public Optional<EtapeProduction> getEtapeProductionById(String etapeProductionId) {
        return etapeProductionRepository.findById(etapeProductionId);
    }

    @Override
    public void createNewEtapeProduction(@Valid EtapeProductionDto etapeProductionDto) {

        etapeProductionRepository.save(buildEtapeProductionFromEtapeProductionDto(etapeProductionDto));
    }

    @Override
    public void updateEtapeProduction(@Valid EtapeProductionDto etapeProductionDto) throws ResourceNotFoundException {

        EtapeProduction etapeProduction = getEtapeProductionById(etapeProductionDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), etapeProductionDto.getId())));

        etapeProduction.setNomEtape(etapeProductionDto.getNomEtape());
        etapeProduction.setTypeEtape(etapeProductionDto.getTypeEtape());

        etapeProductionRepository.save(etapeProduction);

    }

    @Override
    public void deleteEtapeProduction(EtapeProduction etapeProduction) {
        etapeProductionRepository.delete(etapeProduction);

    }

    @Override
    public String getIdEtapeProductionFromEtapeProductionDto(EtapeProductionDto etapeProductionDto) {
        if (etapeProductionDto == null) {
            return null;
        }

        String IdEtapeProduction = etapeProductionDto.getId();

        return IdEtapeProduction;
    }


}
