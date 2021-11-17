package com.housservice.housstock.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */

@Getter
@Setter
@Document(collection = "database_sequences")
public class DatabaseSequence {

	@Id
	private String id;
	
	private long seq;

    public DatabaseSequence() {}

}
