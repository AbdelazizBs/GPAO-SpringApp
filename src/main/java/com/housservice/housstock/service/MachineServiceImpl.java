package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.MachineMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.repository.MachineRepository;
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
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.machineRepository = machineRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
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
    public void createNewMachine(String refMachine, String nomConducteur, String libelle, int nbConducteur,
                                 Date dateMaintenance, String type) throws ResourceNotFoundException {

        if (machineRepository.existsMachineByRefMachine(refMachine)) {
            throw new IllegalArgumentException(	"Matricule existe deja !!");
        }
        MachineDto machineDto = new MachineDto();

        machineDto.setDateMaintenance(new Date());
        machineDto.setMiseEnVeille(false);
        machineDto.setRefMachine(refMachine);
        machineDto.setLiebelle(libelle);
        machineDto.setType(type);
        machineDto.setNbConducteur(nbConducteur);
        machineDto.setNomConducteur(nomConducteur);
        Machine machine = MachineMapper.MAPPER.toMachine(machineDto);
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
    public void updateMachine(String idMachine, String refMachine, String nomConducteur, 
                              String libelle, int nbConducteur, Date dateMaintenance, String type) throws ResourceNotFoundException {
        if (refMachine.isEmpty() || libelle.isEmpty() || nomConducteur.isEmpty() ||  type.isEmpty()  ) {
            throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
        }
        Machine machine = getMachineById(idMachine)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
        if (!machine.getRefMachine().equals(refMachine)) {
            throw new IllegalArgumentException("Error Id!!");
        }
       
       
        machine.setLibelle(libelle);
        machine.setDateMaintenance(machine.getDateMaintenance());
        machine.setNbConducteur(machine.getNbConducteur());
        machine.setType(type);

        machine.setNomConducteur(nomConducteur);
        machine.setRefMachine(machine.getRefMachine());
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
    public void deleteMachine(Machine machine) {
        machineRepository.delete(machine);


    }
    @Override
    public Optional<Machine> getMachineById(String machineId) {
        return machineRepository.findById(machineId);
    }
    @Override
    public List<String> getType()   {
        List<Machine> machines = machineRepository.findAll();
        return machines.stream()
                .map(Machine::getType)
                .collect(Collectors.toList());
    }
}
