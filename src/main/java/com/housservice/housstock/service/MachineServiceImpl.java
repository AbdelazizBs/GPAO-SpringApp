package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.MachineMapper;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.EtatMachine;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.repository.EtapeProductionRepository;
import com.housservice.housstock.repository.EtatMachineRepository;
import com.housservice.housstock.repository.MachineRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MachineServiceImpl implements MachineService {

    private MachineRepository machineRepository;

    private EtapeProductionRepository etapeProductionRepository;

    private SequenceGeneratorService sequenceGeneratorService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    final
    EtatMachineRepository etatMachineRepository;
    private final PersonnelRepository personnelRepository;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, SequenceGeneratorService sequenceGeneratorService,
                              MessageHttpErrorProperties messageHttpErrorProperties, EtapeProductionRepository etapeProductionRepository, EtatMachineRepository etatMachineRepository,
                              PersonnelRepository personnelRepository) {
        this.machineRepository = machineRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.etapeProductionRepository = etapeProductionRepository;
        this.etatMachineRepository = etatMachineRepository;
        this.personnelRepository = personnelRepository;
    }


    @Override
    public MachineDto buildMachineDtoFromMachine(Machine machine) {
        if (machine == null) {
            return null;
        }

        MachineDto machineDto = new MachineDto();
        machineDto.setId(machine.getId());
        machineDto.setReference(machine.getReference());
        machineDto.setLibelle(machine.getLibelle());
        machineDto.setNomEtatMachine(machine.getEtatMachine().getNomEtat());
        machineDto.setNbrConducteur(machine.getNbrConducteur());
        machineDto.setDateMaintenance(machine.getDateMaintenance());
        machineDto.setNomEtapeProduction(machine.getEtapeProduction().getNomEtape());

        return machineDto;
    }


    @Override
    public MachineDto getMachineById(String machineId) {
        Optional<Machine> machineOpt = machineRepository.findById(machineId);
        if (machineOpt.isPresent()) {
            return buildMachineDtoFromMachine(machineOpt.get());
        }
        return null;
    }


    @Override
    public void deleteMachine(final String machineId) {
        machineRepository.deleteById(machineId);
    }


    // delay to update methode is the static LocalDate For EtatMachine


    @Override
    public void createNewMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException {
        Machine machine = machineRepository.save(MachineMapper.MAPPER.toMachine(machineDto));
        EtatMachine etatMachine = new EtatMachine("en repos", LocalDate.now(), LocalDate.now(), machine.getId());
        EtapeProduction etapeProduction = etapeProductionRepository.findByNomEtape(machineDto.getNomEtapeProduction())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), machineDto.getNomEtapeProduction())));
        etatMachineRepository.save(etatMachine);
        machine.setEtatMachine(etatMachine);
        machine.setEtapeProduction(etapeProduction);
        machine.setEnVeille(false);
        List<Personnel> list = new ArrayList<>();
        machineDto.getNomConducteurs().forEach(nom -> {
            Personnel personnel = null;
            try {
                personnel = personnelRepository.findByNom(nom)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nom)));
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            list.add(personnel);
        });
        machine.setPersonnel(list);
        machineRepository.save(machine);
    }


    @Override
    public void updateMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException {
        Machine machine = machineRepository.findById(machineDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), machineDto.getId())));
        machine.setReference(machineDto.getReference());
        machine.setLibelle(machineDto.getLibelle());
        machine.setNbrConducteur(machineDto.getNbrConducteur());
        machine.setDateMaintenance(machineDto.getDateMaintenance());
        List<Personnel> list = new ArrayList<Personnel>();
        machineDto.getNomConducteurs().forEach(nom -> {
            Personnel personnel = null;
            try {
                personnel = personnelRepository.findByNom(nom)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nom)));
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            list.add(personnel);
        });
        machine.setPersonnel(list);
        machine.setEtatMachine(etatMachineRepository.findEtatMachineByNomEtat(machineDto.getNomEtatMachine())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), machineDto.getNomEtatMachine()))));
        machine.setEtapeProduction(etapeProductionRepository.findByNomEtape(machineDto.getNomEtapeProduction())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), machineDto.getNomEtapeProduction()))));
        machineRepository.save(machine);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllMachine(int page, int size) {
        try {
            List<MachineDto> machines;
            Pageable paging = PageRequest.of(page, size);
            Page<Machine> pageTuts;
            pageTuts = machineRepository.findByEnVeille(false, paging);
            machines = pageTuts.getContent().stream().map(machine -> MachineMapper.MAPPER.toMachineDto(machine)).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("machines", machines);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//	@Override
//	public List<MachineDto> getAllMachine() {
//		List<Machine> listMachine = machineRepository.findByEnVeille(false);
//		return listMachine.stream()
//				.map(machine -> buildMachineDtoFromMachine(machine))
//				.filter(machine -> machine != null)
//				.collect(Collectors.toList());
//
//	}


    @Override
    public ResponseEntity<Map<String, Object>> getMachineEnVeille(int page, int size) {

        try {
            List<MachineDto> machines = new ArrayList<MachineDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Machine> pageTuts;
            pageTuts = machineRepository.findByEnVeille(true, paging);
            machines = pageTuts.getContent().stream().map(machine -> buildMachineDtoFromMachine(machine)).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("machines", machines);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public String getIdMachine(String nomEtape) throws ResourceNotFoundException {
        EtapeProduction etapeProduction = etapeProductionRepository.findByNomEtape(nomEtape)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomEtape)));
        List<Machine> machines = machineRepository.findMachineByEtapeProduction(etapeProduction);
        return machines.stream().map(Machine::getId).toString();

    }

    @Override
    public List<Machine> getAllMachinesByEtapes(String nomEtape) throws ResourceNotFoundException {
        EtapeProduction etapeProduction = etapeProductionRepository.findByNomEtape(nomEtape)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), nomEtape)));
        return machineRepository.findMachineByEtapeProduction(etapeProduction);
    }


    @Override
    public void setMachineEnVeille(final String idMachine) throws ResourceNotFoundException {
        final Machine machine = machineRepository.findById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        machine.setEnVeille(true);
        machineRepository.save(machine);
    }


    @Override
    public void setEtatEnmarche(final String idMachine) throws ResourceNotFoundException {
        final Machine machine = machineRepository.findById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        etatMachineActuelle.setDateFin(LocalDate.now());
        etatMachineRepository.save(etatMachineActuelle);
        EtatMachine etatMachineEnMarche = new EtatMachine("en marche", LocalDate.now(), LocalDate.of(2026, 10, 10), idMachine);
        machine.setEtatMachine(etatMachineEnMarche);
        etatMachineRepository.save(etatMachineEnMarche);
        machineRepository.save(machine);
    }

    @Override
    public void setEtatEnPause(final String idMachine) throws ResourceNotFoundException {
        final Machine machine = machineRepository.findById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        etatMachineActuelle.setDateFin(LocalDate.now());
        etatMachineRepository.save(etatMachineActuelle);
        EtatMachine etatMachineEnMarche = new EtatMachine("en pause", LocalDate.now(), LocalDate.of(2026, 10, 10), idMachine);
        machine.setEtatMachine(etatMachineEnMarche);
        etatMachineRepository.save(etatMachineEnMarche);
        machineRepository.save(machine);
    }

    @Override
    public void setEtatEnRepos(final String idMachine) throws ResourceNotFoundException {
        final Machine machine = machineRepository.findById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        etatMachineActuelle.setDateFin(LocalDate.now());
        etatMachineRepository.save(etatMachineActuelle);
        EtatMachine etatMachineEnMarche = new EtatMachine("en repos", LocalDate.now(), LocalDate.of(2026, 10, 10), idMachine);
        machine.setEtatMachine(etatMachineEnMarche);
        etatMachineRepository.save(etatMachineEnMarche);
        machineRepository.save(machine);
    }

    @Override
    public void setEtatEnMaintenance(final String idMachine) throws ResourceNotFoundException {
        final Machine machine = machineRepository.findById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        etatMachineActuelle.setDateFin(LocalDate.now());
        etatMachineRepository.save(etatMachineActuelle);
        EtatMachine etatMachineEnMarche = new EtatMachine("en maintenance", LocalDate.now(), LocalDate.of(2026, 10, 10), idMachine);
        machine.setEtatMachine(etatMachineEnMarche);
        etatMachineRepository.save(etatMachineEnMarche);
        machineRepository.save(machine);
    }

    @Override
    public void setEtatEnPanne(final String idMachine) throws ResourceNotFoundException {
        final Machine machine = machineRepository.findById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        etatMachineActuelle.setDateFin(LocalDate.now());
        etatMachineRepository.save(etatMachineActuelle);
        EtatMachine etatMachineEnMarche = new EtatMachine("en panne", LocalDate.now(), LocalDate.of(2026, 10, 10), idMachine);
        machine.setEtatMachine(etatMachineEnMarche);
        etatMachineRepository.save(etatMachineEnMarche);
        machineRepository.save(machine);
    }


}
