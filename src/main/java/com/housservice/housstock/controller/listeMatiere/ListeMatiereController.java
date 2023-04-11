package com.housservice.housstock.controller.listeMatiere;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.ListeMatiere;
import com.housservice.housstock.model.dto.ListeMatiereDto;
import com.housservice.housstock.service.ListeMatiereService;
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

@CrossOrigin
@RestController
@RequestMapping("/api/v1/listeMatiere")
@Api(tags = {"Liste Matieres Management"})
@Validated
public class ListeMatiereController {
    private final ListeMatiereService listeMatiereService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public ListeMatiereController(ListeMatiereService listeMatiereService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.listeMatiereService = listeMatiereService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }
    @GetMapping("/search")
    @ApiOperation(value = "service to filter liste matieres ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return listeMatiereService.search(textToFind, page, size);

    }
    @PutMapping("/addListeMatiere")
    @ApiOperation(value = "service to add new ListeMatiere")
    public ResponseEntity<String> addListeMatiere(@Valid @RequestBody ListeMatiereDto listeMatiereDto)   {
        listeMatiereService.addListeMatiere(listeMatiereDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @PutMapping("/updateListeMatiere/{idListeMatiere}")
    @ApiOperation(value = "service to update  ListeMatiere")
    public ResponseEntity<String> updateListeMatiere(@Valid  @RequestBody ListeMatiereDto listeMatiereDto,
                                                  @PathVariable(value = "idListeMatiere", required = true) String idListeMatiere) throws ResourceNotFoundException {
        listeMatiereService.updateListeMatiere(listeMatiereDto,idListeMatiere);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }
    @GetMapping("/onSortActiveListeMatiere")
    @ApiOperation(value = "service to get get All active ListeMatiere ordered and sorted by params")
    public ResponseEntity<Map<String, Object>> onSortActiveListeMatiere(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "3") int size,
                                                                     @RequestParam(defaultValue = "field") String field,
                                                                     @RequestParam(defaultValue = "order") String order){
        return listeMatiereService.onSortListeMatiere(page,size,field,order);

    }
    @DeleteMapping("/deleteListeMatiere/{id}")
    @ApiOperation(value = "service to delete one ListeMatiere by Id.")
    public Map<String, Boolean> deleteListeMatiere(
            @ApiParam(name = "id", value = "id of listeMatiere", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId) {

        listeMatiereService.deleteListeMatiere(utilisateurId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @GetMapping("/getAllMatiere")
    @ApiOperation(value = "service to get one type matiere")
    public List<String> getAllMatiere() {
        return listeMatiereService.getAllMatiere();
    }
    @GetMapping("/getType")
    @ApiOperation(value = "service to get one Type")
    public List<String> getType() {
        return listeMatiereService.getTypePapier();
    }
    @GetMapping("/getUniteConsommation")
    @ApiOperation(value = "service to get one unite consommation")
    public List<String> getUniteConsommation() {
        return listeMatiereService.getUniteConsommation();
    }
    @GetMapping("/getListeMatiere/{designation}")
    public List<ListeMatiere> getAllMatiereByDesignation(@PathVariable(value = "designation", required = true)String designation){
        return listeMatiereService.getAllMatiereByDesignation(designation);
    }




}
