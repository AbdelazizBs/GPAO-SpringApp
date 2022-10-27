package com.housservice.housstock.service;
import com.housservice.housstock.exception.ResourceNotFoundException;
public interface ContactService {
    public void deleteContactClient(String idContact) throws ResourceNotFoundException;
}
