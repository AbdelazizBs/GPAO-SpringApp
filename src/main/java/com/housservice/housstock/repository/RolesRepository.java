package com.housservice.housstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.housservice.housstock.model.Roles;

import java.util.Optional;

public interface RolesRepository extends MongoRepository<Roles, String>{
   Optional<Roles>  findByNom(String s);

}
