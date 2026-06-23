package com.jv.incidentflow.auth;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/api/auth/login")
  AuthDtos.TokenResponse login(@Valid @RequestBody AuthDtos.LoginRequest request) {
    return authService.login(request);
  }
}
