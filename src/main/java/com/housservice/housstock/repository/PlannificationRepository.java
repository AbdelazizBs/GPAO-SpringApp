package com.housservice.housstock.repository;

import com.housservice.housstock.model.Article;
import com.housservice.housstock.model.Plannification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlannificationRepository extends MongoRepository<Plannification,String> {
    Page<Plannification> findPlannificationByEtat(boolean b, Pageable paging);
}
