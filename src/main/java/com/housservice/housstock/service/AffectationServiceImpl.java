package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.AffectationMapper;
import com.housservice.housstock.mapper.AffectationMapper;
import com.housservice.housstock.mapper.AffectationMapper;
import com.housservice.housstock.mapper.PrixAchatMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.PrixAchat;
import com.housservice.housstock.model.dto.AffectationDto;
import com.housservice.housstock.model.dto.AffectationDto;
import com.housservice.housstock.model.dto.AffectationDto;
import com.housservice.housstock.model.dto.PrixAchatDto;
import com.housservice.housstock.repository.AffectationRepository;
import com.housservice.housstock.repository.DeviseRepository;
import com.housservice.housstock.repository.PrixAchatRepository;
import com.housservice.housstock.repository.UniteAchatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AffectationServiceImpl implements AffectationService{
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final AffectationRepository affectationRepository;
    private final PrixAchatRepository prixAchatRepository;
    private final DeviseRepository deviseRepository;
    private final UniteAchatRepository uniteAchatRepository;

    public AffectationServiceImpl(MessageHttpErrorProperties messageHttpErrorProperties, AffectationRepository affectationRepository, PrixAchatRepository prixAchatRepository, DeviseRepository deviseRepository, UniteAchatRepository uniteAchatRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.affectationRepository = affectationRepository;
        this.prixAchatRepository = prixAchatRepository;
        this.deviseRepository = deviseRepository;
        this.uniteAchatRepository = uniteAchatRepository;
    }

    @Override
    public void addPrixAchatAffectation(PrixAchatDto prixAchatDto, String idAffectation) throws ResourceNotFoundException {
        Affectation affectation = (Affectation) getAffectationById(idAffectation)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idAffectation)));
        List<PrixAchat> prixAchats = new ArrayList<>();
        PrixAchat prixAchat1 = PrixAchatMapper.MAPPER.toPrixAchat(prixAchatDto);
        if(affectation.getPrixAchat()==null){
            prixAchats.add(prixAchat1);
            prixAchatRepository.save(prixAchat1);
            affectation.setPrixAchat(prixAchats);
            affectationRepository.save(affectation);
        }
        prixAchatRepository.save(prixAchat1);
        prixAchats.add(prixAchat1);
        prixAchats.addAll(affectation.getPrixAchat());
        affectation.setPrixAchat(prixAchats);
        affectationRepository.save(affectation);
        
    }

    private Optional<Affectation> getAffectationById(String idAffectation) {
        return affectationRepository.findById(idAffectation);
    }


    @Override
    public void updatePrixAchatAffectation(PrixAchatDto prixAchatDto, String idPrixAchat) throws ResourceNotFoundException {
        Affectation affectation = (Affectation) affectationRepository.findAffectationByPrixAchatId(idPrixAchat)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  prixAchatDto)));
        PrixAchat prixAchatToUpdate = affectation.getPrixAchat().stream()
                .filter(prixAchat -> prixAchat.getId().equals(idPrixAchat))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  prixAchatDto.getId())));
        prixAchatToUpdate.setPrix(prixAchatDto.getPrix());
        prixAchatToUpdate.setDevise(prixAchatDto.getDevise());
        prixAchatToUpdate.setDateEffet(prixAchatDto.getDateEffet());

        prixAchatRepository.save(prixAchatToUpdate);
        affectationRepository.save(affectation);
    }

    @Override
    public void deletePrixAchatAffectation(String idPrixAchat) throws ResourceNotFoundException {
        Affectation affectation = affectationRepository.findAffectationByIdPrixAchat(idPrixAchat)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPrixAchat)));
        PrixAchat prixAchat = prixAchatRepository.findById(idPrixAchat)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPrixAchat)));
        List<PrixAchat> prixAchatList = affectation.getPrixAchat();
        prixAchatList.removeIf(c -> c.equals(prixAchat));
        affectation.setPrixAchat(prixAchatList);
        affectationRepository.save(affectation);
        prixAchatRepository.deleteById(idPrixAchat);

    }

    @Override
    public void addAffectation(AffectationDto affectationDto) {
        Affectation affectation = AffectationMapper.MAPPER.toAffectation(affectationDto);
       
        affectationRepository.save(affectation);

    }

    @Override
    public void updateAffectation(AffectationDto affectationDto, String idAffectation) throws ResourceNotFoundException {
        Affectation affectation = affectationRepository.findById(idAffectation)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), affectationDto.getId())));
        affectation.setMinimunAchat(affectationDto.getMinimunAchat());
        affectation.setUniteAchat(affectationDto.getUniteAchat());

    }

   @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size) {
        try {

            List<AffectationDto> affectations;
            Pageable paging = PageRequest.of(page, size);
            Page<Affectation> pageTuts;
            pageTuts = affectationRepository.findAffectationByTextToFind(textToFind, paging);
            affectations = pageTuts.getContent().stream().map(affectation -> {
                return AffectationMapper.MAPPER.toAffectationDto(affectation);
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("affectations", affectations);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortAffectation(int page, int size, String field, String order) {
        try {
            List<AffectationDto> affectations ;
            Pageable paging = PageRequest.of(page, size);
            Page<Affectation> pageTuts;
            if (order.equals("1")){
                pageTuts = affectationRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            else {
                pageTuts = affectationRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            affectations = pageTuts.getContent().stream().map(affectation -> {
                return AffectationMapper.MAPPER.toAffectationDto(affectation);
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("affectations", affectations);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<String> getDevise() {
        List<Devise> devises = deviseRepository.findAll();
        return devises.stream()
                .map(Devise::getNom)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getUniteAchat() {
        List<UniteAchat> achats = uniteAchatRepository.findAll();
        return achats.stream()
                .map(UniteAchat::getNom)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Affectation> getAffectationByIdMatiere(String idMatiere) {
        return affectationRepository.findByIdMatiere(idMatiere);
    }
}
