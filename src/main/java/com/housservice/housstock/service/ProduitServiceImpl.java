package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.ProduitMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.ProduitDto;
import com.housservice.housstock.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProduitServiceImpl implements ProduitService {
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final TypeProduitRepository typeProduitRepository;
    private final UniteVenteRepoitory uniteVenteRepoitory;
    private final ProduitRepository produitRepository;
    private final EtapeRepository etapeRepository;
    private final PictureRepository pictureRepository;


    public ProduitServiceImpl(MessageHttpErrorProperties messageHttpErrorProperties, PictureRepository pictureRepository, EtapeRepository etapeRepository, TypeProduitRepository typeProduitRepository, UniteVenteRepoitory uniteVenteRepoitory, ProduitRepository produitRepository) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.typeProduitRepository = typeProduitRepository;
        this.uniteVenteRepoitory = uniteVenteRepoitory;
        this.produitRepository = produitRepository;
        this.etapeRepository = etapeRepository;
        this.pictureRepository = pictureRepository;


    }

    @Override
    public void addProduit(ProduitDto produitDto) {
        try {
            if (produitRepository.existsProduitByDesignation(produitDto.getDesignation())) {
                throw new IllegalArgumentException( produitDto.getDesignation() + "  existe deja !!");
            }
            List<Picture> pictures1 = new ArrayList<>();
            produitDto.setPictures(pictures1);
            Produit produit = ProduitMapper.MAPPER.toProduit(produitDto);
            List<Picture> pictures2 = new ArrayList<>();
            produit.setPictures(pictures2);
            produit.setMiseEnVeille(false);

            produitRepository.save(produit);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void addEtape(String[] Etapes, String id) {
        try {
            Produit produit = produitRepository.findById(id).get();
            produit.setEtapes(Etapes);
            produitRepository.save(produit);
        } catch (Exception e) {
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
    public ResponseEntity<Map<String, Object>> getAllProduitvielle(int page, int size) {
        try {
            List<ProduitDto> produits = new ArrayList<ProduitDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> pageTuts;
            pageTuts = produitRepository.findProduitByMiseEnVeille(true,paging);
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
    public ResponseEntity<Map<String, Object>> getAllProduit(int page, int size) {
        try {
            List<ProduitDto> produits = new ArrayList<ProduitDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> pageTuts;
            pageTuts = produitRepository.findProduitByMiseEnVeille(false,paging);
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
    public List<Produit> getAllProduitByDesignation(String designation) {
        List<Produit> listes = produitRepository.findAllByDesignation(designation);
        return listes;
    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortProduit(int page, int size, String field, String order) {
        try {
            List<ProduitDto> produits;
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> pageTuts;
            if (order.equals("1")) {
                pageTuts = produitRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            } else {
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
    public List<String> getTypeProduit() {
        List<TypeProduit> papiers = typeProduitRepository.findAll();
        return papiers.stream()
                .map(TypeProduit::getNom)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getEtape() {
        List<Etape> etapes = etapeRepository.findAll();
        return etapes.stream()
                .map(Etape::getNomEtape)
                .collect(Collectors.toList());
    }

    @Override
    public String[] getEtapes(String id) {
        Optional<Produit> produit = produitRepository.findById(id);
        return produit.get().getEtapes();
    }

    @Override
    public List<String> getUniteVente() {
        List<UniteVente> unites = uniteVenteRepoitory.findAll();
        return unites.stream()
                .map(UniteVente::getNom)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllProduitByType(String type, int page, int size) {
        try {
            List<ProduitDto> listematieres = new ArrayList<ProduitDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> pageTuts;
            pageTuts = produitRepository.findAllByType(paging, type);
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


    @Override
    public void addphoto(MultipartFile[] images, String ref) {
        Produit produit = produitRepository.findProduitByRef(ref).get();
        List<Picture> pictures = new ArrayList<>();
        for (MultipartFile file : images) {
            Picture picture = new Picture();
            picture.setFileName(file.getOriginalFilename());
            System.out.println(picture.getFileName());
            picture.setType(file.getContentType());
            try {
                picture.setBytes(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            pictureRepository.save(picture);
            pictures.add(picture);
        }
        produit.setPictures(pictures);
        produitRepository.save(produit);

    }

    @Override
    public void removePictures(String idP) throws ResourceNotFoundException {
        Produit produit = produitRepository.findById(idP)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idP)));
        for (String id : produit.getPictures().stream().map(Picture::getId).collect(Collectors.toList())) {
            produitRepository.deleteById(id);
        }
        produit.getPictures().removeAll(produit.getPictures());
        produitRepository.save(produit);
    }

    @Override
    public void removePicture(String idPic) throws ResourceNotFoundException {
        Produit produit = produitRepository.findProduitByPicturesId(idPic)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPic)));

        produit.getPictures().removeIf(picture1 -> picture1.getId().equals(idPic));
        produitRepository.save(produit);
    }

    @Override
    public void miseEnVeille(String idArticle) throws ResourceNotFoundException {
        Produit produit = produitRepository.findById(idArticle)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idArticle)));
        produit.setMiseEnVeille(true);
        produitRepository.save(produit);
    }
    @Override
    public void Restaurer(String id) throws ResourceNotFoundException {
        System.out.println(id);
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        produit.setMiseEnVeille(false);
        produitRepository.save(produit);
    }
}