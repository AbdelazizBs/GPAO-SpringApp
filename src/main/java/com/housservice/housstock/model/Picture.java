package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;


@Getter
@Setter
@Document(collection = "Picture")
public class Picture {
    @Id
    private String id;

    @Column(name = "fileName")
    private String fileName;


   @Lob
   @Column(name = "bytes", length = 1000)
   private byte[] bytes;

    @Column(name = "type")
    private String type;


    public Picture() {

    }


    public Picture(String originalFilename, byte[] bytes, String contentType) {
        this.fileName=originalFilename;
        this.bytes=bytes;
        this.type=contentType;
    }
}
