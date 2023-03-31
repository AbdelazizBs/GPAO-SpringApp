package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter 
@Document(collection="Roles")
public class Roles {
	@Id
	private String id;

	private String role;


	public Roles(String role) {
		this.role = role;
	}

	public Roles() {
	}
}
