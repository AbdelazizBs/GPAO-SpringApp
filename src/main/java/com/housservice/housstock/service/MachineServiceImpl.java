package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.repository.MachineRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MachineServiceImpl implements MachineService {

	private MachineRepository machineRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	

	@Autowired
	public MachineServiceImpl(MachineRepository machineRepository, SequenceGeneratorService sequenceGeneratorService,
									MessageHttpErrorProperties messageHttpErrorProperties) {
		this.machineRepository = machineRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
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
		machineDto.setNbrConducteur(machine.getNbrConducteur());
		machineDto.setDateMaintenance(machine.getDateMaintenance());
		machineDto.setEtapeProduction(machine.getEtapeProduction());
		
		return machineDto;
	}

	
	@Override
	public Optional<Machine> getMachineById(String machineId) {
		return machineRepository.findById(machineId);
	}

	
	@Override
	public void deleteMachine(Machine machine) {
		machineRepository.delete(machine);
		
	}

	@Override
	public void createNewMachine(@Valid MachineDto machineDto) {
		
		machineRepository.save(buildMachineFromMachineDto(machineDto));
	}
	
	
	private Machine buildMachineFromMachineDto(MachineDto machineDto) {
		Machine machine = new Machine();
		machine.setId(""+sequenceGeneratorService.generateSequence(Machine.SEQUENCE_NAME));	
		machine.setReference(machineDto.getReference());		
		machine.setLibelle(machineDto.getLibelle());
		machine.setNbrConducteur(machineDto.getNbrConducteur());
		machine.setDateMaintenance(machineDto.getDateMaintenance());
		machine.setEtapeProduction(machineDto.getEtapeProduction());
		return machine;
	
		}


	@Override
	public void updateMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException {
		
		Machine machine = getMachineById(machineDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getErro0002(),  machineDto.getId())));

		machine.setReference(machineDto.getReference());		
		machine.setLibelle(machineDto.getLibelle());
		machine.setNbrConducteur(machineDto.getNbrConducteur());
		machine.setDateMaintenance(machineDto.getDateMaintenance());
		machine.setEtapeProduction(machineDto.getEtapeProduction());

		machineRepository.save(machine);
		
	}

	@Override
	public List<Machine> findMachineActif() {
		return machineRepository.findMachineActif();
	}


	@Override
	public List<Machine> findMachineNotActif() {
		 return machineRepository.findMachineNotActif();
	}



}
