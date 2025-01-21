package it.lucadev.amazon.jwt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;

@Singleton
public class AmazonJwtService {
    public String generateJwt() {
        
        Set<String> roles = new HashSet<>(Arrays.asList("admin", "writer"));

        return Jwt.issuer("amazon-jwt")
                    .subject("amazon-jwt")
                    .groups(roles)
                    .expiresAt(System.currentTimeMillis()/1000 + 3600)
                    .sign();
    }
}
