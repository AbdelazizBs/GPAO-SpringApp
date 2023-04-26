package com.housservice.housstock.controller.produit;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Produit;
import com.housservice.housstock.model.dto.ProduitDto;
import com.housservice.housstock.repository.ProduitRepository;
import com.housservice.housstock.service.ProduitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "service to filter liste matieres ")
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

    @GetMapping("/getAllProduit")
    @ApiOperation(value = "service to get get All matiere")
    public ResponseEntity<Map<String, Object>> getAllPersonnel(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size){
        return produitService.getAllProduit(page,size);

    }
    @GetMapping("/getTypeProduit")
    @ApiOperation(value = "service to get one Type")
    public List<String> getTypeProduit() {
        return produitService.getTypeProduit();
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

}
