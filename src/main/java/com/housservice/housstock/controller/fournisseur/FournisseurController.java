package com.housservice.housstock.controller.fournisseur;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;

import com.housservice.housstock.service.FournisseurService;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/fournisseur")
@Api(tags = { "fournisseurs Management" })
public class FournisseurController {
	
	private FournisseurService fournisseurService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;

	
	@Autowired
	public FournisseurController(FournisseurService fournisseurService,
			MessageHttpErrorProperties messageHttpErrorProperties)
	{
		this.fournisseurService = fournisseurService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	}

	
	
	@PutMapping("/createNewFournisseur")
	@Validated
	  public ResponseEntity<String> createNewPersonnel(  final String refFrsIris,

													   final String intitule,

													   final String abrege,

													   final String interlocuteur,
														
													   final String adresse,

													   final String codePostal,

													   final String ville,

													   final String region,
														
													   final String pays,
														
													   final String telephone,
														
													   final String telecopie,
														
													   final String linkedin,
														
													   final String email,
														
													   final String siteInternet,
														
													   final String identifiantTva
													   													   
													  ) throws ResourceNotFoundException {
		
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		  if(refFrsIris.equals("") || intitule.equals("") || telephone.equals("") ||  email.equals(""))

		  {
			  throw new IllegalArgumentException("Voulez vous remplir le formulaire !");
		  }

		 if(!email.equals("") && !matcher.matches()){
			throw new IllegalArgumentException("Email incorrecte !!");

		}
    	  fournisseurService.createNewFournisseur(refFrsIris,
    			  intitule,
    			  abrege,
    			  interlocuteur,
    			  adresse,
    			  codePostal,
    			  ville,
    			  region,
    			  pays,
    			  telephone,
    			  telecopie,
    			  linkedin,
    			  email,
    			  siteInternet,
    			  identifiantTva
				  );
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }

	

}
