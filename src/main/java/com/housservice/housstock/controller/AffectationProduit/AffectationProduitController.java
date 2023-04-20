package com.housservice.housstock.controller.AffectationProduit;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.AffectationProduit;

import com.housservice.housstock.model.dto.AffectationProduitDto;
import com.housservice.housstock.model.dto.PrixVenteDto;
import com.housservice.housstock.service.AffectationProduitService;
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
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/AffectationProduit")
@Api(tags = { "affectationProduit produits Management" })
public class AffectationProduitController {
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final AffectationProduitService affectationProduitService;

    public AffectationProduitController(MessageHttpErrorProperties messageHttpErrorProperties, AffectationProduitService affectationProduitProduitServiceService) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.affectationProduitService = affectationProduitProduitServiceService;
    }
    @GetMapping("/search")
    @ApiOperation(value = "service to filter liste matieres ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,

                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return affectationProduitService.search(textToFind, page, size);

    }
    @GetMapping("/getDevise")
    @ApiOperation(value = "service to get one devise")
    public List<String> getDevise() {
        return affectationProduitService.getDevise();
    }
    @GetMapping("/getUniteVente")
    @ApiOperation(value = "service to get one unite Vente")
    public List<String> getUniteVente() {
        return affectationProduitService.getUniteAchat();
    }
    @GetMapping("/getAffectation/{idProduit}")
    public Optional<AffectationProduit> getAffectationByIdProduit(@PathVariable(value = "designation", required = true)String idProduit){
        return affectationProduitService.getAffectationProduitByIdProduit(idProduit);
    }
    @GetMapping("/onSortAffectation")
    @ApiOperation(value = "service to get get All affectationProduit ordered and sorted by params")
    public ResponseEntity<Map<String, Object>> onSortActiveListeProduit(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "3") int size,
                                                                        @RequestParam(defaultValue = "field") String field,
                                                                        @RequestParam(defaultValue = "order") String order){
        return affectationProduitService.onSortAffectationProduit(page,size,field,order);

    }
    @PutMapping("/addPrixVenteAffectation/{idAffectation}")
    public ResponseEntity <String> addPrixVenteAffectation(
            @ApiParam(name = "idAffectation", value="id of affectationProduit", required = true)
            @PathVariable(value = "idAffectation", required = true) @NotEmpty(message = "{http.error.0001}")  String idAffectation,
            @Valid @RequestBody(required = true) PrixVenteDto prixVenteDto) throws ResourceNotFoundException {
        affectationProduitService.addPrixVenteAffectationProduit(prixVenteDto,idAffectation);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }

    @PutMapping("/updatePrixVenteAffectation/{idAffectation}")
    public ResponseEntity <String> updatePrixVenteAffectation(
            @ApiParam(name = "idAffectation", value="id of affectationProduit", required = true)
            @PathVariable(value = "idAffectation", required = true) @NotEmpty(message = "{http.error.0001}")  String idAffectation,
            @Valid @RequestBody(required = true) PrixVenteDto prixVenteDto ) throws ResourceNotFoundException {
        affectationProduitService.updatePrixVenteAffectationProduit(prixVenteDto,idAffectation);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @DeleteMapping("/deletePrixVenteAffectation/{idAffectation}")
    @ApiOperation(value = "service to delete one Client by Id.")
    public Map< String, Boolean > deleteContactClient(
            @ApiParam(name = "idAffectation", value="id of Affectation", required = true)
            @PathVariable(value = "idAffectation", required = true) @NotEmpty(message = "{http.error.0001}") String idAffectation)
            throws ResourceNotFoundException {
        affectationProduitService.deletePrixVenteAffectationProduit(idAffectation);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @PutMapping("/addAffectation")
    @ApiOperation(value = "service to add new Affectation")
    public ResponseEntity<String> addAffectation(@Valid  @RequestBody AffectationProduitDto affectationProduitDto)   {
        affectationProduitService.addAffectationProduit(affectationProduitDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @PutMapping("/updateAffectation/{idAffectation}")
    @ApiOperation(value = "service to update  Affectation")
    public ResponseEntity<String> update(@Valid  @RequestBody AffectationProduitDto affectationProduitDto,
                                                  @PathVariable(value = "idAffectation", required = true) String idAffectation) throws ResourceNotFoundException {
        affectationProduitService.updateAffectationProduit(affectationProduitDto,idAffectation);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }
}
