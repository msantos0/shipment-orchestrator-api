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
- `POST /api/v1/shipments`: cria uma remessa com `origin`, `destination` e `trackingCode`.
- `GET /api/v1/shipments`: lista as remessas com `page`, `size`, `status` e `trackingCode` opcionais.
- `GET /api/v1/shipments/{id}`: consulta uma remessa pelo ID.
- `PUT /api/v1/shipments/{id}`: atualiza uma remessa.
- `DELETE /api/v1/shipments/{id}`: remove uma remessa.
- `GET /swagger-ui.html`: abre a documentacao interativa da API.

## Testar

```bash
mvn test
```

## Estrutura

```text
src/main/java/com/shipmentorchestrator/api
|-- ShipmentOrchestratorApiApplication.java
|-- web/HealthController.java
`-- shipment
	|-- api
	|   |-- CreateShipmentRequest.java
	|   |-- ShipmentController.java
	|   |-- ShipmentExceptionHandler.java
	|   |-- ShipmentPageResponse.java
	|   |-- ShipmentResponse.java
	|   `-- UpdateShipmentRequest.java
	|-- application
	|   |-- CreateShipmentCommand.java
	|   |-- ShipmentMapper.java
	|   |-- ShipmentNotFoundException.java
	|   |-- ShipmentOutput.java
	|   |-- ShipmentService.java
	|   `-- UpdateShipmentCommand.java
	|-- domain
	|   |-- Shipment.java
	|   |-- ShipmentRepository.java
	|   `-- ShipmentStatus.java
	`-- infrastructure
		|-- ShipmentDocument.java
		|-- ShipmentMongoRepository.java
		|-- ShipmentPersistenceMapper.java
		`-- ShipmentRepositoryAdapter.java
src/main/resources/application.yml
src/test/java/com/shipmentorchestrator/api
`-- ShipmentOrchestratorApiApplicationTests.java
```

O dominio nao depende de MongoDB. A infraestrutura concentra o documento Mongo,
o repositorio Spring Data e o adaptador de persistencia. Esta e uma organizacao
DDD simplificada, sem introduzir uma arquitetura hexagonal completa.
