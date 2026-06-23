package com.jv.incidentflow.dashboard;

import com.jv.incidentflow.ticket.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationsDashboardController {
  private final TicketService service;

  public OperationsDashboardController(TicketService service) {
    this.service = service;
  }

  @GetMapping("/api/dashboard")
  TicketService.SlaSummary dashboard() {
    return service.summary();
  }
}
