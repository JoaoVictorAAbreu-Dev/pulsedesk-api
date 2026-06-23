package com.jv.incidentflow.ticket;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class TicketEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 140)
  private String title;

  @Column(nullable = false, length = 1000)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TicketPriority priority;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TicketStatus status;

  @Column(nullable = false)
  private int slaHours;

  @Column(nullable = false)
  private boolean escalated;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime dueAt;

  @PrePersist
  void prePersist() {
    if (status == null) {
      status = TicketStatus.OPEN;
    }
    if (priority == null) {
      priority = TicketPriority.MEDIUM;
    }
    createdAt = LocalDateTime.now();
    dueAt = createdAt.plusHours(slaHours);
  }

  public UUID getId() { return id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public TicketPriority getPriority() { return priority; }
  public void setPriority(TicketPriority priority) { this.priority = priority; }
  public TicketStatus getStatus() { return status; }
  public void setStatus(TicketStatus status) { this.status = status; }
  public int getSlaHours() { return slaHours; }
  public void setSlaHours(int slaHours) { this.slaHours = slaHours; }
  public boolean isEscalated() { return escalated; }
  public void setEscalated(boolean escalated) { this.escalated = escalated; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public LocalDateTime getDueAt() { return dueAt; }
}
