package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.EtapeProduction;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.repository.EtapeProductionRepository;
import com.housservice.housstock.repository.MachineRepository;


@Service
public class MachineServiceImpl implements MachineService {

	private MachineRepository machineRepository;
	
	private EtapeProductionRepository etapeProductionRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	

	@Autowired
	public MachineServiceImpl(MachineRepository machineRepository, SequenceGeneratorService sequenceGeneratorService,
									MessageHttpErrorProperties messageHttpErrorProperties, EtapeProductionRepository etapeProductionRepository) {
		this.machineRepository = machineRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.etapeProductionRepository = etapeProductionRepository;
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
		machineDto.setIdEtapeProduction(machine.getEtapeProduction().getId());
		machineDto.setNomEtapeProduction(machine.getEtapeProduction().getNomEtape());
		
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
		EtapeProduction etape = etapeProductionRepository.findById(machineDto.getIdEtapeProduction()).get();
		machine.setEtapeProduction(etape);
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
		if ( machine.getEtapeProduction() == null || !StringUtils.equals(machineDto.getIdEtapeProduction(), machine.getEtapeProduction().getId()) )
		{
			EtapeProduction etape = etapeProductionRepository.findById(machineDto.getIdEtapeProduction()).get();
			machine.setEtapeProduction(etape);
		}
	
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
