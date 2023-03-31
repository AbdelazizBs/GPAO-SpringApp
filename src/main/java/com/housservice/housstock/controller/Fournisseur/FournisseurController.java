package com.housservice.housstock.controller.Fournisseur;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.dto.FournisseurDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.service.FournisseurService;
import com.housservice.housstock.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/fournisseur")
@Api(tags = {"Fournisseurs Management"})
@Validated
public class FournisseurController {

	private final FournisseurService fournisseurService;

	private final MessageHttpErrorProperties messageHttpErrorProperties;
	final PictureService  pictureService;
	@Autowired
	public FournisseurController(FournisseurService fournisseurService, MessageHttpErrorProperties messageHttpErrorProperties,
								 PictureService pictureService) {
		this.fournisseurService = fournisseurService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
		this.pictureService = pictureService;
	}

	@GetMapping("/getAllFournisseur")
	@ApiOperation(value = "service to get tout les fournisseurs ")
	public ResponseEntity<Map<String, Object>> getActiveFournisseur(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return fournisseurService.getActiveFournisseur(page,size);

	}


	@GetMapping("/fournisseur/{id}")
	@ApiOperation(value = "service to get one Fournisseur by Id.")
	public ResponseEntity < Fournisseur > getFournisseurById(
			@ApiParam(name = "id", value="id of fournisseur", required = true)
			@PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String fournisseurId)
			throws ResourceNotFoundException {
		Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), fournisseurId)));
		return ResponseEntity.ok().body(fournisseur);
	}


	@GetMapping("/getIdFournisseurs/{raisonSociale}")
	@ApiOperation(value = "service to get Id Fournisseur by raisonSociale.")

	public ResponseEntity<Map<String, Object>>  getIdFournisseurs(  @ApiParam(name = "raisonSociale", value="raisonSociale of fournisseurs", required = true)
																	@PathVariable(value = "raisonSociale", required = true) @NotEmpty(message = "{http.error.0001}") String raisonSociale) throws ResourceNotFoundException {
		return fournisseurService.getIdFournisseurs(raisonSociale);
	}

	@GetMapping("/getRaisonSociales")
	@ApiOperation(value = "service to get one Raison Sociale")
	public List<String> getRaisonSociales() {
		return fournisseurService.getRaisonSociales();
	}




	@GetMapping("/getAllFournisseurNonActive")
	public ResponseEntity<Map<String, Object>> getAllFournisseurNonActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		return fournisseurService.getFournisseurNotActive(page,size);

	}


	@GetMapping("/search")
	@ApiOperation(value = "service to filter fournisseurs ")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
													  @RequestParam boolean enVeille,
													  @RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "3") int size) {
		return fournisseurService.search(textToFind, page, size,enVeille);

	}


	@DeleteMapping("/deleteSelectedFournisseur/{idFournisseursSelected}")
	@ApiOperation(value = "service to delete many Personnel by Id.")
	public Map<String, Boolean> deleteFournisseurSelected(
			@ApiParam(name = "idFournisseursSelected", value = "ids of fournisseur Selected", required = true)
			@PathVariable(value = "idFournisseursSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idFournisseursSelected) {
		fournisseurService.deleteFournisseurSelected(idFournisseursSelected);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PutMapping(value = "/addFournisseur")
	public ResponseEntity<String> createNewFournisseur(
			@RequestParam("refFournisseurIris")
			@NotEmpty
			String refFournisseurIris,
			@RequestParam("raisonSociale")
			@NotEmpty
			String raisonSociale,
			@RequestParam("adresse")
			@NotEmpty
			String adresse,
			@RequestParam("codePostal")
			@NotEmpty
			String codePostal,
			@RequestParam("ville")
			@NotEmpty
			String ville,
			@RequestParam("pays")
			@NotEmpty
			String pays,
			@RequestParam("region")
			@NotEmpty
			String region,
			@RequestParam("phone") String phone,
			@RequestParam("email")
			@NotEmpty
			@Email(message="Email invalide !!", regexp="^[A-Za-z0-9+_.-]+@(.+)$")
			String email,
			@RequestParam("statut") String statut,
			@RequestParam("linkedin") String linkedin,
			@RequestParam("abrege") String abrege,
			@RequestParam("siteWeb") String siteWeb,
			@RequestParam("nomBanque") String nomBanque,
			@RequestParam("adresseBanque") String adresseBanque,
			@RequestParam("codeDouane") String codeDouane,
			@RequestParam("rne") String rne,
			@RequestParam("identifiantTva") String identifiantTva,
			@RequestParam("telecopie") String telecopie,
			@RequestParam("rib") String rib,
			@RequestParam("swift") String swift,
			@RequestParam("images") MultipartFile[] images

	) throws ResourceNotFoundException {
		fournisseurService.createNewFournisseur(refFournisseurIris,raisonSociale,adresse,codePostal,ville,pays,region,phone,email,statut,linkedin,abrege,siteWeb
				,nomBanque,adresseBanque,codeDouane,rne,identifiantTva,telecopie,rib,swift,images);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	}


	@PutMapping("/updateFournisseur/{idFournisseur}")
	public ResponseEntity <String> updateFournisseur(
			@ApiParam(name = "idFournisseur", value="id of fournisseur", required = true)
			@PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}")  String idFournisseur,
			@RequestParam("refFournisseurIris") String refFournisseurIris,
			@RequestParam("raisonSociale") String raisonSociale,
			@RequestParam("adresse") String adresse,
			@RequestParam("codePostal") String codePostal,
			@RequestParam("ville") String ville,
			@RequestParam("pays") String pays,
			@RequestParam("region") String region,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("statut") String statut,
			@RequestParam("linkedin") String linkedin,
			@RequestParam("abrege") String abrege,
			@RequestParam("siteWeb") String siteWeb,
			@RequestParam("nomBanque") String nomBanque,
			@RequestParam("adresseBanque") String adresseBanque,
			@RequestParam("codeDouane") String codeDouane,
			@RequestParam("rne") String rne,
			@RequestParam("identifiantTva") String identifiantTva,
			@RequestParam("telecopie") String telecopie,
			@RequestParam("rib") String rib,
			@RequestParam("swift") String swift,
			@RequestParam("images") MultipartFile[] images) throws ResourceNotFoundException {

		fournisseurService.updateFournisseur(idFournisseur,refFournisseurIris,raisonSociale,adresse,codePostal,ville,pays,region,phone,email,statut,linkedin,abrege,siteWeb
				,nomBanque,adresseBanque,codeDouane,rne,identifiantTva,telecopie,rib,swift,images);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}

	@GetMapping("/onSortActiveFournisseur")
	@ApiOperation(value = "service to get get All active fournisseur   sorted  and ordered by  params")
	public ResponseEntity<Map<String, Object>> onSortActiveFournisseur(@RequestParam(defaultValue = "0") int page,
																	   @RequestParam(defaultValue = "3") int size,
																	   @RequestParam(defaultValue = "field") String field,
																	   @RequestParam(defaultValue = "order") String order){
		return fournisseurService.onSortActiveFournisseur(page,size,field,order);

	}

	@GetMapping("/onSortFournisseurNotActive")
	@ApiOperation(value = "service to get get All fournisseur not active sorted  and ordered by  params")
	public ResponseEntity<Map<String, Object>> onSortFournisseurNotActive(@RequestParam(defaultValue = "0") int page,
																		  @RequestParam(defaultValue = "3") int size,
																		  @RequestParam(defaultValue = "field") String field,
																		  @RequestParam(defaultValue = "order") String order){
		return fournisseurService.onSortFournisseurNotActive(page,size,field,order);

	}


	@PutMapping("/miseEnVeille/{idFournisseur}")
	public ResponseEntity <String> miseEnVeille(
			@ApiParam(name = "idFournisseur", value="id of fournisseur", required = true)
			@PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}")  String idFournisseur,
			@RequestBody(required = true) FournisseurDto fournisseurDto) throws ResourceNotFoundException {

		fournisseurService.miseEnVeille(idFournisseur);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}


	@PutMapping("/addContactFournisseur/{idFournisseur}")
	public ResponseEntity <String> addContactFournisseur(
			@ApiParam(name = "idFournisseur", value="id of fournisseur", required = true)
			@PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}")  String idFournisseur,
			@Valid @RequestBody(required = true) ContactDto contactDto) throws ResourceNotFoundException {
		fournisseurService.addContactFournisseur(contactDto,idFournisseur);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

	}

	@PutMapping("/updateContactFournisseur/{idContact}")
	public ResponseEntity <String> updateContactFournisseur(
			@ApiParam(name = "idContact", value="id of contact", required = true)
			@PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}")  String idContact,
			@Valid @RequestBody(required = true) ContactDto ContactDto ) throws ResourceNotFoundException {
		fournisseurService.updateContactFournisseur(ContactDto,idContact);
		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}

	@DeleteMapping("/deleteFournisseur/{id}")
	@ApiOperation(value = "service to delete one Fournisseur by Id.")
	public Map < String, Boolean > deletefournisseur(
			@ApiParam(name = "id", value="id of fournisseur", required = true)
			@PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String fournisseurId)
			throws ResourceNotFoundException {
		Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId)
				.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), fournisseurId)));

		fournisseurService.deleteFournisseur(fournisseur);
		Map < String, Boolean > response = new HashMap < > ();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/deleteContactFournisseur/{idContact}")
	@ApiOperation(value = "service to delete one Fournisseur by Id.")
	public Map< String, Boolean > deleteContactFournisseur(
			@ApiParam(name = "idContact", value="id of fournisseur", required = true)
			@PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}") String idContact)
			throws ResourceNotFoundException {
		fournisseurService.deleteContactFournisseur(idContact);
		Map < String, Boolean > response = new HashMap< >();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/removePictures/{idFournisseur}")
	@ApiOperation(value = "service to delete all  Picture by idFournisseur and idPicture.")
	public Map< String, Boolean > removePictures(
			@ApiParam(name = "idFournisseur", value="id of fournisseur", required = true)
			@PathVariable(value = "idFournisseur", required = true) @NotEmpty(message = "{http.error.0001}") String idFournisseur)
			throws ResourceNotFoundException {
		fournisseurService.removePictures(idFournisseur);
		Map < String, Boolean > response = new HashMap< >();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/removePicture/{idPicture}")
	@ApiOperation(value = "service to delete one Picture by Id.")
	public Map< String, Boolean > removePicture(
			@ApiParam(name = "idPicture", value="id of picture", required = true)
			@PathVariable(value = "idPicture", required = true) @NotEmpty(message = "{http.error.0001}") String idPicture)
			throws ResourceNotFoundException {
		fournisseurService.removePicture(idPicture);
		Map < String, Boolean > response = new HashMap< >();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@GetMapping("/report/{refFournisseurIris}")
	public ResponseEntity<byte[]> generateReport(@PathVariable String refFournisseurIris){

		return fournisseurService.RecordReport(refFournisseurIris);

	}

	@GetMapping("/getFournisseurByMonth")
	public int getFournisseurByMonth(){
		return fournisseurService.getFournisseurByMonth();
	}

	@GetMapping("/getallFournisseur")
	public int getallFournisseur(){
		return fournisseurService.getallFournisseur();
	}

	@GetMapping("/getFrsActifListe")
	public List<Integer> getFrsActifListe(){
		return fournisseurService.getFrsListe(false);
	}
	@GetMapping("/getFrsNoActifListe")
	public List<Integer> getFrsnoActifListe(){
		return fournisseurService.getFrsListe(true);
	}
	@GetMapping("/getallcommande")
	public int getAllCommande(){
		return fournisseurService.getAllCommande();
	}

}
