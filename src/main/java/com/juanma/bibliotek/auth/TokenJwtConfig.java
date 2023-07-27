package com.juanma.bibliotek.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class TokenJwtConfig {
    public final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
}
