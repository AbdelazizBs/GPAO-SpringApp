package com.housservice.housstock.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Fournisseur;

import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.repository.FournisseurRepository;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;



import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class FournisseurServiceImpl implements FournisseurService{
	
	private FournisseurRepository fournisseurRepository;
		
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
		
	@Autowired
	public FournisseurServiceImpl(SequenceGeneratorService sequenceGeneratorService ,FournisseurRepository fournisseurRepository, MessageHttpErrorProperties messageHttpErrorProperties) 
	{
		this.fournisseurRepository = fournisseurRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}

	@Override
	public FournisseurDto buildFournisseurDtoFromFournisseur(Fournisseur fournisseur) throws ResourceNotFoundException {
		
		if (fournisseur == null) {
			return null;
		}

		FournisseurDto fournisseurDto = new FournisseurDto();
		fournisseurDto.setId(fournisseur.getId());
		fournisseurDto.setRefFrsIris(fournisseur.getRefFrsIris());
		fournisseurDto.setIntitule(fournisseur.getIntitule());
		fournisseurDto.setAbrege(fournisseur.getAbrege());
		fournisseurDto.setStatut(fournisseur.getStatut());
		fournisseurDto.setInterlocuteur(fournisseur.getInterlocuteur());
		fournisseurDto.setAdresse(fournisseur.getAdresse());
		fournisseurDto.setCodePostal(fournisseur.getCodePostal());
		fournisseurDto.setVille(fournisseur.getVille());
		fournisseurDto.setRegion(fournisseur.getRegion());
		fournisseurDto.setPays(fournisseur.getPays());
		fournisseurDto.setTelephone(fournisseur.getTelephone());
		fournisseurDto.setTelecopie(fournisseur.getTelecopie());
		fournisseurDto.setLinkedin(fournisseur.getLinkedin());
		fournisseurDto.setEmail(fournisseur.getEmail());
		fournisseurDto.setSiteWeb(fournisseur.getSiteWeb());
		
		fournisseurDto.setIdentifiantTva(fournisseur.getIdentifiantTva());

		
		return fournisseurDto;

	}

	
	private Fournisseur buildFournisseurFromFournisseurDto(FournisseurDto fournisseurDto) {
		Fournisseur fournisseur = new Fournisseur();
		
		fournisseur.setId("" + sequenceGeneratorService.generateSequence(Fournisseur.SEQUENCE_NAME));
		
		fournisseur.setId(fournisseurDto.getId());
		fournisseur.setRefFrsIris(fournisseurDto.getRefFrsIris());
		fournisseur.setIntitule(fournisseurDto.getIntitule());
		fournisseur.setAbrege(fournisseurDto.getAbrege());
		fournisseur.setStatut(fournisseurDto.getStatut());
		fournisseur.setInterlocuteur(fournisseurDto.getInterlocuteur());
		fournisseur.setAdresse(fournisseurDto.getAdresse());
		fournisseur.setCodePostal(fournisseurDto.getCodePostal());
		fournisseur.setVille(fournisseurDto.getVille());
		fournisseur.setRegion(fournisseurDto.getRegion());
		fournisseur.setPays(fournisseurDto.getPays());
		fournisseur.setTelephone(fournisseurDto.getTelephone());
		fournisseur.setTelecopie(fournisseurDto.getTelecopie());
		fournisseur.setLinkedin(fournisseurDto.getLinkedin());
		fournisseur.setEmail(fournisseurDto.getEmail());
		fournisseur.setSiteWeb(fournisseurDto.getSiteWeb());
		fournisseur.setIdentifiantTva(fournisseurDto.getIdentifiantTva());
		
		return fournisseur;
	}

	@Override
	public void createNewFournisseur(String refFrsIris, String intitule, String abrege ,  String statut, String interlocuteur,
			String adresse, String codePostal, String ville, String region, String pays, String telephone,
			String telecopie, String linkedin, String email, String siteWeb, String identifiantTva)
			throws ResourceNotFoundException {
		
		boolean fournisseurExisteWithreference = fournisseurRepository.existsFournisseurByRefFrsIris(refFrsIris);
		
		if (fournisseurExisteWithreference){
			throw new IllegalArgumentException("Reference fournisseur existe déjà !!");
		}
				
		FournisseurDto fournisseurDto = new FournisseurDto();
		fournisseurDto.setRefFrsIris(refFrsIris);
		fournisseurDto.setIntitule(intitule);
		fournisseurDto.setAbrege(abrege);
		fournisseurDto.setStatut(statut);
		fournisseurDto.setInterlocuteur(interlocuteur);
		fournisseurDto.setAdresse(adresse);
		fournisseurDto.setCodePostal(codePostal);
		fournisseurDto.setVille(ville);
		fournisseurDto.setRegion(region);
		fournisseurDto.setPays(pays);
		fournisseurDto.setTelephone(telephone);
		fournisseurDto.setTelecopie(telecopie);
		fournisseurDto.setLinkedin(linkedin);
		fournisseurDto.setEmail(email);
		fournisseurDto.setSiteWeb(siteWeb);
		fournisseurDto.setIdentifiantTva(identifiantTva);
		fournisseurDto.setMiseEnVeille(false);

		fournisseurRepository.save(buildFournisseurFromFournisseurDto(fournisseurDto));

		
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllFournisseur(int page, int size) {
		try {
			List<FournisseurDto> fournisseurs = new ArrayList<FournisseurDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts = fournisseurRepository.findFournisseurByMiseEnVeille(false, paging);
			fournisseurs = pageTuts.getContent().stream().map(fournisseur -> {
				
				try {		
					return 	 buildFournisseurDtoFromFournisseur(fournisseur);
				} catch (ResourceNotFoundException e) {
					throw new RuntimeException(e);
				}

			}).collect(Collectors.toList());
			
			Map<String, Object> response = new HashMap<>();
			response.put("fournisseurs", fournisseurs);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public FournisseurDto getfournisseurById(String id) throws ResourceNotFoundException {
		Optional<Fournisseur> fournisseurOpt = fournisseurRepository.findById(id);
		if (fournisseurOpt.isPresent()) {
			return buildFournisseurDtoFromFournisseur(fournisseurOpt.get());
		}
		return null;

	}

	@Override
	public Fournisseur getFournisseurByIntitule(String intitule) throws ResourceNotFoundException {
		return fournisseurRepository.findByIntitule(intitule).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), intitule)));

	}

	@Override
	public void updateNewFournisseur(String idFournisseur, String refFrsIris, String intitule, String abrege,String statut,
			String interlocuteur, String adresse, String codePostal, String ville, String region, String pays,
			String telephone, String telecopie, String linkedin, String email, String siteWeb,
			String identifiantTva) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mettreEnVeille(String idFournisseur) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllFournisseurEnVeille(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> find(String textToFind, int page, int size, boolean enVeille) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFournisseur(String fournisseurId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFournisseurSelected(List<String> idFournisseursSelected) {
		// TODO Auto-generated method stub
		
	}


}
