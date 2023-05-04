package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.MachineMapper;
import com.housservice.housstock.mapper.MatiereMapper;
import com.housservice.housstock.mapper.PersonnelMapper;
import com.housservice.housstock.mapper.TypeMachineMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.model.dto.MatiereDto;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.model.dto.TypeMachineDto;
import com.housservice.housstock.repository.MachineRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import com.housservice.housstock.repository.TypeMachineRepository;
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
public class MachineServiceImpl implements MachineService
{
    private final MachineRepository machineRepository;
    private final PersonnelRepository personnelRepository;

    private final TypeMachineRepository typeMachineRepository;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, MessageHttpErrorProperties messageHttpErrorProperties, TypeMachineRepository typeMachineRepository,PersonnelRepository personnelRepository) {
        this.machineRepository = machineRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.typeMachineRepository=typeMachineRepository;
        this.personnelRepository=personnelRepository;
    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortActiveMachine(int page, int size, String field, String order) {
        try {
            List<MachineDto> machineDtos ;
            Page<Machine> pageTuts;
            if (order.equals("1")){
                pageTuts = machineRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            else {
                pageTuts = machineRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            machineDtos = pageTuts.getContent().stream().map(machine -> {
                return MachineMapper.MAPPER.toMachineDto(machine);
            }).collect(Collectors.toList());
            machineDtos =machineDtos.stream().filter(machine -> !machine.isMiseEnVeille()).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("machines", machineDtos);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> onSortMachineNotActive(int page, int size, String field, String order) {
        try {
            List<MachineDto> machineDtos ;
            Page<Machine> pageTuts;
            if (order.equals("1")){
                pageTuts = machineRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
            }
            else {
                pageTuts = machineRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
            }
            machineDtos = pageTuts.getContent().stream().map(machine -> {
                return MachineMapper.MAPPER.toMachineDto(machine);
            }).collect(Collectors.toList());
            machineDtos =machineDtos.stream().filter(MachineDto::isMiseEnVeille).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("machines", machineDtos);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Override
    public void Restaurer(String id) throws ResourceNotFoundException {
        System.out.println(id);
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        machine.setMiseEnVeille(false);
        machineRepository.save(machine);
    }
    @Override
    public ResponseEntity<Map<String, Object>> getAllMachine(int page, int size) {
        try {
            List<MachineDto> machines = new ArrayList<MachineDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Machine> pageTuts;
            pageTuts = machineRepository.findMachineByMiseEnVeille(false, paging);
            machines = pageTuts.getContent().stream().map(machine -> {
                return MachineMapper.MAPPER.toMachineDto(machine);
            }).collect(Collectors.toList());
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
    public ResponseEntity<Map<String, Object>> getAllMachineEnVielle(int page, int size) {
        try {
            List<MachineDto> machines = new ArrayList<MachineDto>();
            Pageable paging = PageRequest.of(page, size);
            Page<Machine> pageTuts;
            pageTuts = machineRepository.findMachineByMiseEnVeille(true, paging);
            machines = pageTuts.getContent().stream().map(machine -> {
                return MachineMapper.MAPPER.toMachineDto(machine);
            }).collect(Collectors.toList());
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
    public void createNewMachine(MachineDto machineDto) throws ResourceNotFoundException {

        if (machineRepository.existsMachineByRefMachine(machineDto.getRefMachine())) {
            throw new IllegalArgumentException(	"Matricule existe deja !!");
        }
        Machine machine = MachineMapper.MAPPER.toMachine(machineDto);
        machine.setEtat("Disponible");
        machineRepository.save(machine);

    }

    @Override
    public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {
        try {

            List<MachineDto> machines;
            Pageable paging = PageRequest.of(page, size);
            Page<Machine> pageTuts;
            pageTuts = machineRepository.findMachineByTextToFind(textToFind, paging);
            machines = pageTuts.getContent().stream().map(machine -> {
                return MachineMapper.MAPPER.toMachineDto(machine);
            }).collect(Collectors.toList());
            machines= machines.stream().filter(machine -> machine.isMiseEnVeille()==enVeille).collect(Collectors.toList());
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
    public void updateMachine(MachineDto machineDto,String idMachine) throws ResourceNotFoundException {
        Machine machine = getMachineById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
        if (!machine.getRefMachine().equals(machineDto.getRefMachine())) {
            throw new IllegalArgumentException("Error Id!!");
        }
        machine.setLibelle(machineDto.getLibelle());
        machine.setDateMaintenance(machineDto.getDateMaintenance());
        machine.setNbConducteur(machineDto.getNbConducteur());
        machine.setNomConducteur(machineDto.getNomConducteur());
        machine.setRefMachine(machineDto.getRefMachine());
        machineRepository.save(machine);
    }

    @Override
    public void miseEnVeille(String idMachine) throws ResourceNotFoundException {
        Machine machine = machineRepository.findById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idMachine)));
        machine.setMiseEnVeille(true);
        machineRepository.save(machine);

    }
    @Override
    public void deleteMachineSelected(List<String> idMachinesSelected){
        for (String id : idMachinesSelected){
            machineRepository.deleteById(id);
        }
    }
    @Override
    public void deleteMachine(Machine machine) {
        machineRepository.delete(machine);
    }
    @Override
    public Optional<Machine> getMachineById(String machineId) {
        return machineRepository.findById(machineId);
    }
    @Override
    public List<String> getType()   {
        List<TypeMachine> machines = typeMachineRepository.findAll();
        return machines.stream()
                .map(TypeMachine::getNom)
                .collect(Collectors.toList());
    }

    @Override
    public String getEtat(String id)   {
        Machine machines = machineRepository.findById(id).get();
        return machines.getEtat();
    }
    @Override
    public List<String> getConducteur()   {
        List<Personnel> personnels = personnelRepository.findPersonnelByPoste("Conducteur machine");
        return personnels.stream()
                .map(Personnel::getFullName)
                .collect(Collectors.toList());
    }

    @Override
    public void addType(String type) throws ResourceNotFoundException {
        typeMachineRepository.deleteByNom("Autre");
        TypeMachineDto machineDto1 = new TypeMachineDto();
        machineDto1.setNom(type);
        TypeMachine matiere1 = TypeMachineMapper.MAPPER.toTypeMachine(machineDto1);
        typeMachineRepository.save(matiere1);
        TypeMachineDto machineDto2 = new TypeMachineDto();
        machineDto2.setNom("Autre");
        TypeMachine matiere2 = TypeMachineMapper.MAPPER.toTypeMachine(machineDto2);
        typeMachineRepository.save(matiere2);

    }

    public void reserve(String id){
        Machine machines = machineRepository.findById(id).get();
        machines.setEtat("Reserve");
        machineRepository.save(machines);
    }
    public void Demarer(String id){
        Machine machines = machineRepository.findById(id).get();
        machines.setEtat("Disponible");
        machineRepository.save(machines);
    }
    public List<String> getAllMachineDisponible(){
        List<Machine> machine= machineRepository.findMachineByEtat("Disponible");
        return machine.stream()
                .map(Machine::getLibelle)
                .collect(Collectors.toList());
    }

    };

