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
import com.housservice.housstock.repository.EtapeRepository;
import com.housservice.housstock.repository.MachineRepository;
import com.housservice.housstock.repository.PersonnelRepository;
import com.housservice.housstock.repository.TypeMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
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

    private final EtapeRepository etapeRepository;
    private final MongoTemplate mongoTemplate;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    @Autowired
    public MachineServiceImpl( MongoTemplate mongoTemplate,MachineRepository machineRepository, MessageHttpErrorProperties messageHttpErrorProperties, EtapeRepository etapeRepository,PersonnelRepository personnelRepository) {
        this.machineRepository = machineRepository;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.etapeRepository=etapeRepository;
        this.personnelRepository=personnelRepository;
        this.mongoTemplate=mongoTemplate;

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

    public int findProduitWithMaxSize() {
        List<Machine> produits = machineRepository.findAll();

        if (produits.isEmpty()) {
            return 0;
        }

        Query query = new Query();
        query.limit(1).with(Sort.by(Sort.Direction.DESC, "counter"));
        Machine produit = mongoTemplate.findOne(query, Machine.class);

        return produit.getCounter();
    }
    @Override
    public void createNewMachine(MachineDto machineDto) throws ResourceNotFoundException {
        machineDto.setCounter(this.findProduitWithMaxSize()+1);
        machineDto.setRefMachine("RefMachine" + String.format("%03d",machineDto.getCounter()));
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
        machine.setType(machineDto.getType());
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
        List<Etape> machines = etapeRepository.findEtapeByTypeEtape("Machine");
        return machines.stream()
                .map(Etape::getNomEtape)
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
        etapeRepository.deleteByNomEtape("Autre");
        Etape machineDto1 = new Etape();
        machineDto1.setNomEtape(type);
        machineDto1.setTypeEtape("Machine");
        etapeRepository.save(machineDto1);
        Etape machineDto2 = new Etape();
        machineDto2.setNomEtape("Autre");
        machineDto2.setTypeEtape("Machine");
        etapeRepository.save(machineDto2);

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
    public List<String> getAllMachineDisponible(String etape){
        List<Machine> machine= machineRepository.findMachineByType(etape);
        return machine.stream()
                .map(Machine::getLibelle)
                .collect(Collectors.toList());
    }
    @Override
    public int getallMachine(String b) {
        try {
            List<Machine> machines = machineRepository.findMachineByEtat(b);
            return (int) machines.stream().count();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Integer> getClientListe(boolean b) {
        int date;

        List<Integer> nbMachines = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

        List<Machine> activeClients =machineRepository.findMachineByMiseEnVeille(b);
        for(int i = 0; i< activeClients.size(); i++){
            Machine client= activeClients.get(i);
            date=client.getDateMaintenance().getMonth()+1;
            switch (date){
                case 9:
                    nbMachines .set(0, nbMachines .get(0) + 1);
                    break;
                case 10:
                    nbMachines .set(1, nbMachines .get(1) + 1);
                    break;
                case 11:
                    nbMachines .set(2, nbMachines .get(2) + 1);
                    break;
                case 12:
                    nbMachines .set(3, nbMachines .get(3) + 1);
                    break;
                case 1:
                    nbMachines .set(4, nbMachines .get(4) + 1);
                    break;
                case 2:
                    nbMachines .set(5, nbMachines .get(5) + 1);
                    break;
                case 3:
                    nbMachines .set(6, nbMachines .get(6) + 1);
                    break;
            }
        }
        return nbMachines ;
    }

    @Override
    public  List<Machine> getMachineListe() {
        try {
            List<Machine> machines = machineRepository.findAll();
            return machines;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

};

