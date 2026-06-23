package com.jv.incidentflow.ticket;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public final class TicketDtos {
  private TicketDtos() {}

  public record CreateTicketRequest(
      @NotBlank @Size(max = 140) String title,
      @NotBlank @Size(max = 1000) String description,
      @NotNull TicketPriority priority,
      @Min(1) int slaHours,
      boolean escalated
  ) {}

  public record UpdateTicketStatusRequest(@NotNull TicketStatus status) {}

  public record TicketResponse(
      UUID id,
      String title,
      String description,
      TicketPriority priority,
      TicketStatus status,
      int slaHours,
      boolean escalated,
      LocalDateTime createdAt,
      LocalDateTime dueAt
  ) {}
}
