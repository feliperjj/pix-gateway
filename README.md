# ⚡ Pix Gateway

[![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/)
[![React](https://img.shields.io/badge/React_+_Vite-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

Sistema full stack de geração e gestão de cobranças via Pix. O backend gera payloads no padrão EMV (Pix Copia e Cola) e QR Codes reais; o frontend consome a API e simula o fluxo completo de pagamento.

---

## 📸 Demonstração

---

## 📸 Demonstração do Fluxo (Passo a Passo)

Aqui está o ciclo completo de uma cobrança, desde a sua criação até a visualização no Swagger.

<p align="center">
  <b>1️⃣ Criando uma Nova Cobrança (Interface React)</b><br>
  <img src="./docs/NovaCobrança.JPG" width="600" alt="Tela de criação de nova cobrança">
  <br><br>

  <b>2️⃣ QR Code Dinâmico Gerado</b><br>
  <img src="./docs/TelaQrCode.JPG" width="450" alt="Tela exibindo o QR Code gerado">
  <br><br>

  <b>3️⃣ Pagamento Confirmado (Simulação)</b><br>
  <img src="./docs/Pago.JPG" width="600" alt="Tela de confirmação de pagamento">
  <br><br>

  <b>4️⃣ Documentação e Teste dos Endpoints (Swagger UI)</b><br>
  <img src="./docs/Swagger Endpoints.JPG" width="700" alt="Endpoints documentados no Swagger">
</p>

---

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────┐
│                  Frontend                   │
│           React + Vite (porta 5174)         │
└────────────────────┬────────────────────────┘
                     │ HTTP + Basic Auth
                     ▼
┌─────────────────────────────────────────────┐
│                  Backend                    │
│         Spring Boot 3 (porta 8080)          │
│                                             │
│  CobrancaController → CobrancaService       │
│                            │                │
│                      PixProvider            │
│                   (interface)               │
│                      │                      │
│              MockPixProvider                │
│         (implementação simulada)            │
└────────────────────┬────────────────────────┘
                     │ JPA
                     ▼
┌─────────────────────────────────────────────┐
│              PostgreSQL (Docker)            │
│              pixgateway_db:5433             │
└─────────────────────────────────────────────┘
```

---

## 🔌 Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/status` | Health check da aplicação |
| `GET` | `/api/cobrancas` | Lista todas as cobranças |
| `POST` | `/api/cobrancas` | Cria nova cobrança Pix |
| `POST` | `/api/cobrancas/{id}/pagar` | Simula confirmação de pagamento |

**Exemplo de request — criar cobrança:**
```json
POST /api/cobrancas
{
  "valor": 30.00,
  "chavePix": "felipeoliveirabento@gmail.com"
}
```

**Exemplo de response:**
```json
{
  "id": 1,
  "valor": 30.00,
  "status": "PENDENTE",
  "chavePix": "felipeoliveirabento@gmail.com",
  "qrCode": "data:image/png;base64,...",
  "txid": "abc123",
  "pixCopiaCola": "00020101021226580014br.gov.bcb.pix...",
  "dataCriacao": "2026-04-07T11:34:00",
  "dataPagamento": null
}
```

---

## 🛠️ Stack

| Camada | Tecnologia |
|--------|-----------|
| Backend | Java 17, Spring Boot 3, Spring Security, Spring Data JPA |
| Banco de Dados | PostgreSQL (Docker Compose) |
| Frontend | React, Vite, Axios |
| Testes | JUnit 5, Mockito |
| Infra | Docker, GitHub Codespaces |

---

## 📁 Estrutura de Pastas

```
pix-gateway/
├── backend/
│   └── src/
│       ├── main/java/com/pixgateway/backend/
│       │   ├── controller/         # CobrancaController, StatusController
│       │   ├── domain/             # Cobranca (entidade), DTOs
│       │   ├── service/            # CobrancaService
│       │   ├── repository/         # CobrancaRepository
│       │   └── infrastructure/pix/ # PixProvider (interface), MockPixProvider
│       └── test/                   # Testes unitários (JUnit 5 + Mockito)
└── frontend/
    └── src/
        └── ...                     # Componentes React
```

---

## ▶️ Como rodar localmente

**Pré-requisitos:** Java 17, Docker, Node.js 18+

```bash
# 1. Clonar o repositório
git clone https://github.com/feliperjj/pix-gateway.git
cd pix-gateway

# 2. Subir o banco de dados
cd backend
docker-compose up -d

# 3. Iniciar o backend
./mvnw spring-boot:run

# 4. Em outro terminal, iniciar o frontend
cd ../frontend
npm install
npm run dev
```

Acesse: `http://localhost:5174`
Swagger: `http://localhost:8080/swagger-ui/index.html`

---

## 🧪 Testes

```bash
cd backend
./mvnw test
```

---

## 👤 Autor

Desenvolvido por **Felipe Bento**

[![GitHub](https://img.shields.io/badge/GitHub-@feliperjj-181717?style=flat&logo=github)](https://github.com/feliperjj)
