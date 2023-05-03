package com.housservice.housstock.service;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.mapper.PersonnelMapper;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Picture;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.repository.PersonnelRepository;
import com.housservice.housstock.repository.PictureRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class PersonnelServiceImpl implements PersonnelService {

	private final PersonnelRepository personnelRepository;


	private final MessageHttpErrorProperties messageHttpErrorProperties;
	private final PictureRepository pictureRepository;



	public PersonnelServiceImpl(PersonnelRepository personnelRepository, MessageHttpErrorProperties messageHttpErrorProperties,PictureRepository pictureRepository) {
		this.personnelRepository = personnelRepository;
		this.pictureRepository=pictureRepository;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}


	@Override
	public void  addPersonnel(PersonnelDto personnelDto)   {
		try
		{
			Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
			Matcher matcher = pattern.matcher(personnelDto.getEmail());
			if (!matcher.matches()) {
				throw new IllegalArgumentException(	" email " + personnelDto.getEmail() +  "  n'est pas valide !!");
			}

			if (personnelRepository.existsPersonnelByCin(personnelDto.getCin())) {
				throw new IllegalArgumentException(	" cin " + personnelDto.getCin() +  "  existe deja !!");
			}
			if (personnelRepository.existsPersonnelByMatricule(personnelDto.getMatricule())){
				throw new IllegalArgumentException( "matricule" + personnelDto.getMatricule() + "  existe deja !!");
			}
			List<Picture> pictures = new ArrayList<>();
			personnelDto.setPictures(pictures);
			personnelDto.setFullName(personnelDto.getNom()+" "+personnelDto.getPrenom());
			Personnel personnel = PersonnelMapper.MAPPER.toPersonnel(personnelDto);
			personnel.setMiseEnVeille(false);
			List<Picture> pictures1 = new ArrayList<>();
			personnel.setPictures(pictures1);
			personnelRepository.save(personnel);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(e.getMessage());
		}

	}
	@Override
	public void Restaurer(String id) throws ResourceNotFoundException {
		System.out.println(id);
		Personnel personnel = personnelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
		personnel.setMiseEnVeille(false);
		personnelRepository.save(personnel);
	}

	@Override
	public void  updatePersonnel(PersonnelDto personnelDto,String idPersonnel) throws ResourceNotFoundException {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(personnelDto.getEmail());
		Personnel personnel = personnelRepository.findById(idPersonnel)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), personnelDto.getId())));
		if (!personnelDto.getEmail().equals("") && !matcher.matches()) {
			throw new IllegalArgumentException(" email " + personnelDto.getEmail() +  "  n'est pas valide !!");
		}
		personnel.setNom(personnelDto.getNom());
		personnel.setPrenom(personnelDto.getPrenom());
		personnel.setDateNaissance(personnelDto.getDateNaissance());
		personnel.setAdresse(personnelDto.getAdresse());
		personnel.setPhoto(personnelDto.getPhoto());
		personnel.setCin(personnelDto.getCin());
		personnel.setSexe(personnelDto.getSexe());
		personnel.setRib(personnelDto.getRib());
		personnel.setPoste(personnelDto.getPoste());
		personnel.setDateEmbauche(personnelDto.getDateEmbauche());
		personnel.setEchelon(personnelDto.getEchelon());
		personnel.setCategorie(personnelDto.getCategorie());
		personnel.setMatricule(personnelDto.getMatricule());
		personnel.setPhone(personnelDto.getPhone());
		personnel.setVille(personnelDto.getVille());
		personnel.setCodePostal(personnelDto.getCodePostal());
		personnel.setEmail(personnelDto.getEmail());
		personnel.setNumCnss(personnelDto.getNumCnss());
		personnel.setSituationFamiliale(personnelDto.getSituationFamiliale());
		personnel.setNbrEnfant(personnelDto.getNbrEnfant());
		personnel.setTypeContrat(personnelDto.getTypeContrat());
		personnelRepository.save(personnel);
	}

	@Override
	public PersonnelDto getPersonnelById(String id) throws ResourceNotFoundException {
		Optional<Personnel> utilisateurOpt = personnelRepository.findById(id);
		if (utilisateurOpt.isPresent()) {
			return PersonnelMapper.MAPPER.toPersonnelDto(utilisateurOpt.get());
		}
		return null;
	}


	@Override
	public Personnel getPersonnelByNom(String nom) throws ResourceNotFoundException {
		return personnelRepository.findByNom(nom).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), nom)));
	}



	@Override
	public void mettreEnVeille(String idPersonnel) throws ResourceNotFoundException {

		Personnel personnel = personnelRepository.findById(idPersonnel).orElseThrow(() -> new ResourceNotFoundException(
				MessageFormat.format(messageHttpErrorProperties.getError0002(), idPersonnel)));
		personnel.setMiseEnVeille(true);
		personnelRepository.save(personnel);
	}
	@Override
	public ResponseEntity<Map<String, Object>> getAllPersonnel(int page, int size) {
		try {
			List<PersonnelDto> personnels = new ArrayList<PersonnelDto>();
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			pageTuts = personnelRepository.findPersonnelByMiseEnVeille(false, paging);
			personnels = pageTuts.getContent().stream().map(personnel -> {
				return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("personnels", personnels);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Override
	public ResponseEntity<Map<String, Object>> onSortActivePersonnel(int page, int size, String field, String order) {
		try {
			List<PersonnelDto> personnels ;
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			if (order.equals("1")){
				pageTuts = personnelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
			}
			else {
				pageTuts = personnelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
			}
			personnels = pageTuts.getContent().stream().map(personnel -> {
				return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
			}).collect(Collectors.toList());
			personnels =personnels.stream().filter(personnel -> !personnel.isMiseEnVeille()).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("personnels", personnels);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
@Override
	public ResponseEntity<Map<String, Object>> onSortPersonnelNotActive(int page, int size, String field, String order) {
	try {
		List<PersonnelDto> personnels ;
		Pageable paging = PageRequest.of(page, size);
		Page<Personnel> pageTuts;
		if (order.equals("1")){
			pageTuts = personnelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field)));
		}
		else {
			pageTuts = personnelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
		}
		personnels = pageTuts.getContent().stream().map(personnel -> {
			return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
		}).collect(Collectors.toList());
		personnels =personnels.stream().filter(personnel -> personnel.isMiseEnVeille()).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("personnels", personnels);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		return new ResponseEntity<>(response, HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	}




	@Override
	public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(int page, int size) {

		try {

			List<PersonnelDto> personnels;
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			pageTuts = personnelRepository.findPersonnelByMiseEnVeille(true, paging);
			personnels = pageTuts.getContent().stream().map(personnel -> {
				return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
			}).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();

			response.put("personnels", personnels);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> search(String textToFind, int page, int size,boolean enVeille) {

		try {

			List<PersonnelDto> personnels;
			Pageable paging = PageRequest.of(page, size);
			Page<Personnel> pageTuts;
			pageTuts = personnelRepository.findPersonnelByTextToFind(textToFind, paging);
			personnels = pageTuts.getContent().stream().map(personnel -> {
				return PersonnelMapper.MAPPER.toPersonnelDto(personnel);
			}).collect(Collectors.toList());
			personnels = personnels.stream().filter(personnel -> personnel.isMiseEnVeille() == enVeille).collect(Collectors.toList());
			Map<String, Object> response = new HashMap<>();
			response.put("personnels", personnels);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public void deletePersonnel(String idPersonnel) {
		personnelRepository.deleteById(idPersonnel);
	}
	@Override
	public void deletePersonnelSelected(List<String> idPersonnelsSelected){
		for (String id : idPersonnelsSelected){
			personnelRepository.deleteById(id);
		}
	}


	@Override
	public int getPersonnalByMonth() {
		try {
			LocalDate today = LocalDate.now();
			ZoneId defaultZoneId = ZoneId.systemDefault();
			LocalDate startD = LocalDate.now().withDayOfMonth(1);
			Date Firstday = Date.from(startD.atStartOfDay(defaultZoneId).toInstant());
			LocalDate endD = LocalDate.now().withDayOfMonth(today.getMonth().length(today.isLeapYear()));;
			Date Lastday = Date.from(endD.atStartOfDay(defaultZoneId).toInstant());
			List<Personnel> personnal = personnelRepository.findBydateEmbaucheBetween(Firstday, Lastday);
			return (int) personnal.stream().count();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	@Override
	public int getallPersonnal() {
		try {
			List<Personnel> personnel = personnelRepository.findAll();
			return (int) personnel.stream().count();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	@Override
	public List<Integer> getPersListe(boolean b) {
		int date;

		List<Integer> nbPersonnels = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

		List<Personnel> activePersonnels =personnelRepository.findPersonnelByMiseEnVeille(b);
		for(int i = 0; i< activePersonnels.size(); i++){
			Personnel personnel= activePersonnels.get(i);
			date=personnel.getDateEmbauche().getMonth()+1;
			System.out.println(date);
			switch (date){
				case 9:
					nbPersonnels .set(0, nbPersonnels .get(0) + 1);
					break;
				case 10:
					nbPersonnels .set(1, nbPersonnels .get(1) + 1);
					break;
				case 11:
					nbPersonnels .set(2, nbPersonnels .get(2) + 1);
					break;
				case 12:
					nbPersonnels .set(3, nbPersonnels .get(3) + 1);
					break;
				case 1:
					nbPersonnels .set(4, nbPersonnels .get(4) + 1);
					break;
				case 2:
					nbPersonnels .set(5, nbPersonnels .get(5) + 1);
					break;
				case 3:
					nbPersonnels .set(6, nbPersonnels .get(6) + 1);
					break;
			}
		}
		return nbPersonnels ;
	}
	@Override
	public void addphoto(MultipartFile[] images, String email){
		Personnel personnel = personnelRepository.findByEmail(email).get();
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
		personnel.setPictures(pictures);
		personnelRepository.save(personnel);

	}
	@Override
	public void removePictures(String icPersonnel) throws ResourceNotFoundException {
		Personnel personnel = personnelRepository.findById(icPersonnel)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), icPersonnel)));
		for (String id : personnel.getPictures().stream().map(Picture::getId).collect(Collectors.toList())) {
			pictureRepository.deleteById(id);
		}
		personnel.getPictures().removeAll(personnel.getPictures());
		personnelRepository.save(personnel);
	}
	@Override
	public void removePicture(String idPic) throws ResourceNotFoundException {
		Picture picture = pictureRepository.findById(idPic)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), idPic)));
		Personnel personnel = personnelRepository.findPersonnelByPictures(picture)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), picture)));
		pictureRepository.deleteById(idPic);
		personnel.getPictures().removeIf(picture1 -> picture1.equals(picture));
		personnelRepository.save(personnel);
	}
}
