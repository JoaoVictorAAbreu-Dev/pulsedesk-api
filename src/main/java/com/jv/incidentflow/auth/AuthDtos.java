package com.jv.incidentflow.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public final class AuthDtos {
  private AuthDtos() {}
  public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
  public record TokenResponse(String token) {}
}
