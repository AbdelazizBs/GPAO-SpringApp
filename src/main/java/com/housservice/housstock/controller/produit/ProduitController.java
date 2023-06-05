package com.housservice.housstock.controller.produit;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Produit;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.model.dto.ProduitDto;
import com.housservice.housstock.repository.ProduitRepository;
import com.housservice.housstock.service.ProduitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/produit")
@Api(tags = { "produits Management" })
public class ProduitController {
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final ProduitService produitService;

    public ProduitController(MessageHttpErrorProperties messageHttpErrorProperties, ProduitService produitService) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.produitService = produitService;
        
    }
    @GetMapping("/search")
    @ApiOperation(value = "service to filter Article ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return produitService.search(textToFind, page, size);

    }
    @PutMapping("/addProduit")
    @ApiOperation(value = "service to add new Produit")
    public ResponseEntity<String> addProduit(@Valid @RequestBody ProduitDto produitDto)   {
        produitService.addProduit(produitDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }

    @PutMapping("/addEtape/{id}")
    @ApiOperation(value = "service to add new Etape")
    public ResponseEntity<String> addEtape(@Valid @RequestBody String[] Etapes, @PathVariable(value = "id", required = true) String id)   {
        produitService.addEtape(Etapes,id);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @PutMapping("/updateProduit/{idProduit}")
    @ApiOperation(value = "service to update  Produit")
    public ResponseEntity<String> updateProduit(@Valid  @RequestBody ProduitDto produitDto,
                                                     @PathVariable(value = "idProduit", required = true) String idProduit) throws ResourceNotFoundException {
        produitService.updateProduit(produitDto,idProduit);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }
    @GetMapping("/onSortActiveProduit")
    @ApiOperation(value = "service to get get All active Produit ordered and sorted by params")
    public ResponseEntity<Map<String, Object>> onSortActiveProduit(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "3") int size,
                                                                        @RequestParam(defaultValue = "field") String field,
                                                                        @RequestParam(defaultValue = "order") String order){
        return produitService.onSortProduit(page,size,field,order);

    }
    @DeleteMapping("/deleteProduit/{id}")
    @ApiOperation(value = "service to delete one Produit by Id.")
    public Map<String, Boolean> deleteProduit(
            @ApiParam(name = "id", value = "id of produit", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId) {
        produitService.deleteProduit(utilisateurId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @PutMapping("/restaurer/{id}")
    public ResponseEntity <String> restaurer(
            @ApiParam(name = "id", value = "id", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        produitService.Restaurer(id);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @GetMapping("/getAllProduit")
    @ApiOperation(value = "service to get get All matiere")
    public ResponseEntity<Map<String, Object>> getAllPersonnel(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size){
        return produitService.getAllProduit(page,size);

    }
    @GetMapping("/getAllProduitEnVeille")
    @ApiOperation(value = "service to get get All matiere")
    public ResponseEntity<Map<String, Object>> getAllProduitEnVeille(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size){
        return produitService.getAllProduitvielle(page,size);

    }
    @GetMapping("/getTypeProduit")
    @ApiOperation(value = "service to get one Type")
    public List<String> getTypeProduit() {
        return produitService.getTypeProduit();
    }

    @GetMapping("/getEtape")
    @ApiOperation(value = "service to get one Type")
    public List<String> getEtape() {
        return produitService.getEtape();
    }

    @GetMapping("/getUniteVente")
    @ApiOperation(value = "service to get one unite Vente")
    public List<String> getUniteVente() {
        return produitService.getUniteVente();
    }
    @GetMapping("/getProduit/{designation}")
    public List<Produit> getAllProduitByDesignation(@PathVariable(value = "designation", required = true)String designation){
        return produitService.getAllProduitByDesignation(designation);
    }

        @GetMapping("/getEtapes/{id}")
    public String[] getEtapes(@PathVariable(value = "id", required = true)String id){
        return produitService.getEtapes(id);
    }

    @DeleteMapping("/removePictures/{id}")
    @ApiOperation(value = "service to delete all  Picture by idClient and idPicture.")
    public Map< String, Boolean > removePictures(
            @ApiParam(name = "id", value="id of Commande", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        produitService.removePictures(id);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/removePicture/{idPic}")
    @ApiOperation(value = "service to delete one Picture by Id.")
    public Map< String, Boolean > removePicture(
            @ApiParam(name = "idPic", value="id of picture", required = true)
            @PathVariable(value = "idPic", required = true) @NotEmpty(message = "{http.error.0001}") String idPic)
            throws ResourceNotFoundException {
        System.out.println(idPic);
        produitService.removePicture(idPic);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @PutMapping("/addImage/{ref}")
    public ResponseEntity<String> addphoto(
            @ApiParam(name = "ref", value="ref", required = true)
            @PathVariable(value = "ref", required = true) @NotEmpty(message = "{http.error.0001}") String ref,
            @RequestParam("images") MultipartFile[] images)
    {
        produitService.addphoto(images,ref);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

    }
    @PutMapping("/miseEnVeille/{idArticle}")
    public ResponseEntity <String> miseEnVeille(
            @ApiParam(name = "idArticle", value="id of article", required = true)
            @PathVariable(value = "idArticle", required = true) @NotEmpty(message = "{http.error.0001}")  String idArticle,
            @RequestBody(required = true) MachineDto machineDto) throws ResourceNotFoundException {

        produitService.miseEnVeille(idArticle);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

        @GetMapping("/getallArticle")
    public int getallClient(){
        return produitService.getallArticle();
    }
    @GetMapping("/getallProduitlist")
    public List<Produit> getallProduitlist(){
        return produitService.getallProduitlist();
    }

    @GetMapping("/getArticleBox")
    public int getArticleBox(){
        return produitService.getArticleBytype("box");
    }
    @GetMapping("/getArticlehangtag")
    public int getArticlehangtag(){
        return produitService.getArticleBytype("hangtag");
    }
    @GetMapping("/getArticleticket")
    public int getArticleticket(){
        return produitService.getArticleBytype("ticket");
    }
}
