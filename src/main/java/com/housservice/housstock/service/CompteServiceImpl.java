package com.housservice.housstock.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.LoginRequest;
import com.housservice.housstock.repository.CompteRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;

@Service
public class CompteServiceImpl implements CompteService{
    private final CompteRepository compteRepository;


    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public String login(LoginRequest loginRequest) throws LoginException {
    System.out.println(loginRequest);
        Compte compte = compteRepository.findByEmail(loginRequest.getEmail());
        if (compte == null) {
            throw new LoginException("Invalid email or password.");
        }
        if (loginRequest.getPassword().equals(compte.getPassword())) {
            String token = generateToken(compte);
            return token;
        } else {
            throw new LoginException("Invalid email or password.");
        }
    }

    private String generateToken(Compte compte) {
        // Define the expiration time for the token (e.g. 1 hour).
        long expirationTime = 3600000; // 1 hour in milliseconds

        // Get the current timestamp.
        long currentTime = System.currentTimeMillis();
        Algorithm algorithm = Algorithm.HMAC256("test".getBytes());
        String token = JWT.create()
                .withSubject(compte.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() +30 *60 * 1000))
                .sign(algorithm);
        return token;
    }
}
