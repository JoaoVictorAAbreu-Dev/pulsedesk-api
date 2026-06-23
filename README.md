# PulseDesk API

API backend para gestao de incidentes, tickets e SLA.

## Objetivo
Centralizar chamados operacionais e dar visibilidade de prioridade, atraso e risco para suportar tomada de decisao em ambientes de suporte e engenharia.

## Problema atendido
- chamados chegam por canais dispersos
- prioridades ficam subjetivas
- SLA nao e acompanhado de forma consistente
- historico de resposta se perde com facilidade

## Escopo funcional
- autenticacao com JWT
- cadastro e listagem de tickets
- atualizacao de status
- resumo operacional de SLA
- indicadores de criticidade e atraso

## Stack
- Java 21
- Spring Boot
- Spring Security com JWT
- Spring Data JPA
- PostgreSQL em producao
- H2 em testes

## Regras de negocio
- tickets novos comecam como `OPEN`
- prioridade define urgencia operacional
- tickets escalados aumentam o risco
- tickets vencidos entram como atraso no dashboard

## Credenciais de demo
- email: `demo@pulsedesk.dev`
- senha: `pulse123`

## Variaveis de ambiente
- `DATABASE_URL`
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MINUTES`

## Como executar
1. suba o PostgreSQL com `docker compose up -d`
2. ajuste as variaveis de ambiente
3. execute `mvn spring-boot:run`

## Como testar
```bash
mvn test
```

## Endpoints principais
- `GET /api/health`
- `POST /api/auth/login`
- `GET /api/tickets`
- `POST /api/tickets`
- `PATCH /api/tickets/{id}/status`
- `GET /api/dashboard`

## Estrutura do projeto
```text
src/main/java/com/jv/incidentflow
  auth/
  config/
  dashboard/
  exception/
  security/
  ticket/
  user/
```

## Decisoes tecnicas
- controllers finos
- regras concentradas em services
- DTOs para entrada e saida
- tratamento centralizado de erros
- persistencia relacional com UUID
- rotas protegidas por JWT

## Evolucao prevista
- historico de mudancas por ticket
- comentarios e atribuicao de responsavel
- notificacoes de SLA
- filtros por prioridade, status e prazo
