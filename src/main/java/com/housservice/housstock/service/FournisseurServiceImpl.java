package com.housservice.housstock.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.repository.FournisseurRepository;


@Service
public class FournisseurServiceImpl implements FournisseurService {
	
    private FournisseurRepository fournisseurRepository;
	
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	@Autowired
	public FournisseurServiceImpl (FournisseurRepository fournisseurRepository,SequenceGeneratorService sequenceGeneratorService,
			MessageHttpErrorProperties messageHttpErrorProperties)
	{
		this.fournisseurRepository = fournisseurRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		
	}
	
	@Override
	public FournisseurDto buildFournisseurDtoFromFournisseur(Fournisseur fournisseur) {
		if (fournisseur == null)
		{
			return null;
		}
			
		FournisseurDto fournisseurDto = new FournisseurDto();
		fournisseurDto.setId(fournisseur.getId());
		fournisseurDto.setDate(fournisseur.getDate());
		fournisseurDto.setReference(fournisseur.getReference());
		fournisseurDto.setType(fournisseur.getType());
		fournisseurDto.setRaisonSocial(fournisseur.getRaisonSocial());
		fournisseurDto.setAdresse(fournisseur.getAdresse());
		fournisseurDto.setNumTel(fournisseur.getNumTel());
		fournisseurDto.setEmail(fournisseur.getEmail());
		fournisseurDto.setBanque(fournisseur.getBanque());
		fournisseurDto.setAdresseBanque(fournisseur.getAdresseBanque());
		fournisseurDto.setRib(fournisseur.getRib());
		fournisseurDto.setSwift(fournisseur.getSwift());
		fournisseurDto.setEcheance(fournisseur.getEcheance());
		fournisseurDto.setModePaiement(fournisseur.getModePaiement());
		fournisseurDto.setCodeTva(fournisseur.getCodeTva());
		fournisseurDto.setExonerationRs(fournisseur.getExonerationRs());
		fournisseurDto.setCertification(fournisseur.getCertification());
	
		
		//TODO ListCommandes fournisseurs
				
		return fournisseurDto;
		
	}

	
	private Fournisseur buildFournisseurFromFournisseurDto(FournisseurDto fournisseurDto) {
		
		Fournisseur fournisseur = new Fournisseur();
		fournisseur.setId(""+sequenceGeneratorService.generateSequence(Fournisseur.SEQUENCE_NAME));
		fournisseur.setDate(fournisseurDto.getDate());
		fournisseur.setReference(fournisseurDto.getReference());
		fournisseur.setType(fournisseurDto.getType());
		fournisseur.setRaisonSocial(fournisseurDto.getRaisonSocial());
		fournisseur.setAdresse(fournisseurDto.getAdresse());
		fournisseur.setNumTel(fournisseurDto.getNumTel());
		fournisseur.setEmail(fournisseurDto.getEmail());
		fournisseur.setBanque(fournisseurDto.getBanque());
		fournisseur.setAdresseBanque(fournisseurDto.getAdresseBanque());
		fournisseur.setRib(fournisseurDto.getRib());
		fournisseur.setSwift(fournisseurDto.getSwift());
		fournisseur.setEcheance(fournisseurDto.getEcheance());
		fournisseur.setModePaiement(fournisseurDto.getModePaiement());
		fournisseur.setCodeTva(fournisseurDto.getCodeTva());
		fournisseur.setExonerationRs(fournisseurDto.getExonerationRs());
		fournisseur.setCertification(fournisseurDto.getCertification());
		
		
		//TODO ListCommandes fournisseurs
		
		return fournisseur;
		
	}
	
	
	@Override
	public List<FournisseurDto> getAllFournisseur() {
		
		List<Fournisseur> listFournisseur = fournisseurRepository.findAll();
		
		return listFournisseur.stream()
				.map(fournisseur -> buildFournisseurDtoFromFournisseur(fournisseur))
				.filter(fournisseur -> fournisseur != null)
				.collect(Collectors.toList());
	}

	@Override
	public FournisseurDto getFournisseurById(String id) {
		
	    Optional<Fournisseur> fournisseurOpt = fournisseurRepository.findById(id);
		if(fournisseurOpt.isPresent()) {
			return buildFournisseurDtoFromFournisseur(fournisseurOpt.get());
		}
		return null;
	}

	
	@Override
	public void createNewFournisseur(@Valid FournisseurDto fournisseurDto) {
	
		fournisseurRepository.save(buildFournisseurFromFournisseurDto(fournisseurDto));
		
	}

	@Override
	public void updateFournisseur(@Valid FournisseurDto fournisseurDto) throws ResourceNotFoundException {
		
		Fournisseur fournisseur = fournisseurRepository.findById(fournisseurDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), fournisseurDto.getId())));
		
		fournisseur.setDate(fournisseurDto.getDate());
		fournisseur.setReference(fournisseurDto.getReference());
		fournisseur.setType(fournisseurDto.getType());
		fournisseur.setRaisonSocial(fournisseurDto.getRaisonSocial());
		fournisseur.setAdresse(fournisseurDto.getAdresse());
		fournisseur.setNumTel(fournisseurDto.getNumTel());
		fournisseur.setEmail(fournisseurDto.getEmail());
		fournisseur.setBanque(fournisseurDto.getBanque());
		fournisseur.setAdresseBanque(fournisseurDto.getAdresseBanque());
		fournisseur.setRib(fournisseurDto.getRib());
		fournisseur.setSwift(fournisseurDto.getSwift());
		fournisseur.setEcheance(fournisseurDto.getEcheance());
		fournisseur.setModePaiement(fournisseurDto.getModePaiement());
		fournisseur.setCodeTva(fournisseurDto.getCodeTva());
		fournisseur.setExonerationRs(fournisseurDto.getExonerationRs());
		fournisseur.setCertification(fournisseurDto.getCertification());

		// Update liste commandes fournisseurs
		
		fournisseurRepository.save(fournisseur);
	}

	@Override
	public void deleteFournisseur(String fournisseurId) {
		
		fournisseurRepository.deleteById(fournisseurId);

		
	}

}
