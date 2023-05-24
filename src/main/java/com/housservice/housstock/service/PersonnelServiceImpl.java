package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.PersonnelMapper;
import com.housservice.housstock.model.Comptes;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.repository.*;
import javassist.NotFoundException;
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
public class PersonnelServiceImpl implements PersonnelService {

    private final PersonnelRepository personnelRepository;
    private final MachineRepository machineRepository;

    final
    ComptesRepository comptesRepository;

    private final MessageHttpErrorProperties messageHttpErrorProperties;


    final RolesRepository rolesRepository;
    private final EtapeProductionRepository etapeProductionRepository;
    private final ComptesRepository compteRepository;

    public PersonnelServiceImpl(PersonnelRepository personnelRepository, MachineRepository machineRepository, MessageHttpErrorProperties messageHttpErrorProperties,
                                RolesRepository rolesRepository, ComptesRepository comptesRepository,
                                EtapeProductionRepository etapeProductionRepository, ComptesRepository compteRepository) {
        this.personnelRepository = personnelRepository;
        this.machineRepository = machineRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.rolesRepository = rolesRepository;
        this.comptesRepository = comptesRepository;
        this.etapeProductionRepository = etapeProductionRepository;
        this.compteRepository = compteRepository;
    }


    @Override
    public void addPersonnel(PersonnelDto personnelDto) {
        try {
            if (personnelRepository.existsPersonnelByCin(personnelDto.getCin())) {
                throw new IllegalArgumentException(" cin " + personnelDto.getCin() + "  existe deja !!");
            }
            if (personnelRepository.existsPersonnelByMatricule(personnelDto.getMatricule())) {
                throw new IllegalArgumentException("matricule" + personnelDto.getMatricule() + "  existe deja !!");
            }
            Personnel personnel = PersonnelMapper.MAPPER.toPersonnel(personnelDto);
            personnel.setMiseEnVeille(false);
            personnelRepository.save(personnel);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    @Override
    public void updatePersonnel(PersonnelDto personnelDto, String idPersonnel) throws ResourceNotFoundException {
        Personnel personnel = personnelRepository.findById(idPersonnel)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), personnelDto.getId())));
        personnel.setNom(personnelDto.getNom());
        personnel.setDateNaissance(personnelDto.getDateNaissance());
        personnel.setAdresse(personnelDto.getAdresse());        
        personnel.setRegion(personnelDto.getRegion());
        personnel.setPays(personnelDto.getPays());       
        personnel.setPhoto(personnelDto.getPhoto());
        personnel.setCin(personnelDto.getCin());
        personnel.setSexe(personnelDto.getSexe());
        personnel.setRib(personnelDto.getRib());
        personnel.setPoste(personnelDto.getPoste());
        personnel.setDateEmbauche(personnelDto.getDateEmbauche());
        personnel.setEchelon(personnelDto.getEchelon());
        personnel.setCategorie(personnelDto.getCategorie());
        personnel.setMatricule(personnelDto.getMatricule());
        personnel.setPhone(personnelDto.getPhone());
        personnel.setVille(personnelDto.getVille());
        personnel.setCodePostal(personnelDto.getCodePostal());
        personnel.setEmail(personnelDto.getEmail());
        personnel.setNumCnss(personnelDto.getNumCnss());
        personnel.setSituationFamiliale(personnelDto.getSituationFamiliale());
        personnel.setNbrEnfant(personnelDto.getNbrEnfant());
        personnel.setTypeContrat(personnelDto.getTypeContrat());
        personnelRepository.save(personnel);
    }

    @Override
    public PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException {
        Optional<Personnel> utilisateurOpt = personnelRepository.findById(id);
        if (utilisateurOpt.isPresent()) {
            return PersonnelMapper.MAPPER.toPersonnelDto(utilisateurOpt.get());
        }
        return null;
    }


    @Override
    public Personnel getPersonnelByNom(String nom) throws ResourceNotFoundException {
        return personnelRepository.findByNom(nom).orElseThrow(() -> new ResourceNotFoundException(
                MessageFormat.format(messageHttpErrorProperties.getError0002(), nom)));
    }


    @Override
    public void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException {
        Personnel personnel = personnelRepository.findById(idPersonnel).orElseThrow(() -> new ResourceNotFoundException(
                MessageFormat.format(messageHttpErrorProperties.getError0002(), idPersonnel)));
        personnel.setMiseEnVeille(true);
        personnel.getCompte().setEnVeille(true);
        personnelRepository.save(personnel);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllPersonnel(int page, int size) {
        try {
            List<PersonnelDto> personnels ;
            Pageable paging = PageRequest.of(page, size);
            Page<Personnel> pageTuts;
            pageTuts = personnelRepository.findPersonnelByMiseEnVeille(false, paging);
            personnels = pageTuts.getContent().stream().map(personnel -> {
                return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("personnels", personnels);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllPersonnelName() {

        try {
           List<String> personnelsName = personnelRepository.findAll().stream().map(personnel -> {
                return personnel.getNom();
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("personnelsName", personnelsName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortActivePersonnel(int page, int size, String field, String order) {
        try {
            List<PersonnelDto> personnels;
            Pageable paging = PageRequest.of(page, size);
            Page<Personnel> pageTuts;
            if (order.equals("1")) {
                pageTuts = personnelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            } else {
                pageTuts = personnelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            personnels = pageTuts.getContent().stream().map(personnel -> {
                return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
            }).collect(Collectors.toList());
            personnels = personnels.stream().filter(personnel -> !personnel.isMiseEnVeille()).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("personnels", personnels);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortPersonnelNotActive(int page, int size, String field, String order) {
        try {
            List<PersonnelDto> personnels;
            Page<Personnel> pageTuts;
            if (order.equals("1")) {
                pageTuts = personnelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            } else {
                pageTuts = personnelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            personnels = pageTuts.getContent().stream().map(personnel -> {
                return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
            }).collect(Collectors.toList());
            personnels = personnels.stream().filter(PersonnelDto::isMiseEnVeille).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("personnels", personnels);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(int page, int size) {

        try {

            List<PersonnelDto> personnels;
            Pageable paging = PageRequest.of(page, size);
            Page<Personnel> pageTuts;
            pageTuts = personnelRepository.findPersonnelByMiseEnVeille(true, paging);
            personnels = pageTuts.getContent().stream().map(personnel -> {
                return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
            }).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();

            response.put("personnels", personnels);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> getConcducteurs() {

        try {
            List<PersonnelDto> personnels;
            personnels = personnelRepository.findAll().stream().map(personnel -> {
                return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
            }).collect(Collectors.toList());
            List<String> nomConducteurs = new ArrayList<>();
            for (PersonnelDto personnel : personnels) {
                if (personnel.getPoste().equals("Conducteur machine")) {
                    nomConducteurs.add(personnel.getNom());
                }
            }
            Map<String, Object> response = new HashMap<>();
            response.put("nomConducteurs", nomConducteurs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> getOperatricesName() {

        try {
            List<PersonnelDto> personnels;
            personnels = personnelRepository.findAll().stream().map(personnel -> {
                return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
            }).collect(Collectors.toList());
            List<String> operatricesName = new ArrayList<>();
            for (PersonnelDto personnel : personnels) {
                if (personnel.getPoste().startsWith("Monitrice de contrôle")) {
                    operatricesName.add(personnel.getNom());
                }
            }
            Map<String, Object> response = new HashMap<>();
            response.put("operatricesName", operatricesName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
 @Override
    public ResponseEntity<Map<String, Object>> getPersonnelsNameByMachine(String nomEtape) {

        try {
            List<String> personnelsName = new ArrayList<>();
            EtapeProduction etapeProductions =etapeProductionRepository.findByNomEtape(nomEtape).
                    orElseThrow(() -> new NotFoundException("Etape production not found"));
            List<Machine> machines = machineRepository.findMachineByEtapeProduction(etapeProductions);
            // add all personnels name of machines in list and filter reppeated name
            for (Machine machine : machines) {
                for (Personnel personnel : machine.getPersonnel()) {
                    personnelsName.add(personnel.getNom());
                }
            }
            personnelsName = personnelsName.stream().distinct().collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("nomConducteurs", personnelsName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> getPersonnelByEmailCompte(String email) {
        try {
            Comptes comptes = compteRepository.findByEmail(email).
                    orElseThrow(() -> new NotFoundException("Compte not found"));
            Personnel personnel = personnelRepository.findPersonnelByCompteId(comptes.getId()).
                    orElseThrow(() -> new NotFoundException("Personnel not found"));
            Map<String, Object> response = new HashMap<>();
            response.put("personnel", personnel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
 @Override
    public ResponseEntity<Map<String, Object>> getPersonnelsId(List<String> personnelsName) {

        try {
            List<String> personnels = new ArrayList<>();
            personnelRepository.findAll().forEach(personnel -> {
                if (personnelsName.contains(personnel.getNom())) {
                    personnels.add(personnel.getId());
                }
            });
            Map<String, Object> response = new HashMap<>();
            response.put("personnelsId", personnels);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {

        try {

            List<PersonnelDto> personnels;
            Pageable paging = PageRequest.of(page, size);
            Page<Personnel> pageTuts;
            pageTuts = personnelRepository.findPersonnelByTextToFind(textToFind, paging);
            personnels = pageTuts.getContent().stream().map(personnel -> {
                return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
            }).collect(Collectors.toList());
            personnels = personnels.stream().filter(personnel -> personnel.isMiseEnVeille() == enVeille).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("personnels", personnels);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void deletePersonnel(String idPersonnel) {
        personnelRepository.deleteById(idPersonnel);
    }

    @Override
    public void deletePersonnelSelected(List<String> idPersonnelsSelected) {
        for (String id : idPersonnelsSelected) {
            personnelRepository.deleteById(id);
        }
    }

}
