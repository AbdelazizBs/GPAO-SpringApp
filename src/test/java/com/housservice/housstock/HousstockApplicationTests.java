package com.housservice.housstock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.housservice.housstock.mapper.PersonnelMapper;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.repository.PersonnelRepository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;



@SpringBootTest
class HousstockApplicationTests {

	@Autowired
	private PersonnelRepository personnelRepository;
	
	@Test
	public void testCreatePersonnel() {
		
		Date date = new Date(1990, 3, 3);
		Date dateDeEmbauche=new Date("14/09/2022");
		Personnel personnel=new Personnel("Yassine Ben salah", date,"Monastir", "",
				  "06757865",  "Femme", "757865", "Conducteur machine", dateDeEmbauche,
                  "E02", "C01", "mat001", "20456987 ",
                  false,"Monastir", "5020",
                  "aymen@gmail.com");		
		personnelRepository.save(personnel);	
		
	
	}
	
	@Test
	public void testFindPersonnelByID()
	{
		Personnel p = personnelRepository.findById("6438047cb0b84122291dc9e1").get();
		System.out.println(p);
		assertNotNull(p);
        assertEquals("Monastir", p.getAdresse());
	
	}
	
	@Test
	public void testFindPersonnelByNom()
	{
		Personnel p = personnelRepository.findByNom("Aziz").get();
		System.out.println(p);
		assertNotNull(p);     
	
	}
	
	@Test
	public void testUpdatePersonnel() 
	{
		Personnel p = personnelRepository.findById("6438047cb0b84122291dc9e1").get();
		p.setNom("Aziz");
		personnelRepository.save(p);
		System.out.println(p);
		
	}
	
	@Test
	public void testFindAllPersonnel()
	{
		List<Personnel> pers = personnelRepository.findAll();
		
		for(Personnel p:pers)
		{
			System.out.println(p);
		}
		
	}
	
	@Test
	public void testDeletePersonnel()
	{
		personnelRepository.deleteById("643925fa5c90692782511825");
		Optional<Personnel> deletedPersonnel = personnelRepository.findById("643925fa5c90692782511825");
		assertFalse(deletedPersonnel.isPresent());

		
	}
	
	@Test
	public void testDeleteListPersonnel(){
		List<String> idPersonnalsSelected=new ArrayList<>();
		idPersonnalsSelected.add("64392992a959af09683aca17");
		idPersonnalsSelected.add("64391c321886f8280b404a23");
		for (String id : idPersonnalsSelected){
			personnelRepository.deleteById(id);
		}
		Optional<Personnel> deletedPersonnal1 = personnelRepository.findById("64392992a959af09683aca17");
		Optional<Personnel> deletedPersonnal2 = personnelRepository.findById("64391c321886f8280b404a23");
		assertFalse(deletedPersonnal1.isPresent());
		assertFalse(deletedPersonnal2.isPresent());

	}
	
	@Test
	public void testOnSortActivePersonnel(){
		List<PersonnelDto> activePersonnels=new ArrayList<>();

		Page<Personnel> pageTuts;
		String order="1";
		if (order.equals("1")){
			pageTuts = personnelRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC,"matricule" )));
		}
		else {
			pageTuts = personnelRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "matricule")));
		}
		activePersonnels = pageTuts.getContent().stream().map(personnel-> PersonnelMapper.MAPPER.toPersonnelDto(personnel)).collect(Collectors.toList());
		activePersonnels =activePersonnels.stream().filter(personnel -> !personnel.isMiseEnVeille()).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("personnel", activePersonnels);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());

		activePersonnels.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
		;

	}

	
	@Test
	public void testOnSortNotActivePersonnel(){
		List<PersonnelDto> notActivePersonnels=new ArrayList<>();

		Page<Personnel> pageTuts;
		String order="1";
		if (order.equals("1")){
			pageTuts = personnelRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC,"matricule" )));
		}
		else {
			pageTuts = personnelRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "matricule")));
		}
		notActivePersonnels = pageTuts.getContent().stream().map(personnel-> PersonnelMapper.MAPPER.toPersonnelDto(personnel)).collect(Collectors.toList());
		notActivePersonnels =notActivePersonnels.stream().filter(PersonnelDto::isMiseEnVeille).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("personnel", notActivePersonnels);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		assertNotNull(pageTuts.getContent());

		notActivePersonnels.forEach(clientDto -> assertFalse(clientDto.isMiseEnVeille()));
		;

	}


	
	@Test
	public void testMettreEnVeillePersonnel(){
		Personnel p=personnelRepository.findById("64392992a959af09683aca17").get();
		p.setMiseEnVeille(true);
		personnelRepository.save(p);

		Personnel updatedPersonnal = personnelRepository.findById("64392992a959af09683aca17").get();
		assertTrue(updatedPersonnal.isMiseEnVeille());
	}
	
	@Test
	public void getConducteur()
	{ 
	  List<Personnel> pers = personnelRepository.findAll();
		
		for(Personnel p:pers)
		{
			if (p.getPoste().equals("Conducteur machine"))
			{
				System.out.println(p);
				assertNotNull(p);
			}
			
		}
	
	}

}
