package com.housservice.housstock.service;

import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.LoginRequest;
import com.housservice.housstock.repository.CompteRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Date;
import io.jsonwebtoken.Jwts;

@Service
public class CompteServiceImpl implements CompteService{
    private final CompteRepository compteRepository;

    private final PasswordEncoder passwordEncoder;

    public CompteServiceImpl(CompteRepository compteRepository, PasswordEncoder passwordEncoder) {
        this.compteRepository = compteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginRequest loginRequest) throws LoginException {
        Compte compte = compteRepository.findById(loginRequest.getEmail()).orElse(null);

        if (compte == null) {
            throw new LoginException("Invalid email or password.");
        }

        if (passwordEncoder.matches(loginRequest.getPassword(), compte.getPassword())) {
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

        // Build the JWT token using the JWT library.
        String token = Jwts.builder()
                .setSubject(compte.getEmail()) // Set the subject of the token to the user's email.
                .setIssuedAt(new Date(currentTime)) // Set the issued date of the token to the current time.
                .setExpiration(new Date(currentTime + expirationTime)) // Set the expiration date of the token.
                .signWith(SignatureAlgorithm.HS512, "yourSecretKey") // Sign the token with the HMAC-SHA512 algorithm and a secret key.
                .compact();

        return token;
    }
}
