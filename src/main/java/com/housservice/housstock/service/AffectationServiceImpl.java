package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.AffectationMapper;
import com.housservice.housstock.mapper.PrixAchatMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.AffectationDto;
import com.housservice.housstock.model.dto.PrixAchatDto;
import com.housservice.housstock.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AffectationServiceImpl implements AffectationService {
    private final AffectationRepository affectationRepository;
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final FournisseurRepository fournisseurRepository;
    private final ListeMatiereRepository matiereRepository;
    private final DeviseRepository deviseRepository;
    private final UniteAchatRepository uniteAchatRepository;
    private final PrixAchatRepository prixAchatRepository;



    @Autowired
    public AffectationServiceImpl(AffectationRepository affectationRepository,PrixAchatRepository prixAchatRepository,UniteAchatRepository uniteAchatRepository,DeviseRepository deviseRepository, MessageHttpErrorProperties messageHttpErrorProperties,FournisseurRepository fournisseurRepository,ListeMatiereRepository matiereRepository) {
        this.affectationRepository = affectationRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.fournisseurRepository = fournisseurRepository;
        this.matiereRepository = matiereRepository;
        this.deviseRepository = deviseRepository;
        this.uniteAchatRepository = uniteAchatRepository;
        this.prixAchatRepository = prixAchatRepository;

    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortActiveAffectation(int page, int size, String field, String order) {
        try {
            List<AffectationDto> affectationDtos ;
            Page<Affectation> pageTuts;
            if (order.equals("1")){
                pageTuts = affectationRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            else {
                pageTuts = affectationRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            affectationDtos = pageTuts.getContent().stream().map(affectation -> {
                return AffectationMapper.MAPPER.toAffectationDto(affectation);
            }).collect(Collectors.toList());
            affectationDtos =affectationDtos.stream().collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("affectations", affectationDtos);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<Map<String, Object>> getAllAffectation(int page, int size,String id) {
        try {
            List<AffectationDto> affectations = new ArrayList<AffectationDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Affectation> pageTuts;
            pageTuts = affectationRepository.findAffectationByIdmatiere(id, paging);
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
    public ResponseEntity<Map<String, Object>> getAllAffectationFrs(int page, int size,String id) {
        try {
            List<AffectationDto> affectations = new ArrayList<AffectationDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Affectation> pageTuts;
            Fournisseur fournisseur = fournisseurRepository.findById(id).get();
            pageTuts = affectationRepository.findAffectationByListFournisseur(fournisseur.getRaisonSocial(), paging);
            affectations = pageTuts.getContent().stream().map(affectation -> {
                return AffectationMapper.MAPPER.toAffectationDto(affectation);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            System.out.println(affectations.get(0).getDestination()+"azeaz");
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
    public void createNewAffectation(AffectationDto affectationDto) throws ResourceNotFoundException {
        ListeMatiere matiere = matiereRepository.findById(affectationDto.getIdmatiere()).get();
        affectationDto.setType(matiere.getType());
        List<PrixAchat> prixAchat = new ArrayList<>();
        if (affectationDto.getPrixAchat()==null) {
            affectationDto.setPrixAchat(prixAchat);
            Affectation affectation = AffectationMapper.MAPPER.toAffectation(affectationDto);
            affectation.setUnite(affectationDto.getUnite());
            affectationRepository.save(affectation);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {
        try {

            List<AffectationDto> affectations;
            Pageable paging = PageRequest.of(page, size);
            Page<Affectation> pageTuts;
            pageTuts = affectationRepository.findAffectationByTextToFind(textToFind, paging);
            affectations = pageTuts.getContent().stream().map(affectation -> {
                return AffectationMapper.MAPPER.toAffectationDto(affectation);
            }).collect(Collectors.toList());
            affectations= affectations.stream().collect(Collectors.toList());
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
    public void updateAffectation(AffectationDto affectationDto,String idAffectation) throws ResourceNotFoundException {
        Affectation affectation = affectationRepository.findById(idAffectation)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idAffectation)));
        if (!affectation.getId().equals(affectationDto.getId())) {
            throw new IllegalArgumentException("Error Id!!");
        }
        affectation.setDateeffect(affectationDto.getDateeffect());
        affectation.setDevises(affectationDto.getDevises());
        affectation.setPrix(affectationDto.getPrix());
        affectation.setMinimumachat(affectationDto.getMinimumachat());
        affectation.setRef(affectationDto.getRef());
        affectation.setListFournisseur(affectationDto.getListFournisseur());
        affectation.setUnite(affectationDto.getUnite());
        affectationRepository.save(affectation);
    }


    @Override
    public void deleteAffectation(Affectation affectation) {
        affectationRepository.delete(affectation);
    }

    
    @Override
    public List<String> getFournisseur()   {
        List<Fournisseur> affectations = fournisseurRepository.findAll();
        return affectations.stream()
                .map(Fournisseur::getRaisonSocial)
                .collect(Collectors.toList());
    }
    @Override
    public String getFournisseurRef(String id)   {
        Optional<Fournisseur> affectations = fournisseurRepository.findFournisseurByRaisonSocial(id);
        String refFrs=affectations.get().getRefFournisseurIris();
        return refFrs;
    }

    public Optional<ListeMatiere> getMatiere(String id) {
        Optional<ListeMatiere> affectations = matiereRepository.findById(id);
        return affectations;
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
    public void addPrixAchatAffectation(PrixAchatDto prixAchatDto, String idAffectation) throws ResourceNotFoundException {
        Affectation affectation =  getAffectationById(idAffectation)
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
        Affectation affectation =  affectationRepository.findAffectationByPrixAchatId(idPrixAchat)
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
        PrixAchat prixAchat = prixAchatRepository.findById(idPrixAchat)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPrixAchat)));
        List<Affectation> affectations = affectationRepository.findByPrixAchat(prixAchat);

        for (Affectation affectation : affectations) {
            affectation.getPrixAchat().removeIf(c -> c.equals(prixAchat));
            affectationRepository.save(affectation);
        }
        prixAchatRepository.deleteById(idPrixAchat);
    }
    public Optional<Fournisseur> getFournisseurid(String id){
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return fournisseur;
    }


}
