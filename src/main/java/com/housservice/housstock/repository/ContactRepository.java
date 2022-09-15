package com.housservice.housstock.repository;

import com.housservice.housstock.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact,String> {
}
