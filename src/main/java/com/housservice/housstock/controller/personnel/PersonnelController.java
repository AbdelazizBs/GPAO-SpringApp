package com.housservice.housstock.controller.personnel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.service.PersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/personnel")
@Api(tags = { "personnels Management" })
public class PersonnelController {

	private PersonnelService personnelService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;

	public PersonnelController(PersonnelService personnelService,
			MessageHttpErrorProperties messageHttpErrorProperties) {
		this.personnelService = personnelService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}



      @GetMapping("/getPersonnelById/{id}")
	  @ApiOperation(value = "service to get one Utilisateur by Id.")
	  public ResponseEntity <PersonnelDto> getPersonnelById(
			  @ApiParam(name = "id", value="id of utilisateur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId)
	  throws ResourceNotFoundException {
    	PersonnelDto utilisateur = personnelService.getPersonnelById(utilisateurId);
		  if (utilisateur == null) {
			  ResponseEntity.badRequest();
		  }

	      return ResponseEntity.ok().body(utilisateur);
	  }



	@PutMapping("/addPersonnel")
	@ApiOperation(value = "service to add new Personnel")
	public ResponseEntity<PersonnelDto> addPersonnel(@Valid @RequestBody PersonnelDto personnelDto) throws ResourceNotFoundException {
//		String regex = "^(.+)@(.+)$";
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(personnelDto.getEmail());
//
//		if(!personnelDto.getEmail().equals("") && !matcher.matches()){
//			throw new IllegalArgumentException("Email incorrecte !!");
//
//		}
		return personnelService.addPersonnel(personnelDto);
//		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

	}

//	@PutMapping("/createNewPersonnel")
//	@Validated
//	  public ResponseEntity<String> createNewPersonnel( final String nom ,
//													  final String prenom ,
//													  final Date dateNaissance ,
//													  final String adresse ,
//													   final String matricule,
//													   final String photo ,
//													  final String cin,
//													  final String sexe,
//													  final String rib,
//													  final String poste,
//													   final Date dateEmbauche,
//													  final String phone,
//													  final String categorie,
//													  final String ville,
//													  final String codePostal,
//													   final String email,
//													   final String numCnss,
//													   final String typeContrat,
//													   final String situationFamiliale,
//													   final String nbrEnfant
//													  ) throws ResourceNotFoundException {
//		String regex = "^(.+)@(.+)$";
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(email);
//
//		  if(nom.equals("") || prenom.equals("") || adresse.equals("") ||  cin.equals("")
//				  || dateEmbauche.equals("") || dateNaissance.equals(""))
//
//		  {
//			  throw new IllegalArgumentException("Voulez vous remplir le formulaire !");
//		  }
//
//		 if(!email.equals("") && !matcher.matches()){
//			throw new IllegalArgumentException("Email incorrecte !!");
//
//		}
//    	  personnelService.createNewPersonnel(nom,
//				  prenom,
//				  dateNaissance,
//				  adresse,
//				  matricule,
//				  photo,
//				   cin,
//				   sexe,
//				   rib,
//				   poste,
//				  dateEmbauche,
//				  phone,
//				  categorie,
//				  ville,
//				  codePostal,
//				  email,
//				  numCnss,
//				  typeContrat,
//				  situationFamiliale,
//				  nbrEnfant
//				  );
//	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
//	  }
	  @GetMapping("token/refreshToken")
	  public void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {

		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refreshToken = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				String username = decodedJWT.getSubject();
				Personnel user = personnelService.getPersonnelByNom(username);
				String access_token = JWT.create().withSubject(user.getNom())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles",
								user.getCompte().getRoles().stream().map(Roles::getNom).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refreshToken);
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				response.setStatus(FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}

      @PutMapping("/updatePersonnel/{idPersonnel}")
	  public ResponseEntity <String> updatePersonnel(
			  @ApiParam(name = "idPersonnel", value="id of personnel", required = true)
			  @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}")
			    String idPersonnel,
			  final String nom ,
			  final String prenom ,
			 Date dateNaissance ,
			  final String matricule,
			  final String adresse ,
			  final String photo ,
			  final String cin,
			  final String sexe,
			  final String rib,
			  final String poste,
			 Date dateEmbauche,
			  final String echelon,
			  final String phone,
			  final String categorie,
			  final String ville,
			  final  String  codePostal,
			    final  String  email,
			  final String numCnss,
			  final String typeContrat,
			  final String situationFamiliale,
			  final String nbrEnfant
			  ) throws ResourceNotFoundException {
		  String regex = "^(.+)@(.+)$";
		  Pattern pattern = Pattern.compile(regex);
		  Matcher matcher = pattern.matcher(email);

		  if(nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() ||  cin.isEmpty()
				  || dateEmbauche.toString().isEmpty() || dateNaissance.toString().isEmpty())
		  {
			  throw new IllegalArgumentException("Voulez vous remplir le formulaire !");
		  }
		  if(!matcher.matches()){
			  throw new IllegalArgumentException( "Email incorrecte !!");
		  }

			  personnelService.updatePersonnel(
					  idPersonnel,
					  nom,
					  prenom,
					  dateNaissance,
					  adresse,
					  matricule,
					  photo,
					  cin,
					  sexe,
					  rib,
					  poste,
					  dateEmbauche,
					  echelon,
					  phone,
					  categorie,
					  ville,
					  codePostal,
					  email,
					  numCnss,
					  typeContrat,
					  situationFamiliale,
					  nbrEnfant
			  );
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

	@PutMapping("/mettreEnVeille/{idPersonnel}")
	public ResponseEntity<String> mettreEnVeille(
			@ApiParam(name = "idPersonnel", value = "id of personnel", required = true) @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}") String idPersonnel)
			throws ResourceNotFoundException {
		personnelService.mettreEnVeille(idPersonnel);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}



	@GetMapping("/getAllPersonnel")
	@ApiOperation(value = "service to get get All Personnel")
	public ResponseEntity<Map<String, Object>> getAllPersonnel(@RequestParam(defaultValue = "0") int page,
															   @RequestParam(defaultValue = "3") int size){
		return personnelService.getAllPersonnel(page,size);

	}
	@GetMapping("/getAllPersonnelEnVeille")
	@ApiOperation(value = "service to get get All Personnel En Veille ")
	public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(@RequestParam(defaultValue = "0") int page,
																	   @RequestParam(defaultValue = "3") int size) {
		return personnelService.getAllPersonnelEnVeille(page, size);

	}
	@GetMapping("/search")
	@ApiOperation(value = "service to filter personnel ")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
													  @RequestParam boolean enVeille,
													  @RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "3") int size) {
		return personnelService.find(textToFind, page, size,enVeille);

	}

	@DeleteMapping("/deletePersonnel/{id}")
	@ApiOperation(value = "service to delete one Personnel by Id.")
	public Map<String, Boolean> deletePersonnel(
			@ApiParam(name = "id", value = "id of personnel", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId) {

		personnelService.deletePersonnel(utilisateurId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/deleteSelectedPersonnel/{idPersonnelsSelected}")
	@ApiOperation(value = "service to delete many Personnel by Id.")
	public Map<String, Boolean> deletePersonnelSelected(
			@ApiParam(name = "idPersonnelsSelected", value = "ids of personnel Selected", required = true) @PathVariable(value = "idPersonnelsSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idPersonnelsSelected) {
		personnelService.deletePersonnelSelected(idPersonnelsSelected);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
