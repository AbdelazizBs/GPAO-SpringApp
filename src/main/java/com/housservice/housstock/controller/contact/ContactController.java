package com.housservice.housstock.controller.contact;


import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/contactClient")
@Api(tags = {"Contact Clients Management"})
public class ContactController {

    final
    ContactService contactService ;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @DeleteMapping("/deleteContactClient/{idContact}")
    @ApiOperation(value = "service to delete one Client by Id.")
    public Map< String, Boolean > deleteContactClient(
            @ApiParam(name = "idContact", value="id of client", required = true)
            @PathVariable(value = "idContact", required = true) @NotEmpty(message = "{http.error.0001}") String idContact)
            throws ResourceNotFoundException {
        contactService.deleteContactClient(idContact);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
