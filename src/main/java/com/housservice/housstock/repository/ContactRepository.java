package com.housservice.housstock.repository;

import com.housservice.housstock.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactRepository extends MongoRepository<Contact,String> {

}
