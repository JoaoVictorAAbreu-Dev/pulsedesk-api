package com.jv.incidentflow.auth;

import com.jv.incidentflow.security.JwtService;
import com.jv.incidentflow.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public AuthDtos.TokenResponse login(AuthDtos.LoginRequest request) {
    var user = userRepository.findByEmail(request.email()).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid credentials");
    }
    return new AuthDtos.TokenResponse(jwtService.generateToken(user.getEmail()));
  }
}
