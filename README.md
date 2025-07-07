# 🛒 Order Service — Kafka Producer (Spring Boot + Podman + KRaft)

This microservice exposes a REST API to accept order requests and publishes them to an Apache Kafka topic. It is built using clean architecture principles and is designed to run in modern distributed environments like Podman, Docker, Kubernetes, and Helm.

> 📦 Part of a full-scale distributed system project for system design interview preparation.

---

## ✅ Features

- REST endpoint: `POST /orders`
- Publishes JSON order events to Kafka topic `order-events`
- Clean architecture with abstract, reusable Kafka producer configuration
- Externalized config via environment variables & application.yml
- Compatible with Kafka in KRaft (Zookeeper-less) mode
- Works with Podman, Docker, Kubernetes, Helm

---

## 🛠 Tech Stack

| Layer              | Technology                |
|-------------------|---------------------------|
| Language           | Java 17+                  |
| Framework          | Spring Boot 3.x           |
| Messaging Queue    | Apache Kafka (KRaft mode) |
| Container Runtime  | Podman (or Docker)        |
| Build Tool         | Maven + Jib               |
| Serialization      | Jackson                   |

---

## 🧱 Architecture Overview

- Abstract class `KafkaProducerConfig<K, V>` defines a generic producer config
- Concrete class `OrderKafkaProducerConfig` uses String serializers
- `OrderController` receives `OrderRequest` via HTTP POST and sends it to Kafka
- KafkaTemplate is generic, injectable, and reusable for different types

---

## 📁 Project Structure

src/
├── main/
│ ├── java/com/daoud/inventory/
│ │ ├── config/
│ │ │ ├── KafkaProducerConfig.java # Abstract config
│ │ │ ├── OrderKafkaProducerConfig.java # String-to-String
│ │ │ └── KafkaProducerProperties.java # External properties
│ │ └── order/
│ │ ├── OrderController.java # REST endpoint
│ │ └── OrderRequest.java # DTO
│ └── resources/
│ └── application.yml
├── Dockerfile (optional)
├── pom.xml
└── README.md

yaml
Copy
Edit


---

## ⚙️ Configuration (application.yml)

```yaml
server:
  port: ${SERVER_PORT:8081}

kafka:
  bootstrap-servers: ${SPRING_KAFKA_BOOTSTRAP_SERVERS:kafka:9092}
  producer:
    acks: ${SPRING_KAFKA_PRODUCER_ACKS:all}
    retries: ${SPRING_KAFKA_PRODUCER_RETRIES:3}
    linger-ms: ${SPRING_KAFKA_PRODUCER_LINGER_MS:5}
    batch-size: ${SPRING_KAFKA_PRODUCER_BATCH_SIZE:16384}
topics:
  order-events: ${ORDER_TOPIC_NAME:order-events}
```



# 📦 Inventory Service — Kafka Consumer (Spring Boot + Podman + KRaft)

This microservice listens for order events from a Kafka topic and updates product inventory in an in-memory store. It is a stateless, reactive component of a distributed system built using clean architecture and event-driven design.

---

## 🚀 Features

- ✅ Listens to Kafka topic order-events
- ✅ Processes incoming OrderEvent JSON messages
- ✅ In-memory product inventory with auto-initialization
- ✅ Graceful stock reduction and logging
- ✅ Externalized configuration via environment variables
- ✅ Works with Podman, Docker, and KRaft mode Kafka

---

## ⚙️ Tech Stack

| Layer             | Technology                |
|------------------|---------------------------|
| Language          | Java 17+                  |
| Framework         | Spring Boot 3.x           |
| Messaging Queue   | Apache Kafka (KRaft mode) |
| Container Runtime | Podman (or Docker)        |
| Build Tool        | Maven                     |
| JSON Handling     | Jackson                   |

---

## 🧱 Architecture Overview

- Kafka consumer using @KafkaListener
- Consumes string-based JSON messages and maps them to OrderEvent DTO
- Updates inventory via InventoryService
- Uses application.yml and ENV overrides for topic/group/kafka host
- Minimal dependencies, fast startup

---

## 📁 Project Structure

src/
├── main/
│   ├── java/com/daoud/inventoryservice/
│   │   ├── config/
│   │   ├── consumer/
│   │   │   └── OrderEventConsumer.java
│   │   ├── inventory/
│   │   │   └── InventoryService.java
│   │   ├── order/
│   │   │   └── OrderEvent.java
│   │   └── InventoryServiceApplication.java
│   └── resources/
│       └── application.yml
└── pom.xml

---

## 🔧 Configuration (application.yml)

```yaml
server:
  port: ${SERVER_PORT:8082}

spring:
  application:
    name: inventory-service

  kafka:
    bootstrap-servers: ${SPRING_KAFKA_BOOTSTRAP_SERVERS:kafka:9092}
    order-topic: ${ORDER_TOPIC_NAME:order-events}
    group-id: inventory-consumer-group
```


