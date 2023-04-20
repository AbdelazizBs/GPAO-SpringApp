package com.housservice.housstock.controller.affectation;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Affectation;
import com.housservice.housstock.model.ListeMatiere;
import com.housservice.housstock.model.dto.AffectationDto;
import com.housservice.housstock.model.dto.ContactDto;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.model.dto.PrixAchatDto;
import com.housservice.housstock.service.AffectationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/affectation")
@Api(tags = {"Liste Matieres d'achat Management"})
@Validated
public class AffectationController {
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final AffectationService affectationService;

    public AffectationController(MessageHttpErrorProperties messageHttpErrorProperties, AffectationService affectationService) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.affectationService = affectationService;
    }
    @GetMapping("/search")
    @ApiOperation(value = "service to filter liste matieres ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,

                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return affectationService.search(textToFind, page, size);

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
    @GetMapping("/getAffectation/{idMatiere}")
    public Optional<Affectation> getAffectationByIdMatiere(@PathVariable(value = "designation", required = true)String idMateire){
        return affectationService.getAffectationByIdMatiere(idMateire);
    }
    @GetMapping("/onSortAffectation")
    @ApiOperation(value = "service to get get All affectation ordered and sorted by params")
    public ResponseEntity<Map<String, Object>> onSortActiveListeMatiere(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "3") int size,
                                                                        @RequestParam(defaultValue = "field") String field,
                                                                        @RequestParam(defaultValue = "order") String order){
        return affectationService.onSortAffectation(page,size,field,order);

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
    @PutMapping("/addAffectation")
    @ApiOperation(value = "service to add new Affectation")
    public ResponseEntity<String> addAffectation(@Valid  @RequestBody AffectationDto affectationDto)   {
        affectationService.addAffectation(affectationDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @PutMapping("/updateAffectation/{idAffectation}")
    @ApiOperation(value = "service to update  Affectation")
    public ResponseEntity<String> updateAffectation(@Valid  @RequestBody AffectationDto affectationDto,
                                                  @PathVariable(value = "idPersonnel", required = true) String idAffectation) throws ResourceNotFoundException {
        affectationService.updateAffectation(affectationDto,idAffectation);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }
}
