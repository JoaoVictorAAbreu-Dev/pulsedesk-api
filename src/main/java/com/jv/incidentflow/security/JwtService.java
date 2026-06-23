package com.jv.incidentflow.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private final JwtProperties properties;

  public JwtService(JwtProperties properties) {
    this.properties = properties;
  }

  public String generateToken(String subject) {
    var key = Keys.hmacShaKeyFor(properties.secret().getBytes(StandardCharsets.UTF_8));
    var now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(properties.expirationMinutes() * 60)))
        .signWith(key)
        .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      getSubject(token);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public String getSubject(String token) {
    var key = Keys.hmacShaKeyFor(properties.secret().getBytes(StandardCharsets.UTF_8));
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
  }
}
