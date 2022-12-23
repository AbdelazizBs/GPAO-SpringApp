package com.housservice.housstock.service;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.MatiereMapper;

import com.housservice.housstock.model.Matiere;
import com.housservice.housstock.model.dto.MatiereDto;
import com.housservice.housstock.repository.MatiereRepository;


@Service
public class MatiereServiceImpl implements MatiereService {
	
	private MatiereRepository matiereRepository;

	private final MessageHttpErrorProperties messageHttpErrorProperties;

	public MatiereServiceImpl(MatiereRepository matiereRepository, MessageHttpErrorProperties messageHttpErrorProperties) {
		
		this.matiereRepository = matiereRepository;
		this.messageHttpErrorProperties = messageHttpErrorProperties;

	}


	@Override
	public void  addMatiere(MatiereDto matiereDto)   {
	
		if (matiereRepository.existsMatiereByRefMatiereIrisAndDesignation(matiereDto.getRefMatiereIris(),matiereDto.getDesignation())|| matiereRepository.existsMatiereByRefMatiereIris(matiereDto.getRefMatiereIris())) {
			throw new IllegalArgumentException(	" reference matiere iris " + matiereDto.getRefMatiereIris() + " ou designation " + matiereDto.getDesignation() + "  existe deja !!");
		}
			
		final Matiere matiere = MatiereMapper.MAPPER.toMatiere(matiereDto);
		 MatiereMapper.MAPPER.toMatiereDto(matiereRepository.save(matiere));
	}


	@Override
	public void  updateMatiere(MatiereDto matiereDto,String idMatiere) throws ResourceNotFoundException {
		
		Matiere matiere = matiereRepository.findById(idMatiere)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), matiereDto.getId())));
		
		matiere.setRefMatiereIris(matiereDto.getRefMatiereIris());
		matiere.setDesignation(matiereDto.getDesignation());
		matiere.setFamille(matiereDto.getFamille());
		matiere.setUniteAchat(matiereDto.getUniteAchat());
		
		matiereRepository.save(matiere);
	}

	@Override
	public MatiereDto getMatiereById(String id) throws ResourceNotFoundException {
		Optional<Matiere> utilisateurOpt = matiereRepository.findById(id);
		if (utilisateurOpt.isPresent()) {
			return MatiereMapper.MAPPER.toMatiereDto(utilisateurOpt.get());
		}
		return null;
	}

	


	@Override
	public Matiere getMatiereByRefMatiereIris(String refMatiereIris) throws ResourceNotFoundException {
		return matiereRepository.findByRefMatiereIris(refMatiereIris).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), refMatiereIris)));
	}

	
	@Override
	public Matiere getMatiereByDesignation(String designation) throws ResourceNotFoundException {
		return matiereRepository.findByDesignation(designation).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), designation)));
	}

	
	@Override
	public ResponseEntity<Map<String, Object>> getAllMatiere(int page, int size) {
		try {
			List<MatiereDto> matieres = new ArrayList<MatiereDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Matiere> pageTuts;
			pageTuts = matiereRepository.findMatiereByMiseEnVeille(false, paging);
			matieres = pageTuts.getContent().stream().map(matiere -> {
		
				return MatiereMapper.MAPPER.toMatiereDto(matiere);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("matieres", matieres);
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

			List<MatiereDto> matieres;
			Pageable paging = PageRequest.of(page, size);
			Page<Matiere> pageTuts;
			pageTuts = matiereRepository.findMatiereByTextToFindAndMiseEnVeille(textToFind,enVeille, paging);
			matieres = pageTuts.getContent().stream().map(matiere -> {
				return MatiereMapper.MAPPER.toMatiereDto(matiere);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("matieres", matieres);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Override
	public void deleteMatiere(String idMatiere) {
		matiereRepository.deleteById(idMatiere);
	}
	
	@Override
	public void deleteMatiereSelected(List<String> idMatieresSelected){
		for (String id : idMatieresSelected){
			matiereRepository.deleteById(id);
		}
	}


	@Override
	public void mettreEnVeille(String idMatiere) throws ResourceNotFoundException {
		Matiere matiere = matiereRepository.findById(idMatiere).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), idMatiere)));
		matiere.setMiseEnVeille(true);
		matiereRepository.save(matiere);
		
	}


	@Override
	public ResponseEntity<Map<String, Object>> getAllMatiereEnVeille(int page, int size) {
		try {

			List<MatiereDto> matieres;
			Pageable paging = PageRequest.of(page, size);
			Page<Matiere> pageTuts;
			pageTuts = matiereRepository.findMatiereByMiseEnVeille(true, paging);
			matieres = pageTuts.getContent().stream().map(matiere -> {
				return MatiereMapper.MAPPER.toMatiereDto(matiere);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();

			response.put("matieres", matieres);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}




}
