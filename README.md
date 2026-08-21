# Shipment Orchestrator API

API base para orquestracao de remessas e operacoes logisticas, construida com Spring Boot 3, Java 21 e MongoDB.

## Requisitos

- JDK 21
- Maven 3.9+
- MongoDB 7+

## Executar

```bash
mvn spring-boot:run
```

A aplicacao inicia em `http://localhost:8080`.

A URI do MongoDB pode ser alterada pela variavel de ambiente `MONGODB_URI`. O valor padrao e `mongodb://localhost:27017/shipment_orchestrator`.

## Endpoints iniciais

- `GET /api/v1/health`: verifica a disponibilidade da API.
- `GET /actuator/health`: health check do Spring Boot Actuator.
- `POST /api/v1/shipments`: cria uma remessa com `origin` e `destination`.
- `GET /api/v1/shipments`: lista as remessas.
- `GET /api/v1/shipments/{id}`: consulta uma remessa pelo ID.
- `GET /swagger-ui.html`: abre a documentacao interativa da API.

## Testar

```bash
mvn test
```

## Estrutura

```text
src/main/java/com/shipmentorchestrator/api
|-- ShipmentOrchestratorApiApplication.java
`-- web/HealthController.java
src/main/resources/application.yml
src/test/java/com/shipmentorchestrator/api
`-- ShipmentOrchestratorApiApplicationTests.java
```
