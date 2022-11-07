package com.housservice.housstock.service.api;

import java.io.IOException;
import java.util.List;

import com.housservice.housstock.model.dto.DocumentDto;

public interface DocumentService {
	

    public List<String> upLoadFile(DocumentDto documentDto) throws IOException;

}

