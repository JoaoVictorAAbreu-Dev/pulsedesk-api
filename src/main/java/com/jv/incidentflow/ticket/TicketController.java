package com.jv.incidentflow.ticket;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
  private final TicketService service;

  public TicketController(TicketService service) {
    this.service = service;
  }

  @PostMapping
  TicketDtos.TicketResponse create(@Valid @RequestBody TicketDtos.CreateTicketRequest request) {
    return service.create(request);
  }

  @GetMapping
  List<TicketDtos.TicketResponse> list() {
    return service.list();
  }

  @PatchMapping("/{id}/status")
  TicketDtos.TicketResponse updateStatus(@PathVariable UUID id, @Valid @RequestBody TicketDtos.UpdateTicketStatusRequest request) {
    return service.updateStatus(id, request.status());
  }
}
