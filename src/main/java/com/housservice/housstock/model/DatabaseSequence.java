package com.housservice.housstock.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@Document(collection = "database_sequences")
public class DatabaseSequence {

	@Id
	private String id;
	
	private long seq;

    public DatabaseSequence() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
