package com.housservice.housstock.controller.affectationProduit;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;

import com.housservice.housstock.model.dto.AffectationProduitDto;
import com.housservice.housstock.model.dto.PrixVenteDto;
import com.housservice.housstock.repository.AffectationProduitRepository;
import com.housservice.housstock.service.AffectationProduitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/affectationProduit")
@Api(tags = { "affectationProduit produits Management" })
public class AffectationProduitController {
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final AffectationProduitService affectationProduitService;
    private final AffectationProduitRepository affectationProduitRepository;


    public AffectationProduitController(MessageHttpErrorProperties messageHttpErrorProperties,AffectationProduitRepository affectationProduitRepository, AffectationProduitService affectationProduitProduitServiceService) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.affectationProduitService = affectationProduitProduitServiceService;
        this.affectationProduitRepository = affectationProduitRepository;

    }
    @GetMapping("/search")
    @ApiOperation(value = "service to filter liste matieres ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,

                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return affectationProduitService.search(textToFind, page, size);

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
    @GetMapping("/getProduit/{id}")
    @ApiOperation(value = "service to get get All Personnel")
    public Optional<Produit> getProduit(@ApiParam(name = "id", value="id of affectation", required = true)
                                             @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id){
        return affectationProduitService.getProduit(id);
    }
    @PutMapping("/addAffectation")
    @ApiOperation(value = "service to add new Affectation")
    public ResponseEntity<String> addAffectation(@Valid  @RequestBody AffectationProduitDto affectationProduitDto)   {
        System.out.println(affectationProduitDto.getDestination());
        affectationProduitService.addAffectationProduit(affectationProduitDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }

    @GetMapping("/getAllAffectation")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllAffectationEnVielle(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "3") int size,
                                                                         @RequestParam String id){
        return affectationProduitService.getAllAffectation(page,size,id);
    }
    @PutMapping("/updateAffectation/{idAffectation}")
    @ApiOperation(value = "service to update  Affectation")
    public ResponseEntity<String> update(@Valid  @RequestBody AffectationProduitDto affectationProduitDto,
                                                  @PathVariable(value = "idAffectation", required = true) String idAffectation) throws ResourceNotFoundException {
        affectationProduitService.updateAffectationProduit(affectationProduitDto,idAffectation);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }



    @GetMapping("/getClientRef/{id}")
    @ApiOperation(value = "service to get get All Client")
    public String getClientRef(@ApiParam(name = "id", value="id of affectation", required = true)
                                    @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id){
        return affectationProduitService.getClientRef(id);
    }

    @DeleteMapping("/deleteAffectation/{id}")
    @ApiOperation(value = "service to delete one Affectation by Id.")
    public Map < String, Boolean > deleteaffectation(
            @ApiParam(name = "id", value="id of affectation", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        AffectationProduit affectationProduit = affectationProduitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        affectationProduitService.deleteAffectation(affectationProduit);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }



    @GetMapping("/getClient")
    @ApiOperation(value = "service to get get All Personnel")
    public List<String> getClient(){
        return affectationProduitService.getClient();
    }


    @GetMapping("/getClientid/{id}")
    @ApiOperation(value = "service to get get All Personnel")
    public Optional<Client> getClientid(@ApiParam(name = "id", value="id of affectation", required = true)
                                                  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id){
        return affectationProduitService.getClientid(id);
    }

    @GetMapping("/getAllAffectationClt")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllAffectationClt(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "3") int size,
                                                                    @RequestParam String id){
        return affectationProduitService.getAllAffectationClt(page,size,id);
    }

}
