# API de Agendamentos de Festas – Resumo Enxuto

## Arquitetura
Camadas + Domain-Driven Design (DDD)
```
Controller → Service → Domain → Repository → PostgreSQL
```
- Services orquestram operações
- Entidades encapsulam lógica de negócio
- DTOs separam entrada/saída

## Estrutura de Pacotes
```
client/          # Cliente: Client.java, ClientService, ClientController, DTOs, exceções
place/           # Espaço: Place.java, PlaceService, PlaceController, DTOs
booking/         # Agendamento: Booking.java, BookingService, BookingController, DTOs, Enums
interfaces/      # Genéricos: BaseServiceImpl, BaseControllerImpl
security/        # OAuth2 Resource Server (Keycloak)
```

## Entidades e Regras de Domínio

### Client
- `name`: obrigatório, não vazio → `IllegalArgumentException`
- `phoneNumber`: obrigatório, não vazio, múltiplos formatos → `IllegalArgumentException`
- `dateOfBirth`: obrigatório → `IllegalArgumentException`

### Place
- `name`: obrigatório, não vazio → `IllegalArgumentException`
- `address`: obrigatório, não vazio → `IllegalArgumentException`
- `capacity`: > 0 (validado no constructor e setter) → `IllegalArgumentException`

### Booking
- `place`: obrigatório, deve existir no BD → `RuntimeException` (não encontrado)
- `eventDate`, `dateEnd`: obrigatórios, `dateEnd > eventDate` → `IllegalArgumentException`
- `value`: > 0, obrigatório em `changeValue()` → `IllegalArgumentException`
- `partyPackage`: obrigatório em `changePartyPackage()` → `IllegalArgumentException`
- `clientName`: pode ser null na criação, obrigatório em `changeClientName()` → `IllegalArgumentException`
- `bookingStatus`: PENDING (inicial) → CONFIRMED (apenas de PENDING) / CANCELLED (idempotente) / EXPIRED (PENDING com data passada)

## Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.6+
- Docker e Docker Compose

### Passos
```bash
# 1. Iniciar infraestrutura (PostgreSQL + Keycloak)
docker-compose up -d

# 2. Build
mvn clean install

# 3. Executar aplicação
mvn spring-boot:run

# 4. Parar infraestrutura
docker-compose down
```
**Porta padrão**: `http://localhost:8080/api/`

## Como Rodar os Testes

```bash
# Todos os testes (124)
mvn test

# Apenas domínio (97)
mvn test -Dtest=*DomainTest

# Apenas service (27)
mvn test -Dtest=*ServiceTest_v2

# Teste específico por classe
mvn test -Dtest=ClientDomainTest

# Teste específico por método
mvn test -Dtest=ClientDomainTest#shouldThrowWhenNameIsNullOrBlank
```

| Tipo | Arquivo | Quantidade | Escopo |
|------|---------|-----------|--------|
| Domain | `*DomainTest.java` | 97 | Sem Spring, sem Mockito – validam regras puras |
| Service | `*ServiceTest_v2.java` | 27 | Com Mockito, sem Spring – validam orquestração |

## Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.2 | Framework web |
| Spring Data JPA | - | ORM |
| Spring Security + OAuth2 | - | Autenticação/autorização |
| PostgreSQL | 17-alpine | Banco de dados |
| Flyway | latest | Migrations |
| Keycloak | 24.0.5 | OAuth2 Identity Provider |
| SpringDoc OpenAPI | 2.5.0 | Swagger/OpenAPI |
| JUnit 5 | - | Testes unitários |
| Mockito | - | Mocks em testes |
| Maven | 3.6+ | Build |

---

**Status**: Documentação enxuta baseada em análise de código

