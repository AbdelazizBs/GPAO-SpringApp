package com.housservice.housstock.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "OrdreFabrication")
public class OrdreFabrication {

    @Transient
    public static final String SEQUENCE_NAME = "ordreFabrication_sequence";

    @Id
    private String id;
}
