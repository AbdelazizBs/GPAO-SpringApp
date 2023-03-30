package com.housservice.housstock.repository;

import com.housservice.housstock.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RolesRepository extends MongoRepository<Roles, String> {
    Roles findByrole(String admin);
}
