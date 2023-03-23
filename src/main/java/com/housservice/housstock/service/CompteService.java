package com.housservice.housstock.service;

import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.LoginRequest;

import javax.security.auth.login.LoginException;

public interface CompteService {
    public String login(LoginRequest loginRequest) throws LoginException;
}
