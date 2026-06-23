package com.jv.incidentflow.ticket;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TicketService {
  private final TicketRepository repository;

  public TicketService(TicketRepository repository) {
    this.repository = repository;
  }

  public TicketDtos.TicketResponse create(TicketDtos.CreateTicketRequest request) {
    var ticket = new TicketEntity();
    ticket.setTitle(request.title().trim());
    ticket.setDescription(request.description().trim());
    ticket.setPriority(request.priority());
    ticket.setSlaHours(request.slaHours());
    ticket.setEscalated(request.escalated());
    ticket.setStatus(TicketStatus.OPEN);
    return toResponse(repository.save(ticket));
  }

  @Transactional(readOnly = true)
  public List<TicketDtos.TicketResponse> list() {
    return repository.findAllByOrderByPriorityDescCreatedAtDesc().stream().map(this::toResponse).toList();
  }

  public TicketDtos.TicketResponse updateStatus(UUID id, TicketStatus status) {
    var ticket = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
    ticket.setStatus(status);
    return toResponse(repository.save(ticket));
  }

  @Transactional(readOnly = true)
  public SlaSummary summary() {
    var tickets = repository.findAll();
    var total = tickets.size();
    var overdue = tickets.stream().filter(ticket -> ticket.getDueAt().isBefore(LocalDateTime.now()) && ticket.getStatus() != TicketStatus.CLOSED).count();
    var critical = tickets.stream().filter(ticket -> ticket.getPriority() == TicketPriority.CRITICAL).count();
    var escalated = tickets.stream().filter(TicketEntity::isEscalated).count();
    return new SlaSummary(total, overdue, critical, escalated, computeHealthScore(total, overdue, escalated));
  }

  public long minutesToDue(TicketEntity ticket) {
    return Duration.between(LocalDateTime.now(), ticket.getDueAt()).toMinutes();
  }

  private int computeHealthScore(long total, long overdue, long escalated) {
    return Math.max(0, 100 - (int) (overdue * 20 + escalated * 10 + total * 2));
  }

  private TicketDtos.TicketResponse toResponse(TicketEntity ticket) {
    return new TicketDtos.TicketResponse(ticket.getId(), ticket.getTitle(), ticket.getDescription(), ticket.getPriority(), ticket.getStatus(), ticket.getSlaHours(), ticket.isEscalated(), ticket.getCreatedAt(), ticket.getDueAt());
  }

  public record SlaSummary(long totalTickets, long overdueTickets, long criticalTickets, long escalatedTickets, int healthScore) {}
}
