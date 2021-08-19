package com.housservice.housstock.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;


@Document(collection="Client")
public class Client {
	
	@Transient
	public static final String SEQUENCE_NAME ="client_sequence";
	
	@Id
	private String id;
	

	// Automatiquement ajouté à partir de la date système
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate date;
	
	//private LocalDate date = LocalDate.now();
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String raison_social;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String regime;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse_facturation;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse_liv;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String Incoterm;

    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String echeance;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String Mode_pai;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String nom_banque;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String adresse_banque;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String RIB;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String SWIFT;
	
    
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String blocage;
	
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private int MiseEnVeille;
	

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate date_MiseEnVeille;
	
	
	private BrancheActivite brancheActivite;
	
	private SecteurActivite secteurActivite;
	
	private List<CommandeClient> listCommandes = new ArrayList<>();
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return the raison_social
	 */
	public String getRaison_social() {
		return raison_social;
	}

	/**
	 * @param raison_social the raison_social to set
	 */
	public void setRaison_social(String raison_social) {
		this.raison_social = raison_social;
	}

	/**
	 * @return the regime
	 */
	public String getRegime() {
		return regime;
	}

	/**
	 * @param regime the regime to set
	 */
	public void setRegime(String regime) {
		this.regime = regime;
	}

	/**
	 * @return the adresse_facturation
	 */
	public String getAdresse_facturation() {
		return adresse_facturation;
	}

	/**
	 * @param adresse_facturation the adresse_facturation to set
	 */
	public void setAdresse_facturation(String adresse_facturation) {
		this.adresse_facturation = adresse_facturation;
	}

	/**
	 * @return the adresse_liv
	 */
	public String getAdresse_liv() {
		return adresse_liv;
	}

	/**
	 * @param adresse_liv the adresse_liv to set
	 */
	public void setAdresse_liv(String adresse_liv) {
		this.adresse_liv = adresse_liv;
	}

	/**
	 * @return the incoterm
	 */
	public String getIncoterm() {
		return Incoterm;
	}

	/**
	 * @param incoterm the incoterm to set
	 */
	public void setIncoterm(String incoterm) {
		Incoterm = incoterm;
	}

	/**
	 * @return the echeance
	 */
	public String getEcheance() {
		return echeance;
	}

	/**
	 * @param echeance the echeance to set
	 */
	public void setEcheance(String echeance) {
		this.echeance = echeance;
	}

	/**
	 * @return the mode_pai
	 */
	public String getMode_pai() {
		return Mode_pai;
	}

	/**
	 * @param mode_pai the mode_pai to set
	 */
	public void setMode_pai(String mode_pai) {
		Mode_pai = mode_pai;
	}

	/**
	 * @return the nom_banque
	 */
	public String getNom_banque() {
		return nom_banque;
	}

	/**
	 * @param nom_banque the nom_banque to set
	 */
	public void setNom_banque(String nom_banque) {
		this.nom_banque = nom_banque;
	}

	/**
	 * @return the adresse_banque
	 */
	public String getAdresse_banque() {
		return adresse_banque;
	}

	/**
	 * @param adresse_banque the adresse_banque to set
	 */
	public void setAdresse_banque(String adresse_banque) {
		this.adresse_banque = adresse_banque;
	}

	/**
	 * @return the rIB
	 */
	public String getRIB() {
		return RIB;
	}

	/**
	 * @param rIB the rIB to set
	 */
	public void setRIB(String rIB) {
		RIB = rIB;
	}

	/**
	 * @return the sWIFT
	 */
	public String getSWIFT() {
		return SWIFT;
	}

	/**
	 * @param sWIFT the sWIFT to set
	 */
	public void setSWIFT(String sWIFT) {
		SWIFT = sWIFT;
	}

	/**
	 * @return the blocage
	 */
	public String getBlocage() {
		return blocage;
	}

	/**
	 * @param blocage the blocage to set
	 */
	public void setBlocage(String blocage) {
		this.blocage = blocage;
	}

	/**
	 * @return the miseEnVeille
	 */
	public int getMiseEnVeille() {
		return MiseEnVeille;
	}

	/**
	 * @param miseEnVeille the miseEnVeille to set
	 */
	public void setMiseEnVeille(int miseEnVeille) {
		MiseEnVeille = miseEnVeille;
	}

	/**
	 * @return the date_MiseEnVeille
	 */
	public LocalDate getDate_MiseEnVeille() {
		return date_MiseEnVeille;
	}

	/**
	 * @param date_MiseEnVeille the date_MiseEnVeille to set
	 */
	public void setDate_MiseEnVeille(LocalDate date_MiseEnVeille) {
		this.date_MiseEnVeille = date_MiseEnVeille;
	}

	/**
	 * @return the brancheActivite
	 */
	public BrancheActivite getBrancheActivite() {
		return brancheActivite;
	}

	/**
	 * @param brancheActivite the brancheActivite to set
	 */
	public void setBrancheActivite(BrancheActivite brancheActivite) {
		this.brancheActivite = brancheActivite;
	}

	/**
	 * @return the secteurActivite
	 */
	public SecteurActivite getSecteurActivite() {
		return secteurActivite;
	}

	/**
	 * @param secteurActivite the secteurActivite to set
	 */
	public void setSecteurActivite(SecteurActivite secteurActivite) {
		this.secteurActivite = secteurActivite;
	}

	/**
	 * @return the listCommandes
	 */
	public List<CommandeClient> getListCommandes() {
		return listCommandes;
	}

	/**
	 * @param listCommandes the listCommandes to set
	 */
	public void setListCommandes(List<CommandeClient> listCommandes) {
		this.listCommandes = listCommandes;
	}

	

}
