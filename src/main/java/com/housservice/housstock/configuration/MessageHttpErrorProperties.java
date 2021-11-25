package com.housservice.housstock.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PropertySource("classpath:ValidationMessages.properties")
@Configuration
@ConfigurationProperties(prefix = "http")
public class MessageHttpErrorProperties {

	/**
	 * http.error.0001=le parametre {1} est obligatoire
	 */
	private String error0001;
	
	/**
	 * http.error.0002=Nomenclature avec l'id {1} non trouvé
	 */
	private String error0002;
	
	/**
	 * http.error.0003=success : Create new Nomenclature is OK
	 */
	private String error0003;
	
	/**
	 * http.error.0004=success : update Nomenclature is OK
	 */
	private String error0004;
	
	/**
	 * http.error.0005=le mot utilisé pour la recherche est vide.
	 */
	private String error0005;
	
	/**
	 * http.error.0006=la taille de mot de recherche doit etre supèrieur ou egale à 3.
	 */
	private String error0006;
}
