package com.housservice.housstock.controller.contact;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/contactClient")
@Api(tags = {"Contact Clients Management"})
public class ContactController {
}
