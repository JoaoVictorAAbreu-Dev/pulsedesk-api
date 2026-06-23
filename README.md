# PulseDesk API

API backend para resolver a dor de chamados, incidentes e SLA. O objetivo e mostrar controle de fluxo operacional, rastreabilidade e prioridade em cenarios reais de suporte e engenharia.

## Proposta
- Registro e classificacao de incidentes
- SLA por prioridade
- Historico de mudancas e responsaveis
- Base para auditoria e dashboards operacionais
- Cadastro e consulta de tickets
- Atualizacao de status
- Resumo operacional do SLA

## Stack
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- H2 para testes

## Estrutura
```text
src/main/java/com/jv/incidentflow
  config/
  dashboard/
  exception/
  ticket/
```

## Endpoints
- `GET /api/health`
- `GET /api/tickets`
- `POST /api/tickets`
- `PATCH /api/tickets/{id}/status`
- `GET /api/dashboard`

## Regras
- tickets novos começam como `OPEN`
- prioridade define urgencia operacional
- tickets escalados aumentam risco no dashboard
- SLA e prazo permitem identificar atraso

## LinkedIn
Projeto forte para mostrar dominio de operacao, previsibilidade e organizacao de incidentes.

