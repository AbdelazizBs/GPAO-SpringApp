package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.ListeMatiereMapper;
import com.housservice.housstock.mapper.UniteConsommationMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.ListeMatiere;
import com.housservice.housstock.model.MatierePrimaire;
import com.housservice.housstock.model.TypePapier;
import com.housservice.housstock.model.UniteConsommation;
import com.housservice.housstock.model.dto.ListeMatiereDto;
import com.housservice.housstock.repository.ListeMatiereRepository;
import com.housservice.housstock.repository.MatierePrimaireRepository;
import com.housservice.housstock.repository.TypePapierRepository;
import com.housservice.housstock.repository.UniteConsommationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ListeMatiereServiceImpl implements ListeMatiereService{
    private final ListeMatiereRepository listeMatiereRepository;
    private final MatierePrimaireRepository matierePrimaireRepository;
    private final TypePapierRepository typePapierRepository;
    private final UniteConsommationRepository uniteConsommationRepository;


    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public ListeMatiereServiceImpl(ListeMatiereRepository listeMatiereRepository, MatierePrimaireRepository matierePrimaireRepository, TypePapierRepository typePapierRepository,  UniteConsommationRepository uniteConsommationRepository, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.listeMatiereRepository = listeMatiereRepository;
        this.matierePrimaireRepository = matierePrimaireRepository;
        this.typePapierRepository = typePapierRepository;
        this.uniteConsommationRepository = uniteConsommationRepository;

        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }

    @Override
    public void addListeMatiere(ListeMatiereDto listeMatiereDto) {
        try
        {
          

            if (listeMatiereRepository.existsListeMatiereByDesignation(listeMatiereDto.getDesignation())) {
                throw new IllegalArgumentException(	" cin " + listeMatiereDto.getDesignation() +  "  existe deja !!");
            }
            
            ListeMatiere listeMatiere = ListeMatiereMapper.MAPPER.toListeMatiere(listeMatiereDto);
            
            listeMatiereRepository.save(listeMatiere);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void updateListeMatiere(ListeMatiereDto listeMatiereDto, String idListeMatiere) throws ResourceNotFoundException {
        ListeMatiere listeMatiere = listeMatiereRepository.findById(idListeMatiere)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), listeMatiereDto.getId())));
        listeMatiere.setCouleur(listeMatiereDto.getCouleur());
        listeMatiere.setDiametre(listeMatiereDto.getDiametre());
        listeMatiere.setLargeur(listeMatiereDto.getLargeur());
        listeMatiere.setGrammage(listeMatiereDto.getGrammage());
        listeMatiere.setEmbout(listeMatiereDto.getEmbout());
        listeMatiere.setLaize(listeMatiereDto.getLaize());
        listeMatiere.setLongueur(listeMatiereDto.getLongueur());
        listeMatiere.setMinimumStock(listeMatiereDto.getMinimumStock());
        listeMatiere.setTypeVernis(listeMatiereDto.getTypeVernis());
        listeMatiere.setTypePapier(listeMatiereDto.getTypePapier());
        listeMatiere.setTypePvc(listeMatiereDto.getTypePvc());
        listeMatiere.setTypePelliculage(listeMatiereDto.getTypePelliculage());
        listeMatiere.setTypeCordon(listeMatiereDto.getTypeCordon());
        listeMatiere.setTypeEncre(listeMatiereDto.getTypeVernis());
        listeMatiere.setDesignation(listeMatiereDto.getTypeVernis());
        listeMatiere.setUniteConsommation(listeMatiereDto.getUniteConsommation());
        listeMatiere.setMicron(listeMatiereDto.getMicron());



        listeMatiereRepository.save(listeMatiere);

    }

    @Override
    public void deleteListeMatiere(String listeMatiereId) {
        listeMatiereRepository.deleteById(listeMatiereId);


    }

    @Override
    public List<String> getAllMatiere() {
        List<MatierePrimaire> matieres = matierePrimaireRepository.findAll();
        return matieres.stream()
                .map(MatierePrimaire::getDesignation)
                .collect(Collectors.toList());

    }

    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size) {
        try {

            List<ListeMatiereDto> listeMatieres;
            Pageable paging = PageRequest.of(page, size);
            Page<ListeMatiere> pageTuts;
            pageTuts = listeMatiereRepository.findListeMatiereByTextToFind(textToFind, paging);
            listeMatieres = pageTuts.getContent().stream().map(listeMatiere -> {
                return ListeMatiereMapper.MAPPER.toListeMatiereDto(listeMatiere);
            }).collect(Collectors.toList());
           
            Map<String, Object> response = new HashMap<>();
            response.put("listeMatieres", listeMatieres);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ListeMatiere getListeMatiereByDesignation(String designation) throws ResourceNotFoundException {
        return listeMatiereRepository.findByDesignation(designation);
    }
    @Override
    public List<ListeMatiere> getAllMatiereByDesignation(String designation)   {
        List<ListeMatiere> listes = listeMatiereRepository.findAllByDesignation(designation);
        return listes;
    }
    @Override
    public ResponseEntity<Map<String, Object>> onSortListeMatiere(int page, int size, String field, String order) {
        try {
            List<ListeMatiereDto> listeMatieres ;
            Pageable paging = PageRequest.of(page, size);
            Page<ListeMatiere> pageTuts;
            if (order.equals("1")){
                pageTuts = listeMatiereRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            else {
                pageTuts = listeMatiereRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            listeMatieres = pageTuts.getContent().stream().map(listeMatiere -> {
                return ListeMatiereMapper.MAPPER.toListeMatiereDto(listeMatiere);
            }).collect(Collectors.toList());
           
            Map<String, Object> response = new HashMap<>();
            response.put("listeMatieres", listeMatieres);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Override
    public List<String> getTypePapier()   {
        List<TypePapier> papiers = typePapierRepository.findAll();
        return papiers.stream()
                .map(TypePapier::getNom)
                .collect(Collectors.toList());
    }
    @Override
    public List<String> getUniteConsommation()   {
        List<UniteConsommation> unites = uniteConsommationRepository.findAll();
        return unites.stream()
                .map(UniteConsommation::getNom)
                .collect(Collectors.toList());
    }
    
}
