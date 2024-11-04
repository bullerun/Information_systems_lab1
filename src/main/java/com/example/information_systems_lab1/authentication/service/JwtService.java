package com.example.information_systems_lab1.authentication.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private String jwtSigningKey = "verySecretKeyverySecretKeyverySecretKeyverySecretKeyverySecretKeyverySecretKeyverySecretKeyverySecretKeyverySecretKey"; //TODO

    private long lifeTime = 10_000_000; //TODO

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + lifeTime * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

    public String getNameFromJwt(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = getNameFromJwt(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}