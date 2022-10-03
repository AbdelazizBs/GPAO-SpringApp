package com.housservice.housstock.controller.utilisateur;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.UtilisateurDto;
import com.housservice.housstock.service.UtilisateurService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/user")
@Api(tags = {"Utilisateurs Management"})
public class UtilisateurController {
	
	private UtilisateurService utilisateurService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    @Autowired
	  public UtilisateurController(UtilisateurService utilisateurService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.utilisateurService = utilisateurService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/getAllUser")
	 public List< UtilisateurDto > getAllUtilisateur() {
		 		
		 return utilisateurService.getAllUtilisateur();
		 	 
	 }

      @GetMapping("/utilisateur/{id}")
	  @ApiOperation(value = "service to get one Utilisateur by Id.")
	  public ResponseEntity < UtilisateurDto > getUtilisateurById(
			  @ApiParam(name = "id", value="id of utilisateur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId)
	  throws ResourceNotFoundException {
    	UtilisateurDto utilisateur = utilisateurService.getUtilisateurById(utilisateurId);
		  if (utilisateur == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(utilisateur);
	  }
    
      @PutMapping("/addUser")
	  public ResponseEntity<String> createUtilisateur(final String nom ,
													  final String prenom ,
													  final Date dateDeNaissance ,
													  final String adresse ,
													  final String photo ,
													  final String email ,
													  final String password
													  ) throws ResourceNotFoundException {
		  
    	  utilisateurService.createNewUtilisateur(nom,
				  prenom,
				  dateDeNaissance,
				  adresse,
				  photo,
				  email,
				  password
				  );
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }

	  @GetMapping("token/refreshToken")
	  public void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		  if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
			  try{
				  String refreshToken = authorizationHeader.substring("Bearer ".length());
				  Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				  JWTVerifier verifier = JWT.require(algorithm).build();
				  DecodedJWT decodedJWT = verifier.verify(refreshToken);
				  String username =decodedJWT.getSubject();
				  Utilisateur user = utilisateurService.getUtilisateurByNom(username);
				  String access_token = JWT.create()
						  .withSubject(user.getNom())
						  .withExpiresAt(new Date(System.currentTimeMillis() +10 *60 * 1000))
						  .withIssuer(request.getRequestURL().toString())
						  .withClaim("roles",user.getRoles().stream().map(Roles::getNom).collect(Collectors.toList()))
						  .sign(algorithm);
				  Map<String,String> tokens = new HashMap<>();
				  tokens.put("access_token",access_token);
				  tokens.put("refresh_token",refreshToken);
				  response.setContentType(APPLICATION_JSON_VALUE);
				  new ObjectMapper().writeValue(response.getOutputStream(),tokens);

			  }catch (Exception exception){
				  response.setHeader("error",exception.getMessage());
				  response.setStatus(FORBIDDEN.value());
				  Map<String,String> error = new HashMap<>();
				  error.put("error_message",exception.getMessage());
				  response.setContentType(APPLICATION_JSON_VALUE);
				  new ObjectMapper().writeValue(response.getOutputStream(),error);
			  }


		  }else {
			throw new  RuntimeException("Refresh token is missing");
		  }
	}
    
      @PutMapping("/utilisateur/{id}")
	  public ResponseEntity <String> updateUtilisateur(
			  @ApiParam(name = "id", value="id of utilisateur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String utilisateurId,
	          @Valid @RequestBody(required = true) UtilisateurDto utilisateurDto) throws ResourceNotFoundException {
		  
    	  utilisateurService.updateUtilisateur(utilisateurDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/utilisateur/{id}")
	  @ApiOperation(value = "service to delete one Utilisateur by Id.")
	  public Map < String, Boolean > deleteUtilisateur(
			  @ApiParam(name = "id", value="id of utilisateur", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId) {

		  utilisateurService.deleteUtilisateur(utilisateurId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }
	
	 
}
 