package com.housservice.housstock.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.housservice.housstock.model.DatabaseSequence;

@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

	 private MongoOperations mongoOperations;

	    @Autowired
	    public SequenceGeneratorServiceImpl(MongoOperations mongoOperations) {
	        this.mongoOperations = mongoOperations;
	    }

	    @Override
		public long generateSequence(String seqName) {

	        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
	                new Update().inc("seq",1), options().returnNew(true).upsert(true),
	                DatabaseSequence.class);
	        return !Objects.isNull(counter) ? counter.getSeq() : 1;

	    }
}
