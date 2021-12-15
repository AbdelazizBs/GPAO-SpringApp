package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Roles;

public interface RolesRepository extends MongoRepository<Roles, String>{

}
