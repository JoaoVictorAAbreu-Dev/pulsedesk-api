package com.jv.incidentflow.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
  List<TicketEntity> findAllByOrderByPriorityDescCreatedAtDesc();
}
