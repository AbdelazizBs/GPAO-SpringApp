package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.validation.Valid;

import com.housservice.housstock.model.EtatMachine;
import com.housservice.housstock.repository.EtatMachineRepository;
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
	final
	EtatMachineRepository etatMachineRepository;

	@Autowired
	public MachineServiceImpl(MachineRepository machineRepository, SequenceGeneratorService sequenceGeneratorService,
							  MessageHttpErrorProperties messageHttpErrorProperties, EtapeProductionRepository etapeProductionRepository, EtatMachineRepository etatMachineRepository) {
		this.machineRepository = machineRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.etapeProductionRepository = etapeProductionRepository;
		this.etatMachineRepository = etatMachineRepository;
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
		machineDto.setEtatMachine(machine.getEtatMachine());
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
		Machine machine =machineRepository.save(buildMachineFromMachineDto(machineDto));
		EtatMachine etatMachine = new EtatMachine("en repos",LocalDate.now(), LocalDate.of(2026,10,10),machine.getId());
		etatMachineRepository.save(etatMachine);
		machine.setEtatMachine(etatMachine);
		machine.setEnVeille(false);
		machineRepository.save(machine);
	}
	
	private Machine buildMachineFromMachineDto(MachineDto machineDto) throws ResourceNotFoundException {
		Machine machine = new Machine();
		machine.setId(""+sequenceGeneratorService.generateSequence(Machine.SEQUENCE_NAME));	
		machine.setReference(machineDto.getReference());		
		machine.setLibelle(machineDto.getLibelle());
		machine.setEnVeille(machineDto.getEnVeille());
		machine.setNbrConducteur(machineDto.getNbrConducteur());
		machine.setEtatMachine(machineDto.getEtatMachine());
		machine.setDateMaintenance(machineDto.getDateMaintenance());
		EtapeProduction etape = etapeProductionRepository.findByNomEtape(machineDto.getNomEtapeProduction())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), machineDto.getNomEtapeProduction())));
		machine.setEtapeProduction(etape);
		return machine;
		}


	@Override
	public void updateMachine(@Valid MachineDto machineDto) throws ResourceNotFoundException {
		
		Machine machine = machineRepository.findById(machineDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  machineDto.getId())));

		machine.setReference(machineDto.getReference());		
		machine.setLibelle(machineDto.getLibelle());
		machine.setEnVeille(machineDto.getEnVeille());
		machine.setNbrConducteur(machineDto.getNbrConducteur());
		machine.setEtatMachine(machineDto.getEtatMachine());
		machine.setDateMaintenance(machineDto.getDateMaintenance());
		machine.setEnVeille(machineDto.getEnVeille());
	machine.setEtapeProduction(etapeProductionRepository.findByNomEtape(machineDto.getNomEtapeProduction())
			.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), machineDto.getNomEtapeProduction()))));

		machineRepository.save(machine);

	}


	@Override
	public List<MachineDto> getAllMachine() {
		List<Machine> listMachine = machineRepository.findByEnVeille(false);
		return listMachine.stream()
				.map(machine -> buildMachineDtoFromMachine(machine))
				.filter(machine -> machine != null)
				.collect(Collectors.toList());
		
	}


	@Override
	public List<MachineDto> getMachineEnVeille() {
		 List<Machine> listMachine = machineRepository.findByEnVeille(true);
			return listMachine.stream()
					.map(machine -> buildMachineDtoFromMachine(machine))
					.filter(machine -> machine != null)
					.collect(Collectors.toList());
					
	}
	@Override
	public String getIdMachine(String  nomEtape) throws ResourceNotFoundException {
		EtapeProduction etapeProduction = etapeProductionRepository.findByNomEtape(nomEtape)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  nomEtape)));
		List<Machine> machines = machineRepository.findMachineByEtapeProduction(etapeProduction);
		return machines.stream().map(Machine::getId).toString();

	}
	@Override
	public List<Machine> getAllMachinesByEtapes(String  nomEtape) throws ResourceNotFoundException {
		EtapeProduction etapeProduction = etapeProductionRepository.findByNomEtape(nomEtape)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  nomEtape)));
		return machineRepository.findMachineByEtapeProduction(etapeProduction);
	}

//	public MachineDto setEtatEnRepos(final String idMachine) throws ResourceNotFoundException {
//		final Machine machine = machineRepository.findById(idMachine)
//				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
//
//		machine.setEtat("En repos");
//		return MachineMapper.MAPPER.toMachineDTO(machineRepository.save(machine));
//	}
//	public MachineDto setEtatEnMaintenance(final String idMachine) throws ResourceNotFoundException {
//		final Machine machine = machineRepository.findById(idMachine)
//				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
//		machine.setEtat("En Maintenance");
//		return MachineMapper.MAPPER.toMachineDTO(machineRepository.save(machine));
//	}
//	public MachineDto setEtatEnPanne(final String idMachine) throws ResourceNotFoundException {
//		final Machine machine = machineRepository.findById(idMachine)
//				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
//		machine.setEtat("En panne");
//		return MachineMapper.MAPPER.toMachineDTO(machineRepository.save(machine));
//	}

	@Override
	public void setMachineEnVeille(final String idMachine) throws ResourceNotFoundException {
		final Machine machine = machineRepository.findById(idMachine)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			machine.setEnVeille(true);
		  machineRepository.save(machine);
	}


	@Override
	public void setEtatEnmarche(final String idMachine) throws ResourceNotFoundException {
		final Machine machine = machineRepository.findById(idMachine)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
					.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			etatMachineActuelle.setDateFin(LocalDate.now());
			etatMachineRepository.save(etatMachineActuelle);
		EtatMachine etatMachineEnMarche = new EtatMachine("en marche",LocalDate.now(), LocalDate.of(2026,10,10),idMachine);
		machine.setEtatMachine(etatMachineEnMarche);
		etatMachineRepository.save(etatMachineEnMarche);
		  machineRepository.save(machine);
	}

	@Override
	public void setEtatEnPause(final String idMachine) throws ResourceNotFoundException {
		final Machine machine = machineRepository.findById(idMachine)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
					.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			etatMachineActuelle.setDateFin(LocalDate.now());
			etatMachineRepository.save(etatMachineActuelle);
		EtatMachine etatMachineEnMarche = new EtatMachine("en pause",LocalDate.now(), LocalDate.of(2026,10,10),idMachine);
		machine.setEtatMachine(etatMachineEnMarche);
		etatMachineRepository.save(etatMachineEnMarche);
		  machineRepository.save(machine);
	}	@Override
	public void setEtatEnRepos(final String idMachine) throws ResourceNotFoundException {
		final Machine machine = machineRepository.findById(idMachine)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
					.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			etatMachineActuelle.setDateFin(LocalDate.now());
			etatMachineRepository.save(etatMachineActuelle);
		EtatMachine etatMachineEnMarche = new EtatMachine("en repos",LocalDate.now(), LocalDate.of(2026,10,10),idMachine);
		machine.setEtatMachine(etatMachineEnMarche);
		etatMachineRepository.save(etatMachineEnMarche);
		  machineRepository.save(machine);
	}	@Override
	public void setEtatEnMaintenance(final String idMachine) throws ResourceNotFoundException {
		final Machine machine = machineRepository.findById(idMachine)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
					.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			etatMachineActuelle.setDateFin(LocalDate.now());
			etatMachineRepository.save(etatMachineActuelle);
		EtatMachine etatMachineEnMarche = new EtatMachine("en maintenance",LocalDate.now(), LocalDate.of(2026,10,10),idMachine);
		machine.setEtatMachine(etatMachineEnMarche);
		etatMachineRepository.save(etatMachineEnMarche);
		  machineRepository.save(machine);
	}	@Override
	public void setEtatEnPanne(final String idMachine) throws ResourceNotFoundException {
		final Machine machine = machineRepository.findById(idMachine)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			EtatMachine etatMachineActuelle = etatMachineRepository.findById(machine.getEtatMachine().getId())
					.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idMachine)));
			etatMachineActuelle.setDateFin(LocalDate.now());
			etatMachineRepository.save(etatMachineActuelle);
		EtatMachine etatMachineEnMarche = new EtatMachine("en panne",LocalDate.now(), LocalDate.of(2026,10,10),idMachine);
		machine.setEtatMachine(etatMachineEnMarche);
		etatMachineRepository.save(etatMachineEnMarche);
		  machineRepository.save(machine);
	}


}
