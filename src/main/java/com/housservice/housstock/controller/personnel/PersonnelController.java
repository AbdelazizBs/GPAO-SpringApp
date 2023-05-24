package com.housservice.housstock.controller.personnel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.Roles;
import com.housservice.housstock.model.dto.PersonnelDto;
import com.housservice.housstock.service.PersonnelService;
import com.housservice.housstock.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/personnel")
@Api(tags = {"personnels Management"})
public class PersonnelController {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    private final PersonnelService personnelService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;
    final
    PictureService pictureService;

    public PersonnelController(PersonnelService personnelService,
                               MessageHttpErrorProperties messageHttpErrorProperties, PictureService pictureService) {
        this.personnelService = personnelService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.pictureService = pictureService;
    }

    @PutMapping("/addPersonnel")
    @ApiOperation(value = "service to add new Personnel")
    public ResponseEntity<String> addPersonnel(@Valid @RequestBody PersonnelDto personnelDto) {
        personnelService.addPersonnel(personnelDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }

    @PutMapping("/updatePersonnel/{idPersonnel}")
    @ApiOperation(value = "service to update  Personnel")
    public ResponseEntity<String> updatePersonnel(@Valid @RequestBody PersonnelDto personnelDto,
                                                  @PathVariable(value = "idPersonnel", required = true) String idPersonnel) throws ResourceNotFoundException {
        if (!validate(personnelDto.getEmail())) {
            throw new IllegalArgumentException(" email " + personnelDto.getEmail() + "  n'est pas valide !!");
        }
        personnelService.updatePersonnel(personnelDto, idPersonnel);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }

    @GetMapping("/getPersonnelById/{id}")
    @ApiOperation(value = "service to get one Utilisateur by Id.")
    public ResponseEntity<PersonnelDto> getPersonnelById(
            @ApiParam(name = "id", value = "id of utilisateur", required = true)
            @PathVariable(value = "id", required = true)
            @NotEmpty(message = "{http.error.0001}") String utilisateurId)
            throws ResourceNotFoundException {
        PersonnelDto utilisateur = personnelService.getPersonnelById(utilisateurId);
        if (utilisateur == null) {
            ResponseEntity.badRequest();
        }

        return ResponseEntity.ok().body(utilisateur);
    }

    @GetMapping("/getConcducteurs")
    @ApiOperation(value = "service to get Conducteurs name list")
    public ResponseEntity<Map<String, Object>> getConcducteurs() {
        return personnelService.getConcducteurs();
    }
    @GetMapping("/getOperatricesName")
    @ApiOperation(value = "service to get Operatrice name list.")
    public ResponseEntity<Map<String, Object>> getOperatricesName() {
        return personnelService.getOperatricesName();
    }

    @GetMapping("/getPersonnelByEmailCompte")
    @ApiOperation(value = "service to get personnel by email account.")
    public ResponseEntity<Map<String, Object>> getPersonnelByEmailCompte(@RequestParam(value = "email", required = true) String email) {
        return personnelService.getPersonnelByEmailCompte(email);
    }
   @GetMapping("/getPersonnelsNameByNameEtape")
    @ApiOperation(value = "service to get Operatrice name list.")
    public ResponseEntity<Map<String, Object>> getPersonnelsNameByMachine(@RequestParam(value = "nomEtape", required = true) String nomEtape) {
        return personnelService.getPersonnelsNameByMachine(nomEtape);
    }

    @GetMapping("/getPersonnelsId")
    @ApiOperation(value = "service to get Operatrice name list.")
    public ResponseEntity<Map<String, Object>> getPersonnelsId(@RequestParam(value = "personnelsName", required = true) List<String> personnelsName) {
        return personnelService.getPersonnelsId(personnelsName);
    }
    @GetMapping("token/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Personnel user = personnelService.getPersonnelByNom(username);
                String access_token = JWT.create().withSubject(user.getNom())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",
                                user.getCompte().getRoles().stream().map(Roles::getNom).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refreshToken);
                tokens.put("roles", user.getCompte().getRoles().stream().map(Roles::getNom).collect(Collectors.toList()).toString());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }


    @PutMapping("/mettreEnVeille/{idPersonnel}")
    public ResponseEntity<String> mettreEnVeille(
            @ApiParam(name = "idPersonnel", value = "id of personnel", required = true) @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}") String idPersonnel)
            throws ResourceNotFoundException {
        personnelService.mettreEnVeille(idPersonnel);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }


    @GetMapping("/onSortActivePersonnel")
    @ApiOperation(value = "service to get get All active Personnel ordered and sorted by params")
    public ResponseEntity<Map<String, Object>> onSortActivePersonnel(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "3") int size,
                                                                     @RequestParam(defaultValue = "field") String field,
                                                                     @RequestParam(defaultValue = "order") String order) {
        return personnelService.onSortActivePersonnel(page, size, field, order);

    }

    @GetMapping("/onSortPersonnelNotActive")
    @ApiOperation(value = "service to get get All  Personnel not active ordered and sorted by params")
    public ResponseEntity<Map<String, Object>> onSortPersonnelNotActive(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "3") int size,
                                                                        @RequestParam(defaultValue = "field") String field,
                                                                        @RequestParam(defaultValue = "order") String order) {
        return personnelService.onSortPersonnelNotActive(page, size, field, order);

    }

    @GetMapping("/getAllPersonnel")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllPersonnel(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size) {
        return personnelService.getAllPersonnel(page, size);

    }
    @GetMapping("/getAllPersonnelName")
    @ApiOperation(value = "service to get get All name of Personnels")
    public ResponseEntity<Map<String, Object>> getAllPersonnelName() {
        return personnelService.getAllPersonnelName();

    }

    @GetMapping("/getAllPersonnelEnVeille")
    @ApiOperation(value = "service to get get All Personnel En Veille ")
    public ResponseEntity<Map<String, Object>> getAllPersonnelEnVeille(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "3") int size) {
        return personnelService.getAllPersonnelEnVeille(page, size);

    }

    @GetMapping("/search")
    @ApiOperation(value = "service to filter personnel ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return personnelService.search(textToFind, page, size, enVeille);

    }

    @DeleteMapping("/deletePersonnel/{id}")
    @ApiOperation(value = "service to delete one Personnel by Id.")
    public Map<String, Boolean> deletePersonnel(
            @ApiParam(name = "id", value = "id of personnel", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String utilisateurId) {

        personnelService.deletePersonnel(utilisateurId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/deleteSelectedPersonnel/{idPersonnelsSelected}")
    @ApiOperation(value = "service to delete many Personnel by Id.")
    public Map<String, Boolean> deletePersonnelSelected(
            @ApiParam(name = "idPersonnelsSelected", value = "ids of personnel Selected", required = true) @PathVariable(value = "idPersonnelsSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idPersonnelsSelected) {
        personnelService.deletePersonnelSelected(idPersonnelsSelected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    @DeleteMapping("/removePic/{idPicture}")
    @ApiOperation(value = "service to delete one Picture by Id.")
    public Map<String, Boolean> deletePicture(
            @ApiParam(name = "id", value = "id of picture", required = true) @PathVariable(value = "idPicture", required = true) @NotEmpty(message = "{http.error.0001}") String idPicture) {
        pictureService.deleteImg(idPicture);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
