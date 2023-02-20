package com.housservice.housstock.repository;

import com.housservice.housstock.model.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PictureRepository extends MongoRepository<Picture, String> {



}
