package com.housservice.housstock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;


@Getter
@Setter
@Document(collection = "Picture")
public class Picture {
	
    @Id
    private String id;

    private String fileName;

    @Size(max = 1000)
    private byte[] bytes;

    private String type;

    public Picture() {

    }

    public Picture(String originalFilename, byte[] bytes, String contentType) {
        this.fileName=originalFilename;
        this.bytes=bytes;
        this.type=contentType;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return id.equals(picture.id);
    }
}
