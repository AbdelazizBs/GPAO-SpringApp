package com.housservice.housstock.controller.affectation;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.ListeMatiere;
import com.housservice.housstock.model.Matiere;
import com.housservice.housstock.model.dto.AffectationDto;
import com.housservice.housstock.model.dto.PrixAchatDto;
import com.housservice.housstock.repository.AffectationRepository;
import com.housservice.housstock.service.AffectationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/v1/affectation")
@Api(tags = {"AffectationManagement"})
@Validated
public class AffectationController {
    private final AffectationService affectationService;
    private final AffectationRepository affectationRepository;


    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public AffectationController(AffectationService affectationService, MessageHttpErrorProperties messageHttpErrorProperties,AffectationRepository affectationRepository) {
        this.affectationService = affectationService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.affectationRepository = affectationRepository;

    }



    @GetMapping("/search")
    @ApiOperation(value = "service to filter affectations ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return affectationService.search(textToFind, page, size, enVeille);

    }

    @PutMapping(value = "/addAffectation")
    public ResponseEntity<String> createNewAffectation(
            @Valid @RequestBody AffectationDto affectationDto
    ) throws ResourceNotFoundException {
        affectationService.createNewAffectation(affectationDto);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @GetMapping("/getAllAffectation")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllAffectationEnVielle(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "3") int size,
                                                                         @RequestParam String id){
        return affectationService.getAllAffectation(page,size,id);
    }

    @GetMapping("/getAllAffectationFrs")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllAffectationFrs(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "3") int size,
                                                                         @RequestParam String id){
        return affectationService.getAllAffectationFrs(page,size,id);
    }
    @GetMapping("/getFournisseur")
    @ApiOperation(value = "service to get get All Personnel")
    public List<String> getFournisseur(){
        return affectationService.getFournisseur();
    }
    @GetMapping("/getMatiere/{id}")
    @ApiOperation(value = "service to get get All Personnel")
    public Optional<ListeMatiere> getMatiere(@ApiParam(name = "id", value="id of affectation", required = true)
                                          @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id){
        return affectationService.getMatiere(id);
    }

    @GetMapping("/getFournisseurid/{id}")
    @ApiOperation(value = "service to get get All Personnel")
    public Optional<Fournisseur> getFournisseurid(@ApiParam(name = "id", value="id of affectation", required = true)
                                             @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id){
        return affectationService.getFournisseurid(id);
    }

    @GetMapping("/getFournisseurRef/{id}")
    @ApiOperation(value = "service to get get All Personnel")
    public String getFournisseurRef(@ApiParam(name = "id", value="id of affectation", required = true)
                                                  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id){
        return affectationService.getFournisseurRef(id);
    }
    @PutMapping("/updateAffectation/{id}")
    public ResponseEntity<String> updateAffectation(
            @ApiParam(name = "id", value = "id of affectation", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id,
            @Valid @RequestBody AffectationDto affectationDto
    ) throws ResourceNotFoundException {
        affectationService.updateAffectation(affectationDto,id);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @GetMapping("/onSortActiveAffectation")
    @ApiOperation(value = "service to get get All active affectation   sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> onSortActiveAffectation(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "3") int size,
                                                                  @RequestParam(defaultValue = "field") String field,
                                                                  @RequestParam(defaultValue = "order") String order){
        return affectationService.onSortActiveAffectation(page,size,field,order);

    }


    @DeleteMapping("/deleteAffectation/{id}")
    @ApiOperation(value = "service to delete one Affectation by Id.")
    public Map < String, Boolean > deleteaffectation(
            @ApiParam(name = "id", value="id of affectation", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        Affectation affectation = affectationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), id)));
        affectationService.deleteAffectation(affectation);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    @GetMapping("/getDevise")
    @ApiOperation(value = "service to get one devise")
    public List<String> getDevise() {
        return affectationService.getDevise();
    }
    @GetMapping("/getUniteAchat")
    @ApiOperation(value = "service to get one unite Achat")
    public List<String> getUniteAchat() {
        return affectationService.getUniteAchat();
    }

    @PutMapping("/addPrixAchatAffectation/{idAffectation}")
    public ResponseEntity <String> addPrixAchatAffectation(
            @ApiParam(name = "idAffectation", value="id of affectation", required = true)
            @PathVariable(value = "idAffectation", required = true) @NotEmpty(message = "{http.error.0001}")  String idAffectation,
            @Valid @RequestBody(required = true) PrixAchatDto prixAchatDto) throws ResourceNotFoundException {
        affectationService.addPrixAchatAffectation(prixAchatDto,idAffectation);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }

    @PutMapping("/updatePrixAchatAffectation/{idAffectation}")
    public ResponseEntity <String> updatePrixAchatAffectation(
            @ApiParam(name = "idAffectation", value="id of affectation", required = true)
            @PathVariable(value = "idAffectation", required = true) @NotEmpty(message = "{http.error.0001}")  String idAffectation,
            @Valid @RequestBody(required = true) PrixAchatDto prixAchatDto ) throws ResourceNotFoundException {
        affectationService.updatePrixAchatAffectation(prixAchatDto,idAffectation);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @DeleteMapping("/deletePrixAchatAffectation/{idAffectation}")
    @ApiOperation(value = "service to delete one Client by Id.")
    public Map< String, Boolean > deleteContactClient(
            @ApiParam(name = "idAffectation", value="id of Affectation", required = true)
            @PathVariable(value = "idAffectation", required = true) @NotEmpty(message = "{http.error.0001}") String idAffectation)
            throws ResourceNotFoundException {
        affectationService.deletePrixAchatAffectation(idAffectation);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
