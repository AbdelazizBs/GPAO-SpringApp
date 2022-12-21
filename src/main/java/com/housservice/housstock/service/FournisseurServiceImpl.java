package com.housservice.housstock.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.FournisseurMapper;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class FournisseurServiceImpl implements FournisseurService{
	
	private FournisseurRepository fournisseurRepository;

	private final MessageHttpErrorProperties messageHttpErrorProperties;


	public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.fournisseurRepository = fournisseurRepository;
		this.messageHttpErrorProperties = messageHttpErrorProperties;

	}


	@Override
	public void  addFournisseur(FournisseurDto fournisseurDto)   {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fournisseurDto.getEmail());
		if (!fournisseurDto.getEmail().equals("") && !matcher.matches()) throw new IllegalArgumentException("Email incorrecte !!");
		
		/*
		 * if (fournisseurRepository.existsFournisseurByRefFrsIrisAndIntitule(
		 * fournisseurDto.getRefFrsIris(),fournisseurDto.getIntitule())||
		 * fournisseurRepository.existsFournisseurByRefFrsIris(fournisseurDto.
		 * getRefFrsIris())) { throw new IllegalArgumentException( " RefFrsIris " +
		 * fournisseurDto.getRefFrsIris() + " ou Intitule " +
		 * fournisseurDto.getIntitule() + "  existe deja !!"); }
		 */
		final Fournisseur fournisseur = FournisseurMapper.MAPPER.toFournisseur(fournisseurDto);
		 //FournisseurMapper.MAPPER.toFournisseurDto(fournisseurRepository.save(fournisseur));
		fournisseurRepository.save(fournisseur);
	}


	@Override
	public void  updateFournisseur(FournisseurDto fournisseurDto,String idFournisseur) throws ResourceNotFoundException {
		Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), fournisseurDto.getId())));

		
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
		fournisseur.setNomBanque(fournisseurDto.getNomBanque());
		fournisseur.setAdresseBanque(fournisseurDto.getNomBanque());
		fournisseur.setRib(fournisseurDto.getRib());
		fournisseur.setSwift(fournisseurDto.getSwift());
		fournisseur.setCodeDouane(fournisseurDto.getCodeDouane());
		fournisseur.setRne(fournisseurDto.getRne());
	    fournisseur.setIdentifiantTva(fournisseurDto.getIdentifiantTva());

		fournisseurRepository.save(fournisseur);
	}

	@Override
	public FournisseurDto getFournisseurById(String id) throws ResourceNotFoundException {
		Optional<Fournisseur> utilisateurOpt = fournisseurRepository.findById(id);
		if (utilisateurOpt.isPresent()) {
			return FournisseurMapper.MAPPER.toFournisseurDto(utilisateurOpt.get());
		}
		return null;
	}


	@Override
	public Fournisseur getFournisseurByIntitule(String intitule) throws ResourceNotFoundException {
		return fournisseurRepository.findByIntitule(intitule).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), intitule)));
	}



	@Override
	public void mettreEnVeille(String idFournisseur) throws ResourceNotFoundException {

		Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), idFournisseur)));
		fournisseur.setMiseEnVeille(true);
		fournisseurRepository.save(fournisseur);
	}
	
	
	@Override
	public ResponseEntity<Map<String, Object>> getAllFournisseur(int page, int size) {
		try {
			List<FournisseurDto> fournisseurs = new ArrayList<FournisseurDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts = fournisseurRepository.findFournisseurByMiseEnVeille(false, paging);
			fournisseurs = pageTuts.getContent().stream().map(fournisseur -> {
				return FournisseurMapper.MAPPER.toFournisseurDto(fournisseur);
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
	public ResponseEntity<Map<String, Object>> getAllFournisseurEnVeille(int page, int size) {

		try {

			List<FournisseurDto> fournisseurs;
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts = fournisseurRepository.findFournisseurByMiseEnVeille(true, paging);
			fournisseurs = pageTuts.getContent().stream().map(fournisseur -> {
				return FournisseurMapper.MAPPER.toFournisseurDto(fournisseur);
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
	public ResponseEntity<Map<String, Object>> find(String textToFind, int page, int size,boolean enVeille) {

		try {

			List<FournisseurDto> fournisseurs;
			Pageable paging = PageRequest.of(page, size);
			Page<Fournisseur> pageTuts;
			pageTuts = fournisseurRepository.findFournisseurByTextToFindAndMiseEnVeille(textToFind,enVeille, paging);
			fournisseurs = pageTuts.getContent().stream().map(fournisseur -> {
				return FournisseurMapper.MAPPER.toFournisseurDto(fournisseur);
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
	public void deleteFournisseur(String idFournisseur) {
		fournisseurRepository.deleteById(idFournisseur);
	}
	@Override
	public void deleteFournisseurSelected(List<String> idFournisseursSelected){
		for (String id : idFournisseursSelected){
			fournisseurRepository.deleteById(id);
		}
	}

}
