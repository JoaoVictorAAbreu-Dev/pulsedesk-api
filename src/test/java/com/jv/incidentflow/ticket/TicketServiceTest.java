package com.jv.incidentflow.ticket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
  @Mock TicketRepository repository;
  @InjectMocks TicketService service;

  @Test
  void createShouldInitializeOpenTicket() {
    when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    var response = service.create(new TicketDtos.CreateTicketRequest("Database outage", "Orders are failing", TicketPriority.CRITICAL, 4, true));
    assertThat(response.status()).isEqualTo(TicketStatus.OPEN);
    assertThat(response.priority()).isEqualTo(TicketPriority.CRITICAL);
    assertThat(response.slaHours()).isEqualTo(4);
  }

  @Test
  void summaryShouldCountOverdueEscalatedTickets() {
    var ticket = new TicketEntity();
    ticket.setTitle("API down");
    ticket.setDescription("Production issue");
    ticket.setPriority(TicketPriority.CRITICAL);
    ticket.setStatus(TicketStatus.IN_PROGRESS);
    ticket.setSlaHours(1);
    ticket.setEscalated(true);
    ticket.setEscalated(true);
    try {
      var createdAtField = TicketEntity.class.getDeclaredField("createdAt");
      createdAtField.setAccessible(true);
      createdAtField.set(ticket, LocalDateTime.now().minusHours(2));
      var dueAtField = TicketEntity.class.getDeclaredField("dueAt");
      dueAtField.setAccessible(true);
      dueAtField.set(ticket, LocalDateTime.now().minusHours(1));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    when(repository.findAll()).thenReturn(java.util.List.of(ticket));
    var summary = service.summary();
    assertThat(summary.overdueTickets()).isEqualTo(1);
    assertThat(summary.escalatedTickets()).isEqualTo(1);
  }

  @Test
  void updateStatusShouldFailWhenTicketDoesNotExist() {
    var id = UUID.randomUUID();
    when(repository.findById(id)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> service.updateStatus(id, TicketStatus.RESOLVED))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Ticket not found");
  }
}
