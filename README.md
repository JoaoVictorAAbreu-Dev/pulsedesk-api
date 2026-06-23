# PulseDesk API

API backend para gestao de incidentes, tickets e SLA. O projeto foi desenhado para mostrar engenharia de software aplicada em um contexto real de suporte operacional.

## Problema que resolve
- centraliza incidentes e chamados
- classifica prioridade e risco
- ajuda a acompanhar SLA e atraso
- cria base para auditoria e dashboard operacional

## Stack
- Java 21
- Spring Boot
- Spring Security com JWT
- Spring Data JPA
- PostgreSQL
- H2 para testes

## Funcionalidades
- login com usuario demo
- cadastro e consulta de tickets
- atualizacao de status
- resumo operacional do SLA
- seguranca com JWT para rotas protegidas

## Credenciais de demo
- email: `demo@pulsedesk.dev`
- senha: `pulse123`

## Variaveis de ambiente
Use `.env.example` como base para configurar:
- `DATABASE_URL`
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MINUTES`

## Execucao local
1. suba o PostgreSQL com `docker compose up -d`
2. ajuste as variaveis de ambiente
3. execute `mvn spring-boot:run`

## Testes
Execute:

```bash
mvn test
```

## Estrutura principal
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

## Endpoints
- `GET /api/health`
- `POST /api/auth/login`
- `GET /api/tickets`
- `POST /api/tickets`
- `PATCH /api/tickets/{id}/status`
- `GET /api/dashboard`

## Observacoes de arquitetura
- controllers sem regra de negocio
- validacao com Bean Validation
- tratamento centralizado de erros
- usuarios demo persistidos em banco
- rotas protegidas por JWT

## Valor para LinkedIn
Projeto forte para demonstrar backend com autenticao, persistencia, testes e dominio operacional.
