package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.MachineMapper;
import com.housservice.housstock.mapper.ProduitMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.model.dto.ProduitDto;
import com.housservice.housstock.repository.ProduitRepository;
import com.housservice.housstock.repository.TypeProduitRepository;
import com.housservice.housstock.repository.UniteVenteRepoitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProduitServiceImpl implements ProduitService{
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final TypeProduitRepository typeProduitRepository;
    private final UniteVenteRepoitory uniteVenteRepoitory;
    private final ProduitRepository produitRepository;
    

    public ProduitServiceImpl(MessageHttpErrorProperties messageHttpErrorProperties, TypeProduitRepository typeProduitRepository, UniteVenteRepoitory uniteVenteRepoitory, ProduitRepository produitRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.typeProduitRepository = typeProduitRepository;
        this.uniteVenteRepoitory = uniteVenteRepoitory;
        this.produitRepository = produitRepository;
    }
    @Override
    public void addProduit(ProduitDto produitDto) {
        try
        {


            if (produitRepository.existsProduitByDesignation(produitDto.getDesignation())) {
                throw new IllegalArgumentException(	" cin " + produitDto.getDesignation() +  "  existe deja !!");
            }

            Produit produit = ProduitMapper.MAPPER.toProduit(produitDto);

            produitRepository.save(produit);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void updateProduit(ProduitDto produitDto, String idProduit) throws ResourceNotFoundException {
        Produit produit = produitRepository.findById(idProduit)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), produitDto.getId())));
        produit.setType(produitDto.getType());
        produit.setDesignation(produitDto.getDesignation());
        produit.setRef(produitDto.getRef());
        produit.setDateCreation(produitDto.getDateCreation());




        produitRepository.save(produit);

    }

    @Override
    public void deleteProduit(String produitId) {
        produitRepository.deleteById(produitId);


    }
    @Override
    public ResponseEntity<Map<String, Object>> getAllProduit(int page, int size) {
        try {
            List<ProduitDto> produits = new ArrayList<ProduitDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> pageTuts;
            pageTuts = produitRepository.findAll(paging);
            produits = pageTuts.getContent().stream().map(produit -> {
                return ProduitMapper.MAPPER.toProduitDto(produit);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("produits", produits);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size) {
        try {

            List<ProduitDto> produits;
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> pageTuts;
            pageTuts = produitRepository.findProduitByTextToFind(textToFind, paging);
            produits = pageTuts.getContent().stream().map(produit -> {
                return ProduitMapper.MAPPER.toProduitDto(produit);
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("produits", produits);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Produit getProduitByDesignation(String designation) throws ResourceNotFoundException {
        return produitRepository.findByDesignation(designation);
    }
    @Override
    public List<Produit> getAllProduitByDesignation(String designation)   {
        List<Produit> listes = produitRepository.findAllByDesignation(designation);
        return listes;
    }
    @Override
    public ResponseEntity<Map<String, Object>> onSortProduit(int page, int size, String field, String order) {
        try {
            List<ProduitDto> produits ;
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> pageTuts;
            if (order.equals("1")){
                pageTuts = produitRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            else {
                pageTuts = produitRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            produits = pageTuts.getContent().stream().map(produit -> {
                return ProduitMapper.MAPPER.toProduitDto(produit);
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("produits", produits);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Override
    public List<String> getTypeProduit()   {
        List<TypeProduit> papiers = typeProduitRepository.findAll();
        return papiers.stream()
                .map(TypeProduit::getNom)
                .collect(Collectors.toList());
    }
    @Override
    public List<String> getUniteVente()   {
        List<UniteVente> unites = uniteVenteRepoitory.findAll();
        return unites.stream()
                .map(UniteVente::getNom)
                .collect(Collectors.toList());
    }

    @Override
    public  ResponseEntity<Map<String, Object>> getAllProduitByType(String type, int page, int size)   {
        try {
            List<ProduitDto> listematieres = new ArrayList<ProduitDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> pageTuts;
            pageTuts =  produitRepository.findAllByType(paging, type);
            listematieres = pageTuts.getContent().stream().map(listematiere -> ProduitMapper.MAPPER.toProduitDto(listematiere)).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("listematieres", listematieres);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
