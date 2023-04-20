package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.AffectationProduitMapper;
import com.housservice.housstock.mapper.PrixVenteMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.AffectationProduitDto;
import com.housservice.housstock.model.dto.PrixVenteDto;
import com.housservice.housstock.repository.*;
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
public class AffectationProduitServiceImpl implements AffectationProduitService{
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final ProduitRepository produitRepository;
    private final TypeProduitRepository typeProduitRepository;
    private final UniteVenteRepoitory uniteVenteRepoitory;
    private final PrixVenteRepository prixVenteRepository;
    private final AffectationProduitRepository affectationProduitRepository;
    private final DeviseRepository deviseRepository;

    public AffectationProduitServiceImpl(MessageHttpErrorProperties messageHttpErrorProperties, ProduitRepository produitRepository, TypeProduitRepository typeProduitRepository, UniteVenteRepoitory uniteVenteRepoitory, PrixVenteRepository prixVenteRepository, AffectationProduitRepository affectationProduitRepository, DeviseRepository deviseRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.produitRepository = produitRepository;

        this.typeProduitRepository = typeProduitRepository;
        this.uniteVenteRepoitory = uniteVenteRepoitory;
        this.prixVenteRepository = prixVenteRepository;
        this.affectationProduitRepository = affectationProduitRepository;
        this.deviseRepository = deviseRepository;
    }
    @Override
    public void addPrixVenteAffectationProduit(PrixVenteDto prixVenteDto, String idAffectationProduit) throws ResourceNotFoundException {
        AffectationProduit affectationProduit = (AffectationProduit) getAffectationProduitById(idAffectationProduit)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idAffectationProduit)));
        List<PrixVente> prixVentes = new ArrayList<>();
        PrixVente prixVente1 = PrixVenteMapper.MAPPER.toPrixVente(prixVenteDto);
        if(affectationProduit.getPrixVente()==null){
            prixVentes.add(prixVente1);
            prixVenteRepository.save(prixVente1);
            affectationProduit.setPrixVente(prixVentes);
            affectationProduitRepository.save(affectationProduit);
        }
        prixVenteRepository.save(prixVente1);
        prixVentes.add(prixVente1);
        prixVentes.addAll(affectationProduit.getPrixVente());
        affectationProduit.setPrixVente(prixVentes);
        affectationProduitRepository.save(affectationProduit);

    }

    private Optional<AffectationProduit> getAffectationProduitById(String idAffectationProduit) {
        return affectationProduitRepository.findById(idAffectationProduit);
    }


    @Override
    public void updatePrixVenteAffectationProduit(PrixVenteDto prixVenteDto, String idPrixVente) throws ResourceNotFoundException {
        AffectationProduit affectationProduit = (AffectationProduit) affectationProduitRepository.findAffectationProduitByPrixVenteId(idPrixVente)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  prixVenteDto)));
        PrixVente prixVenteToUpdate = affectationProduit.getPrixVente().stream()
                .filter(prixVente -> prixVente.getId().equals(idPrixVente))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  prixVenteDto.getId())));
        prixVenteToUpdate.setPrix(prixVenteDto.getPrix());
        prixVenteToUpdate.setDevise(prixVenteDto.getDevise());
        prixVenteToUpdate.setDateEffet(prixVenteDto.getDateEffet());

        prixVenteRepository.save(prixVenteToUpdate);
        affectationProduitRepository.save(affectationProduit);
    }

    @Override
    public void deletePrixVenteAffectationProduit(String idPrixVente) throws ResourceNotFoundException {
        AffectationProduit affectationProduit = (AffectationProduit) affectationProduitRepository.findAffectationProduitByIdPrixVente(idPrixVente)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPrixVente)));
        PrixVente prixVente = prixVenteRepository.findById(idPrixVente)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPrixVente)));
        List<PrixVente> prixVenteList = affectationProduit.getPrixVente();
        prixVenteList.removeIf(c -> c.equals(prixVente));
        affectationProduit.setPrixVente(prixVenteList);
        affectationProduitRepository.save(affectationProduit);
        prixVenteRepository.deleteById(idPrixVente);

    }

    @Override
    public void addAffectationProduit(AffectationProduitDto affectationProduitDto) {
        AffectationProduit affectationProduit = AffectationProduitMapper.MAPPER.toAffectationProduit(affectationProduitDto);

        affectationProduitRepository.save(affectationProduit);

    }

    @Override
    public void updateAffectationProduit(AffectationProduitDto affectationProduitDto, String idAffectationProduit) throws ResourceNotFoundException {
        AffectationProduit affectationProduit = affectationProduitRepository.findById(idAffectationProduit)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), affectationProduitDto.getId())));
        affectationProduit.setMinimunVente(affectationProduitDto.getMinimunAchat());
        affectationProduit.setUniteVente(affectationProduitDto.getUniteAchat());

    }

    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size) {
        try {

            List<AffectationProduitDto> affectationProduits;
            Pageable paging = PageRequest.of(page, size);
            Page<AffectationProduit> pageTuts;
            pageTuts = affectationProduitRepository.findAffectationPrFoduitByTextToFind(textToFind, paging);
            affectationProduits = pageTuts.getContent().stream().map(affectationProduit -> {
                return AffectationProduitMapper.MAPPER.toAffectationProduitDto(affectationProduit);
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("affectationProduits", affectationProduits);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortAffectationProduit(int page, int size, String field, String order) {
        try {
            List<AffectationProduitDto> affectationProduits ;
            Pageable paging = PageRequest.of(page, size);
            Page<AffectationProduit> pageTuts;
            if (order.equals("1")){
                pageTuts = affectationProduitRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            else {
                pageTuts = affectationProduitRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            affectationProduits = pageTuts.getContent().stream().map(affectationProduit -> {
                return AffectationProduitMapper.MAPPER.toAffectationProduitDto(affectationProduit);
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("affectationProduits", affectationProduits);
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
        List<UniteVente> ventes = uniteVenteRepoitory.findAll();
        return ventes.stream()
                .map(UniteVente::getNom)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AffectationProduit> getAffectationProduitByIdProduit(String idProduit) {
        return affectationProduitRepository.findByIdProduit(idProduit);
    }
}
