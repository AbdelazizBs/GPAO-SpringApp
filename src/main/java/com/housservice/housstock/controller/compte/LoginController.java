package com.housservice.housstock.controller.compte;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.*;
import com.housservice.housstock.model.dto.CompteDto;
import com.housservice.housstock.repository.CompteRepository;
import com.housservice.housstock.service.CompteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/compte")
@Api(tags = {"Compte Management"})
public class LoginController {
    private final CompteService loginService;
    private final CompteRepository compteRepository;

    private final MessageHttpErrorProperties messageHttpErrorProperties;


    public LoginController(CompteRepository compteRepository,CompteService loginService,MessageHttpErrorProperties messageHttpErrorProperties) {
        this.loginService = loginService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.compteRepository = compteRepository;

    }

    @SneakyThrows
    @PutMapping("/login")
    public ResponseEntity<Authrespone> login(@Valid @RequestBody LoginRequest loginRequest) {
            String token = loginService.login(loginRequest);
            return ResponseEntity.ok(new Authrespone(token));

    }

    @PutMapping("/add")
    public ResponseEntity <String> login(@Valid @RequestBody CompteDto compteDto) throws ResourceNotFoundException {
        loginService.add(compteDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }


    @DeleteMapping("/deleteCompte/{id}")
    @ApiOperation(value = "service to delete one Compte by Id.")
    public Map < String, Boolean > deletecompte(
            @ApiParam(name = "id", value="id of compte", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        Compte compte = compteRepository.findById(id).get();
        compteRepository.delete(compte);

        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/onSortActiveCompte")
    @ApiOperation(value = "service to get get All active Compte sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> onSortActiveCompte(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "3") int size,
                                                                       @RequestParam(defaultValue = "field") String field,
                                                                       @RequestParam(defaultValue = "order") String order){
        System.out.println(order);
        return loginService.onSortActiveCompte(page,size,field,order);
    }

    @GetMapping("/search")
    @ApiOperation(value = "service to filter comptes ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return loginService.search(textToFind, page, size);

    }

    @PutMapping("/updateCompte/{idCompte}")
    @ApiOperation(value = "service to update  Personnel")
    public ResponseEntity<String> updatePersonnel(@Valid  @RequestBody CompteDto compteDto,
                                                  @PathVariable(value = "idCompte", required = true) String idCompte
                                                  ) throws ResourceNotFoundException {
        loginService.updateCompte(compteDto,idCompte);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }

    @GetMapping("/getAllCompte")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllCompte(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size){
        return loginService.getAllCompte(page,size);
    }

    @GetMapping("/getAllRole")
    public List<String> getAllRole(){
        return loginService.getAllRole();
    }
    @GetMapping("/getAllPer")
    public List<String> getAllPer(){
        return loginService.getAllPer();
    }


    @PutMapping("/miseEnVeille/{id}")
    public ResponseEntity <String> miseEnVeille(
            @ApiParam(name = "id", value = "id", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id) throws ResourceNotFoundException {
        loginService.miseEnVeille(id);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @GetMapping("/getAllCompteEnVeille")
    public ResponseEntity<Map<String, Object>> getAllCompteNonActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return loginService.getAllCompteVeille(page,size);
    }


    @GetMapping("/getCompteid/{id}")
    public Optional<Compte> getCompteid(@ApiParam(name = "id", value = "id", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id) {
        return loginService.getCompteById(id);
    }
    @GetMapping("/getPersonnelid/{username}")
    public Optional<Personnel> getPersonnelid(@ApiParam(name = "username", value = "username", required = true) @PathVariable(value = "username", required = true) @NotEmpty(message = "{http.error.0001}") String username) {
        return loginService.getPersonnelById(username);
    }
    @PutMapping("/restaurer/{id}")
    public ResponseEntity <String> restaurer(
            @ApiParam(name = "id", value = "id", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        loginService.Restaurer(id);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
}
