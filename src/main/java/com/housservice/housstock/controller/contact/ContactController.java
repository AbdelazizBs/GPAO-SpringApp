package com.housservice.housstock.controller.contact;


import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Contact;
import com.housservice.housstock.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
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


}
