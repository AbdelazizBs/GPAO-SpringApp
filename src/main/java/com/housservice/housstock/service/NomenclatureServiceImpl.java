package com.housservice.housstock.service;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.NomenclatureMapper;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.model.enums.TypeNomEnClature;
import com.housservice.housstock.repository.NomenclatureRepository;
import com.housservice.housstock.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Service
public class NomenclatureServiceImpl implements NomenclatureService {

    private final NomenclatureRepository nomenclatureRepository;
	
	final PictureRepository pictureRepository;

	private final MessageHttpErrorProperties messageHttpErrorProperties;
	
	
	@Autowired
	public NomenclatureServiceImpl(NomenclatureRepository nomenclatureRepository, SequenceGeneratorService sequenceGeneratorService,
							 MessageHttpErrorProperties messageHttpErrorProperties, PictureRepository pictureRepository) {
		this.nomenclatureRepository = nomenclatureRepository;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.pictureRepository = pictureRepository;
	}
	
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}


	@Override
	public ResponseEntity<Map<String, Object>> findNomenclatureActif(int page, int size) {

		try {
			List<NomenclatureDto> nomenclatures;
			Pageable paging = PageRequest.of(page, size);
			Page<Nomenclature> pageTuts;
			pageTuts =  nomenclatureRepository.findNomenclatureActif(paging);
			nomenclatures = pageTuts.getContent().stream().map(nomenclature ->
					NomenclatureMapper.MAPPER.toNomenclatureDto(nomenclature)).collect(Collectors.toList());
			nomenclatures = nomenclatures.stream().filter(nomenclature -> nomenclature.getParentsName().isEmpty())
					.collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("nomenclatures", nomenclatures);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	@Override
	public ResponseEntity<Map<String, Object>> findNomenclatureNonActive(int page, int size) {
		try {
			List<NomenclatureDto> nomenclatures;
			Pageable paging = PageRequest.of(page, size);
			Page<Nomenclature> pageTuts;
			pageTuts =  nomenclatureRepository.findNomenclatureNotActif(paging);
			nomenclatures = pageTuts.getContent().stream().map(nomenclature ->
					NomenclatureMapper.MAPPER.toNomenclatureDto(nomenclature)).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("nomenclatures", nomenclatures);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<Map<String, Object>> getIdNomenclatures(String nomNomenclature)
			throws ResourceNotFoundException {
		
		Nomenclature nomenclature = nomenclatureRepository.findNomenclatureByNomNomenclature(nomNomenclature).orElseThrow(()
				-> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),nomNomenclature)));
		Map<String, Object> response = new HashMap<>();
		response.put("idNomenclature", nomenclature.getId());
		//response.put("refIris", nomenclature.getRefIris());
		return ResponseEntity.ok(response);
	}


	@Override
	public List<String> getNomNomenclatures() {
		List<Nomenclature> nomenclatures = nomenclatureRepository.findAll();
		return nomenclatures.stream()
				.map(Nomenclature::getNomNomenclature)
				.collect(Collectors.toList());
	}


	@Override
	public Optional<Nomenclature> getNomenclatureById(String id) {
		return nomenclatureRepository.findById(id);
	}


	@Override
	public void createNewNomenclature(String nomNomenclature,
									  List<String> parentsName,
									  String description, String type, String nature,
			String categorie, MultipartFile[] images) throws ResourceNotFoundException {
		if (nomenclatureRepository.existsNomenclatureByNomNomenclature(nomNomenclature)) {
			throw new IllegalArgumentException(	"Nom nomenclature existe déja !!");
		}
			NomenclatureDto nomenclatureDto = new NomenclatureDto();
			List<Picture> pictures = new ArrayList<>();
		for (MultipartFile file : images) {
			Picture picture = new Picture();
			picture.setFileName(file.getOriginalFilename());
			picture.setType(file.getContentType());
			try {
				picture.setBytes(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			pictureRepository.save(picture);
			pictures.add(picture);
		}
		nomenclatureDto.setType(TypeNomEnClature.valueOf(type));
		nomenclatureDto.setPictures(pictures);
		nomenclatureDto.setDate(new Date());
		nomenclatureDto.setMiseEnVeille(0);
		nomenclatureDto.setNomNomenclature(nomNomenclature);
		nomenclatureDto.setDescription(description);
		nomenclatureDto.setNature(nature);
		nomenclatureDto.setCategorie(categorie);
		nomenclatureDto.setParentsName(parentsName);
		Nomenclature nomenclature = NomenclatureMapper.MAPPER.toNomenclature(nomenclatureDto);
		nomenclature.setChildrensId(new ArrayList<>());
		nomenclatureRepository.save(nomenclature);
		if (nomenclatureDto.getParentsName() != null) {
			List<String> childrens = new ArrayList<>();
			for (String parentName : nomenclatureDto.getParentsName() ) {
				try {
					Nomenclature realChild = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
							new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),parentName)));
					childrens.add(nomenclature.getId());
					childrens.addAll(realChild.getChildrensId());
					realChild.setChildrensId(childrens);
					nomenclatureRepository.save(realChild);
				} catch (ResourceNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public void updateNomenclature(String idNomenclature, String nomNomenclature, String description, String type,
			String nature, String categorie,List<String> parentsName, MultipartFile[] images) throws ResourceNotFoundException {
		
		if (nomNomenclature.isEmpty() || description.isEmpty() || type.isEmpty() || nature.isEmpty() || categorie.isEmpty()) {
			throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires !!");
		}
		Nomenclature nomenclature = getNomenclatureById(idNomenclature)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),  idNomenclature)));
		List<Picture> pictures = new ArrayList<>();
		if (images != null) {
			for (MultipartFile file : images) {
				Picture picture = new Picture();
				picture.setFileName(file.getOriginalFilename());
				picture.setType(file.getContentType());
				try {
					picture.setBytes(file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				pictures.add(picture);
				pictureRepository.save(picture);
			}
		}
		pictures.addAll(nomenclature.getPictures());
		nomenclature.setPictures(pictures);
		nomenclature.setType(TypeNomEnClature.valueOf(type));
		nomenclature.setDate(nomenclature.getDate());
		nomenclature.setMiseEnVeille(nomenclature.getMiseEnVeille());
		nomenclature.setDateMiseEnVeille(nomenclature.getDateMiseEnVeille());
		nomenclature.setDescription(description);
		nomenclature.setNature(nature);
		nomenclature.setCategorie(categorie);
		nomenclature.setNomNomenclature(nomNomenclature);
		if (parentsName.isEmpty()){
			for (String parentName : nomenclature.getParentsName() ) {
				try {
					Nomenclature parentNo = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
							new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),parentName)));
					parentNo.getChildrensId().removeIf(id -> !nomenclature.getId().contains(id));
					nomenclatureRepository.save(parentNo);
				} catch (ResourceNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		}
		if (!parentsName.isEmpty()) {
			List<String> childrens = new ArrayList<>();
			for (String parentName : parentsName ) {
				try {
					Nomenclature realChild = nomenclatureRepository.findNomenclatureByNomNomenclature(parentName).orElseThrow(() ->
							new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(),parentName)));
					realChild.getChildrensId().removeIf(id -> !nomenclature.getId().contains(id));
					childrens.add(nomenclature.getId());
					childrens.addAll(realChild.getChildrensId());
					realChild.setChildrensId(childrens);
					nomenclatureRepository.save(realChild);
				} catch (ResourceNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		}
		nomenclature.setParentsName(parentsName);
		nomenclatureRepository.save(nomenclature);
	}




	@Override
	public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size, boolean enVeille) {

		try {

			List<NomenclatureDto> nomenclatures;
			Pageable paging = PageRequest.of(page, size);
			Page<Nomenclature> pageTuts;
			pageTuts = nomenclatureRepository.findNomenclatureByTextToFindAndMiseEnVeille(textToFind,enVeille, paging);
			nomenclatures = pageTuts.getContent().stream().map(
					nomenclature -> {
						return NomenclatureMapper.MAPPER.toNomenclatureDto(nomenclature);

					}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("nomenclatures", nomenclatures);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Override
	public void miseEnVeille(String idNomenclature) throws ResourceNotFoundException {
		Nomenclature nomenclature = nomenclatureRepository.findById(idNomenclature)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
		nomenclature.setMiseEnVeille(1);
		nomenclatureRepository.save(nomenclature);
		
	}


	@Override
	public void deleteNomenclature(Nomenclature nomenclature) {
		nomenclatureRepository.delete(nomenclature);
		
	}


	@Override
	public void deleteNomenclatureSelected(List<String> idNomenclaturesSelected) {
		for (String id : idNomenclaturesSelected){
			nomenclatureRepository.deleteById(id);
		}
		
		
	}


	@Override
	public void removePictures(String idNomenclature) throws ResourceNotFoundException {
		Nomenclature nomenclature = nomenclatureRepository.findById(idNomenclature)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomenclature)));
		for (String id : nomenclature.getPictures().stream().map(Picture::getId).collect(Collectors.toList())) {
			pictureRepository.deleteById(id);
		}
		nomenclature.getPictures().removeAll(nomenclature.getPictures());
		nomenclatureRepository.save(nomenclature);
		
	}


	@Override
	public void removePicture(String idPic) throws ResourceNotFoundException {
		
		Picture picture = pictureRepository.findById(idPic)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPic)));
		Nomenclature nomenclature = nomenclatureRepository.findNomenclatureByPictures(picture)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), picture)));
		pictureRepository.deleteById(idPic);
		nomenclature.getPictures().removeIf(picture1 -> picture1.equals(picture));
		nomenclatureRepository.save(nomenclature);
	}



	@Override
	public List<String> getParent( )   {
		List<Nomenclature> nomenclatures = nomenclatureRepository.findNomenclatureByMiseEnVeille(0) ;
		// return list nomenclatures after filtered by type
		return nomenclatures.stream().filter(nomenclature -> !nomenclature.getType().equals("Element"))
				.map(Nomenclature::getNomNomenclature).collect(Collectors.toList());

	}


	@Override
	public List<Nomenclature> getLigneSousFamilleByIdFamille(String idNomEnClature) throws ResourceNotFoundException {
	List<Nomenclature> nomenclatures = new ArrayList<>();
		Nomenclature nomenclature = nomenclatureRepository.findById
				(idNomEnClature).orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idNomEnClature)));
		nomenclature.getChildrensId().stream().map(
				id -> {
					try {
						return nomenclatures.add(nomenclatureRepository.findById(id).orElseThrow(() ->
								new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id))));

					} catch (ResourceNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
		).collect(Collectors.toList());
		return nomenclatures;
	}



}
