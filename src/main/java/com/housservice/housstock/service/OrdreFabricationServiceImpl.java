package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OrdreFabricationServiceImpl implements OrdreFabricationService {

    final SequenceGeneratorService sequenceGeneratorService;
    private final MessageHttpErrorProperties messageHttpErrorProperties;


    public OrdreFabricationServiceImpl(SequenceGeneratorService sequenceGeneratorService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }


}
