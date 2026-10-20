# Sistema de Processamento de Boletos

Projeto didático que simula o fluxo de solicitação, validação e pagamento de boletos bancários usando **arquitetura orientada a eventos**, com **Apache Kafka**, **Avro** e **Spring Boot**.

O sistema é composto por duas aplicações independentes que se comunicam de forma assíncrona através de tópicos do Kafka:

| Serviço | Descrição | Porta |
|---|---|---|
| `api.boleto` | API REST que recebe solicitações de pagamento de boletos e acompanha sua situação | `8282` |
| `validador-boleto` | Serviço que consome as solicitações, valida e "paga" os boletos, publicando notificações de resultado | `8383` (aleatória entre 8383–9090) |

## 📌 Fluxo do sistema

```
Cliente ──POST /api/boleto──▶ api.boleto ──▶ [tópico: solicitacao-boleto] ──▶ validador-boleto
                                 ▲                                                  │
                                 │                                          valida / paga
                                 │                                                  │
                        [tópico: notificacao-boleto] ◀───────────────────────────────┘
```

1. O cliente envia uma solicitação de pagamento via `POST /api/boleto` na `api.boleto`;
2. A API registra o boleto em banco (com situação `INICIALIZADO`) e publica uma mensagem Avro no tópico **`solicitacao-boleto`**;
3. O `validador-boleto` consome a mensagem, realiza a validação e o pagamento simulados, atualizando a situação (`VALIDADO`, `ERRO_VALIDACAO`, `PAGO` ou `ERRO_PAGAMENTO`) e publica o resultado no tópico **`notificacao-boleto`**;
4. A `api.boleto` consome a notificação e atualiza a situação do boleto em seu banco de dados;
5. O cliente pode consultar a situação final via `GET /api/boleto/{codigoBarras}`.

### Situações do boleto (enum `SituacaoBoleto`)

| Valor | Situação |
|---|---|
| 0 | `INICIALIZADO` |
| 1 | `VALIDADO` |
| 2 | `ERRO_VALIDACAO` |
| 3 | `PAGO` |
| 4 | `ERRO_PAGAMENTO` |

## 🧰 Tecnologias

- **Java 25**
- **Spring Boot 4.1.1** (Web MVC, Data JPA, Validation, Kafka)
- **Apache Kafka** + **Confluent Schema Registry** (mensagens serializadas em **Avro**)
- **Banco H2** em memória (console disponível em `/h2-console`)
- **SpringDoc / Swagger UI** para documentação da API
- **Maven** (via Maven Wrapper)
- **Docker Compose** para subir o ambiente de mensageria (Zookeeper, Kafka, Schema Registry e Control Center)

## 📁 Estrutura do projeto

```
.
├── api.boleto/               # API REST de solicitação/consulta de boletos
│   └── src/main/
│       ├── avro/             # Esquema Avro (boleto.avsc)
│       ├── java/.../         # Controller, Service, Repository, Mapper, Kafka (producer/consumer)
│       └── resources/        # application.properties
├── validador-boleto/         # Serviço de validação e pagamento
│   └── src/main/
│       ├── avro/             # Mesmo esquema Avro (boleto.avsc)
│       ├── java/.../         # Consumers, services de validação/pagamento, producer de notificação
│       └── resources/        # application.properties
└── docker-compose.yml        # Zookeeper, Kafka, Schema Registry e Control Center
```

## 🚀 Como executar

### Pré-requisitos

- Docker e Docker Compose
- JDK 25 (ou superior) instalado — o Maven Wrapper (`mvnw`) é incluído nos projetos

### 1. Subir o ambiente de mensageria

```bash
docker compose up -d
```

Isso inicia:

| Serviço | URL |
|---|---|
| Kafka Broker | `localhost:19092` |
| Schema Registry | `http://localhost:8081` |
| Zookeeper | `localhost:2181` |
| Kafka Control Center (UI) | `http://localhost:9021` |

### 2. Executar a API de boletos

```bash
cd api.boleto
./mvnw spring-boot:run
```

Aplicação disponível em `http://localhost:8282`.

### 3. Executar o validador de boletos

Em outro terminal:

```bash
cd validador-boleto
./mvnw spring-boot:run
```

## 🔎 Endpoints da API (`api.boleto`)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/boleto` | Registra uma nova solicitação de pagamento. Corpo: `{"codigoBarras": "..."}` |
| `GET` | `/api/boleto/{codigoBarras}` | Consulta a situação atual do boleto pelo código de barras |

### Exemplo de uso

```bash
# Solicitar pagamento
curl -X POST http://localhost:8282/api/boleto \
  -H "Content-Type: application/json" \
  -d '{"codigoBarras": "34191"}'

# Consultar situação (após o processamento assíncrono)
curl http://localhost:8282/api/boleto/34191
```

### Documentação interativa

- **Swagger UI**: `http://localhost:8282/swagger-ui.html`
- **Console H2 (api.boleto)**: `http://localhost:8282/h2-console`
  - JDBC URL: `jdbc:h2:mem:boleto` — usuário: `admin` — senha: `123`

## ⚙️ Configuração Kafka

Os esquemas Avro das mensagens são registrados automaticamente no Schema Registry. O contrato compartilhado (`boleto.avsc`):

```json
{
  "type": "record",
  "namespace": "br.com.gabrielflorentino.avro",
  "name": "Boleto",
  "fields": [
    { "name": "codigoBarras", "type": "string" },
    { "name": "situacaoBoleto", "type": "int" }
  ]
}
```

| Propriedade | `api.boleto` | `validador-boleto` |
|---|---|---|
| Bootstrap servers | `localhost:19092` | `localhost:19092` |
| Schema Registry | `http://localhost:8081` | `http://localhost:8081` |
| Tópico de solicitações | `solicitacao-boleto` (produz) | `solicitacao-boleto` (consome) |
| Tópico de notificações | `notificacao-boleto` (consome) | `notificacao-boleto` (produz) |

> Observação: no `validador-boleto` o commit dos offsets é **manual** (`enable-auto-commit=false` e `ack-mode=manual`).

## ✅ Testes

```bash
cd api.boleto && ./mvnw test
cd validador-boleto && ./mvnw test
```

## 👤 Autor

[Gabriel Florentino](https://github.com/gabrielflorentino)
