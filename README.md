<div align="center">

# 🏛️ GuiaServPublico

**Plataforma de orientação para serviços públicos brasileiros**

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://docs.docker.com/compose/)
[![JWT](https://img.shields.io/badge/Auth-JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)](https://jwt.io/)
[![Swagger](https://img.shields.io/badge/Docs-Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

[![GitHub repo](https://img.shields.io/badge/GitHub-nicholasfocke%2Fguia--serv--publico-181717?style=flat-square&logo=github)](https://github.com/nicholasfocke/guia-serv-publico)
[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=flat-square)]()

</div>

---

## 📋 Sumário

1. [Descrição do Sistema](#-descrição-do-sistema)
2. [Objetivo](#-objetivo)
3. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
4. [Arquitetura do Projeto](#-arquitetura-do-projeto)
5. [Funcionalidades](#-funcionalidades)
6. [Regras de Acesso](#-regras-de-acesso)
7. [Estrutura de Pastas](#-estrutura-de-pastas)
8. [Modelo de Banco de Dados](#-modelo-de-banco-de-dados)
9. [Principais Endpoints](#-principais-endpoints)
10. [Autenticação JWT](#-autenticação-jwt)
11. [Swagger / OpenAPI](#-swagger--openapi)
12. [Docker e Docker Compose](#-docker-e-docker-compose)
13. [Como Executar o Projeto](#-como-executar-o-projeto)
14. [Como Executar via Docker](#-como-executar-via-docker)
15. [Configuração do PostgreSQL](#-configuração-do-postgresql)
16. [Variáveis de Ambiente / application.yml](#-variáveis-de-ambiente--applicationyml)
17. [Exemplos de Requisições](#-exemplos-de-requisições)
18. [Estrutura Futura do Frontend Angular](#-estrutura-futura-do-frontend-angular)
19. [Melhorias Futuras](#-melhorias-futuras)
20. [Roadmap](#-roadmap)
21. [Segurança](#-segurança)
22. [Boas Práticas Aplicadas](#-boas-práticas-aplicadas)
23. [Autor](#-autor)

---

## 📖 Descrição do Sistema

O **GuiaServPublico** é uma API REST backend desenvolvida em **Java 21 com Spring Boot 3**, projetada para centralizar e organizar informações sobre serviços públicos brasileiros. A plataforma conecta cidadãos às unidades de atendimento, documentos exigidos, horários de funcionamento e avaliações de outros usuários — tudo em um único ponto de acesso estruturado e seguro.

O sistema foi projetado com foco em escalabilidade, separação de responsabilidades e boas práticas de engenharia de software, servindo como base sólida para integração com um frontend moderno (Angular) no futuro.

---

## 🎯 Objetivo

Permitir que qualquer cidadão encontre, de forma simples e rápida:

- **Serviços públicos** disponíveis (emissão de RG, vacinação, segunda via de documentos etc.)
- **Unidades de atendimento** com localização e contato
- **Documentos necessários** para cada serviço
- **Horários de funcionamento** das unidades
- **Avaliações** de outros cidadãos sobre os serviços

---

## 🛠️ Tecnologias Utilizadas

| Categoria | Tecnologia | Versão |
|---|---|---|
| Linguagem | Java | 21 (LTS) |
| Framework | Spring Boot | 3.x |
| Segurança | Spring Security + JWT | - |
| Persistência | Spring Data JPA + Hibernate | - |
| Banco de Dados | PostgreSQL | 16 |
| Banco de Testes | H2 (in-memory) | - |
| Documentação | Swagger / OpenAPI 3 | - |
| Build | Apache Maven | 3.x |
| Utilitários | Lombok | - |
| Containerização | Docker + Docker Compose | - |
| Arquitetura | REST + MVC | - |

---

## 🏗️ Arquitetura do Projeto

O projeto segue a **Arquitetura MVC em camadas**, padrão consolidado para APIs REST com Spring Boot. Cada camada tem uma responsabilidade bem definida e se comunica apenas com a camada imediatamente abaixo.
┌─────────────────────────────────────────────────────┐
│                   Cliente / Swagger UI               │
└──────────────────────────┬──────────────────────────┘
│ HTTP Request
┌──────────────────────────▼──────────────────────────┐
│                    Controller Layer                  │
│         Recebe requisições, valida entrada,          │
│         aciona o Service e retorna a resposta        │
└──────────────────────────┬──────────────────────────┘
│
┌──────────────────────────▼──────────────────────────┐
│                    Service Layer                     │
│       Contém toda a lógica de negócio do sistema.    │
│       Orquestra chamadas ao Repository e ao DTO      │
└──────────────────────────┬──────────────────────────┘
│
┌──────────────────────────▼──────────────────────────┐
│                   Repository Layer                   │
│      Interfaces JPA que abstraem o acesso ao banco.  │
│      Queries customizadas via @Query ou method name  │
└──────────────────────────┬──────────────────────────┘
│
┌──────────────────────────▼──────────────────────────┐
│                      Database                        │
│                  PostgreSQL / H2                     │
└─────────────────────────────────────────────────────┘

### Papel de cada camada

**Controller** — Ponto de entrada da API. Responsável por receber as requisições HTTP, realizar validações básicas de entrada (com Bean Validation), delegar ao Service e devolver a resposta adequada (status code + body).

**Service** — Núcleo da lógica de negócio. Realiza validações complexas, aplica regras de domínio, coordena múltiplos repositórios quando necessário e realiza o mapeamento entre entidades e DTOs. É completamente desacoplada da camada de transporte HTTP.

**Repository** — Interface que estende `JpaRepository`. Abstrai completamente o acesso ao banco de dados. Queries simples são geradas automaticamente pelo Spring Data; queries complexas utilizam `@Query` com JPQL ou SQL nativo.

**DTO (Data Transfer Object)** — Objetos intermediários que trafegam entre as camadas Controller ↔ Service. Evitam expor as entidades JPA diretamente na API e permitem controlar exatamente quais campos são recebidos e retornados.

---

## ✅ Funcionalidades

### Módulos implementados

| Módulo | Descrição |
|---|---|
| 🔐 Autenticação JWT | Registro, login e emissão de tokens |
| 👤 Endpoint `/me` | Dados do usuário autenticado |
| 🗂️ Categorias de Serviço | Agrupamento de serviços por tipo |
| 🏷️ Serviços Públicos | CRUD completo com busca flexível |
| 🏢 Unidades de Atendimento | Cadastro e gestão de locais |
| 📄 Documentos | Documentos exigidos por serviço |
| 🔗 Vínculo Serviço ↔ Unidade | Associação de serviços a unidades |
| 🕐 Horários de Funcionamento | Gestão de horários por unidade |
| ⭐ Avaliações | Avaliação de serviços e unidades por usuários |
| 🔍 Busca Parcial / Flexível | Pesquisa por nome, categoria etc. |
| 📚 Swagger / OpenAPI | Documentação interativa da API |
| ⚠️ Tratamento Global de Exceções | Respostas de erro padronizadas |

---

## 🔐 Regras de Acesso

O sistema possui três perfis de acesso com permissões distintas:

### 🌐 Público (sem autenticação)

| Ação | Endpoint |
|---|---|
| Visualizar serviços | `GET /api/servicos` |
| Buscar serviços | `GET /api/servicos?nome=...` |
| Visualizar unidades | `GET /api/unidades` |
| Visualizar avaliações | `GET /api/avaliacoes` |
| Visualizar horários | `GET /api/horarios` |

### 👤 USER (autenticado)

| Ação | Endpoint |
|---|---|
| Avaliar serviço/unidade | `POST /api/avaliacoes` |
| Visualizar documentos | `GET /api/documentos` |
| Acessar dados próprios | `GET /api/me` |

### 🛡️ ADMIN (autenticado + perfil ADMIN)

| Ação | Endpoint |
|---|---|
| Cadastrar / editar / excluir serviços | `POST/PUT/DELETE /api/servicos` |
| Cadastrar / editar / excluir unidades | `POST/PUT/DELETE /api/unidades` |
| Cadastrar documentos | `POST /api/documentos` |
| Vincular serviços a unidades | `POST /api/servicos/{id}/unidades` |
| Cadastrar horários | `POST /api/horarios` |

---

## 📁 Estrutura de Pastas
guia-serv-publico/
├── src/
│   ├── main/
│   │   ├── java/com/nicholasfocke/guiaservpublico/
│   │   │   ├── config/               # Configurações (Security, JWT, OpenAPI, CORS)
│   │   │   ├── controller/           # Controllers REST por domínio
│   │   │   ├── dto/                  # Request e Response DTOs
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── exception/            # GlobalExceptionHandler e exceções customizadas
│   │   │   ├── model/                # Entidades JPA
│   │   │   ├── repository/           # Interfaces JPA
│   │   │   ├── security/             # Filtros JWT, UserDetailsService, JwtUtil
│   │   │   └── service/              # Lógica de negócio
│   │   └── resources/
│   │       ├── application.yml       # Configuração base
│   │       ├── application-dev.yml   # Perfil de desenvolvimento (PostgreSQL)
│   │       └── application-test.yml  # Perfil de testes (H2)
│   └── test/
│       └── java/com/nicholasfocke/guiaservpublico/
│           └── ...                   # Testes unitários e de integração
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md

---

## 🗄️ Modelo de Banco de Dados

### Entidades principais e relacionamentos
┌──────────────┐       ┌─────────────────────┐       ┌──────────────┐
│   categoria  │1     N│      servico         │N     N│    unidade   │
│──────────────│───────│─────────────────────│───────│──────────────│
│ id           │       │ id                  │       │ id           │
│ nome         │       │ nome                │       │ nome         │
│ descricao    │       │ descricao           │       │ endereco     │
└──────────────┘       │ categoria_id (FK)   │       │ telefone     │
└─────────────────────┘       │ email        │
│                    └──────────────┘
│1                          │
│                           │1
┌───────▼──────┐          ┌────────▼──────┐
│  documento   │          │    horario     │
│──────────────│          │───────────────│
│ id           │          │ id            │
│ nome         │          │ dia_semana    │
│ descricao    │          │ hora_abertura │
│ servico_id   │          │ hora_fechamento│
└──────────────┘          │ unidade_id    │
└───────────────┘
┌──────────────┐
│    usuario   │1     N┌──────────────┐
│──────────────│───────│  avaliacao   │
│ id           │       │──────────────│
│ nome         │       │ id           │
│ email        │       │ nota         │
│ senha (hash) │       │ comentario   │
│ role         │       │ usuario_id   │
└──────────────┘       │ servico_id   │
│ unidade_id   │
└──────────────┘

### Relacionamentos principais

- `Categoria` → `Serviço`: um para muitos (uma categoria agrupa vários serviços)
- `Serviço` ↔ `Unidade`: muitos para muitos (via tabela de junção `servico_unidade`)
- `Unidade` → `Horário`: um para muitos (cada unidade possui vários horários)
- `Serviço` → `Documento`: um para muitos (cada serviço exige um conjunto de documentos)
- `Usuário` → `Avaliação`: um para muitos (um usuário pode avaliar vários serviços/unidades)

---

## 🔗 Principais Endpoints

### Autenticação

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/api/auth/register` | Público | Cadastro de novo usuário |
| `POST` | `/api/auth/login` | Público | Login e emissão de JWT |
| `GET` | `/api/me` | USER | Dados do usuário autenticado |

### Serviços

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/api/servicos` | Público | Listar todos os serviços |
| `GET` | `/api/servicos/{id}` | Público | Buscar serviço por ID |
| `GET` | `/api/servicos?nome={termo}` | Público | Busca parcial por nome |
| `POST` | `/api/servicos` | ADMIN | Cadastrar novo serviço |
| `PUT` | `/api/servicos/{id}` | ADMIN | Atualizar serviço |
| `DELETE` | `/api/servicos/{id}` | ADMIN | Excluir serviço |

### Unidades de Atendimento

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/api/unidades` | Público | Listar todas as unidades |
| `GET` | `/api/unidades/{id}` | Público | Buscar unidade por ID |
| `POST` | `/api/unidades` | ADMIN | Cadastrar unidade |
| `PUT` | `/api/unidades/{id}` | ADMIN | Atualizar unidade |
| `DELETE` | `/api/unidades/{id}` | ADMIN | Excluir unidade |

### Documentos

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/api/documentos` | USER | Listar documentos |
| `GET` | `/api/documentos/{id}` | USER | Buscar documento por ID |
| `POST` | `/api/documentos` | ADMIN | Cadastrar documento |

### Avaliações

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/api/avaliacoes` | Público | Listar avaliações |
| `POST` | `/api/avaliacoes` | USER | Submeter avaliação |

### Horários

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/api/horarios` | Público | Listar horários |
| `POST` | `/api/horarios` | ADMIN | Cadastrar horário |

### Categorias

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/api/categorias` | Público | Listar categorias |
| `POST` | `/api/categorias` | ADMIN | Cadastrar categoria |

---

## 🔑 Autenticação JWT

O sistema utiliza **JSON Web Tokens (JWT)** para autenticação stateless. Nenhuma sessão é armazenada no servidor.

### Fluxo completo
┌─────────┐                          ┌──────────────────┐                    ┌────────────┐
│ Cliente │                          │   Spring Security │                    │    Banco   │
└────┬────┘                          └────────┬─────────┘                    └─────┬──────┘
│                                        │                                    │
│  POST /api/auth/login                  │                                    │
│  { email, senha }                      │                                    │
│───────────────────────────────────────>│                                    │
│                                        │  Busca usuário por email           │
│                                        │──────────────────────────────────>│
│                                        │  Retorna usuário + hash da senha   │
│                                        │<──────────────────────────────────│
│                                        │  Valida senha com BCrypt           │
│                                        │  Gera JWT assinado (HS256)         │
│  200 OK { token: "eyJ..." }            │                                    │
│<───────────────────────────────────────│                                    │
│                                        │                                    │
│  GET /api/me                           │                                    │
│  Authorization: Bearer eyJ...          │                                    │
│───────────────────────────────────────>│                                    │
│                                        │  JwtAuthFilter intercepta          │
│                                        │  Valida assinatura e expiração     │
│                                        │  Extrai claims (subject, role)     │
│                                        │  Autentica no SecurityContext      │
│  200 OK { id, nome, email, role }      │                                    │
│<───────────────────────────────────────│                                    │

### Estrutura do Token JWT

```json
{
  "sub": "usuario@email.com",
  "role": "ROLE_USER",
  "iat": 1716000000,
  "exp": 1716086400
}
```

### Componentes de segurança

| Classe | Responsabilidade |
|---|---|
| `JwtUtil` | Geração, assinatura e validação de tokens |
| `JwtAuthFilter` | Filtro que intercepta cada requisição e valida o token |
| `SecurityConfig` | Define rotas públicas, protegidas e a cadeia de filtros |
| `UserDetailsServiceImpl` | Carrega o usuário do banco para o Spring Security |

---

## 📚 Swagger / OpenAPI

A documentação interativa da API está disponível via **Swagger UI** após subir a aplicação.

| Recurso | URL |
|---|---|
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |

A interface permite explorar todos os endpoints, visualizar schemas de request/response e autenticar com o token JWT clicando em **Authorize** e inserindo `Bearer <seu_token>`.

---

## 🐳 Docker e Docker Compose

### O que é Docker?

**Docker** é uma plataforma de containerização que empacota a aplicação e todas as suas dependências em um ambiente isolado e reproduzível, eliminando o problema de "funciona na minha máquina".

### O que é Docker Compose?

**Docker Compose** permite definir e orquestrar múltiplos containers em um único arquivo `docker-compose.yml`. No GuiaServPublico, ele sobe simultaneamente o container da **API Java** e o container do **PostgreSQL**, com a rede e os volumes configurados automaticamente.

### Dockerfile da Aplicação

```dockerfile
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: guia_postgres
    environment:
      POSTGRES_DB: guia_serv_publico
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - guia_network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5

  api:
    build: .
    container_name: guia_api
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/guia_serv_publico
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres
      JWT_SECRET: sua_chave_secreta_aqui
      SPRING_PROFILES_ACTIVE: dev
    depends_on:
      postgres:
        condition: service_healthy
    networks:
      - guia_network

volumes:
  postgres_data:

networks:
  guia_network:
    driver: bridge
```

---

## ▶️ Como Executar o Projeto

### Pré-requisitos

- Java 21+
- Maven 3.8+
- PostgreSQL 16+ (ou Docker)
- Git

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/nicholasfocke/guia-serv-publico.git
cd guia-serv-publico

# 2. Configure as variáveis de ambiente (ou edite application-dev.yml)
export DB_URL=jdbc:postgresql://localhost:5432/guia_serv_publico
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export JWT_SECRET=sua_chave_secreta_muito_longa_e_segura

# 3. Execute com o perfil de desenvolvimento
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# 4. Acesse a API
# http://localhost:8080/api
# http://localhost:8080/swagger-ui.html
```

### Executar testes

```bash
# Testes unitários e de integração (H2 in-memory)
./mvnw test

# Relatório de cobertura (JaCoCo)
./mvnw verify
```

---

## 🐳 Como Executar via Docker

```bash
# 1. Clone o repositório
git clone https://github.com/nicholasfocke/guia-serv-publico.git
cd guia-serv-publico

# 2. Build e subir todos os containers
docker-compose up --build

# 3. Subir em background (modo detached)
docker-compose up -d --build

# 4. Verificar containers em execução
docker ps

# 5. Ver logs da aplicação
docker logs -f guia_api

# 6. Ver logs do banco
docker logs -f guia_postgres

# 7. Parar todos os containers
docker-compose down

# 8. Parar e remover volumes (apaga dados do banco)
docker-compose down -v
```

Após subir, acesse:
- **API:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

---

## 🗃️ Configuração do PostgreSQL

### Criação manual do banco (sem Docker)

```sql
-- Conecte ao PostgreSQL como superusuário
psql -U postgres

-- Crie o banco de dados
CREATE DATABASE guia_serv_publico;

-- Crie um usuário dedicado (recomendado)
CREATE USER guia_user WITH PASSWORD 'senha_segura';
GRANT ALL PRIVILEGES ON DATABASE guia_serv_publico TO guia_user;

-- Saia
\q
```

O Spring Boot com `spring.jpa.hibernate.ddl-auto: update` criará as tabelas automaticamente na primeira execução.

---

## ⚙️ Variáveis de Ambiente / application.yml

### `application.yml` (base)

```yaml
spring:
  application:
    name: guia-serv-publico
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:dev}

server:
  port: 8080
  servlet:
    context-path: /api

jwt:
  secret: ${JWT_SECRET:chave_padrao_apenas_para_desenvolvimento}
  expiration: 86400000   # 24 horas em millisegundos

springdoc:
  swagger-ui:
    path: /swagger-ui.html
  api-docs:
    path: /v3/api-docs
```

### `application-dev.yml` (perfil de desenvolvimento)

```yaml
spring:
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/guia_serv_publico}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

### `application-test.yml` (perfil de testes)

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop
    database-platform: org.hibernate.dialect.H2Dialect
```

### Variáveis de ambiente necessárias em produção

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_URL` | URL JDBC do banco | `jdbc:postgresql://host:5432/db` |
| `DB_USERNAME` | Usuário do banco | `guia_user` |
| `DB_PASSWORD` | Senha do banco | `senha_segura` |
| `JWT_SECRET` | Chave de assinatura JWT | string aleatória de 64+ chars |
| `SPRING_PROFILES_ACTIVE` | Perfil ativo | `dev` / `prod` |

---

## 📡 Exemplos de Requisições

### Registro de usuário

**Request:**
```http
POST /api/auth/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "senha": "Senha@2024"
}
```

**Response `201 Created`:**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "role": "ROLE_USER"
}
```

---

### Login

**Request:**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao.silva@email.com",
  "senha": "Senha@2024"
}
```

**Response `200 OK`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvLnNpbHZhQGVtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MTYwMDAwMDAsImV4cCI6MTcxNjA4NjQwMH0.assinatura",
  "tipo": "Bearer",
  "expiresIn": 86400000
}
```

---

### Listar serviços com busca parcial

**Request:**
```http
GET /api/servicos?nome=rg
```

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "nome": "Emissão de RG",
    "descricao": "Serviço de emissão da Carteira de Identidade (RG) para cidadãos brasileiros.",
    "categoria": {
      "id": 2,
      "nome": "Documentação Civil"
    }
  },
  {
    "id": 8,
    "nome": "Segunda Via de RG",
    "descricao": "Solicitação de segunda via do documento de identidade em caso de perda ou roubo.",
    "categoria": {
      "id": 2,
      "nome": "Documentação Civil"
    }
  }
]
```

---

### Cadastrar serviço (ADMIN)

**Request:**
```http
POST /api/servicos
Authorization: Bearer eyJhbGci...
Content-Type: application/json

{
  "nome": "Vacinação contra Gripe",
  "descricao": "Aplicação gratuita da vacina contra influenza para grupos prioritários.",
  "categoriaId": 3
}
```

**Response `201 Created`:**
```json
{
  "id": 12,
  "nome": "Vacinação contra Gripe",
  "descricao": "Aplicação gratuita da vacina contra influenza para grupos prioritários.",
  "categoria": {
    "id": 3,
    "nome": "Saúde"
  }
}
```

---

### Submeter avaliação (USER)

**Request:**
```http
POST /api/avaliacoes
Authorization: Bearer eyJhbGci...
Content-Type: application/json

{
  "nota": 4,
  "comentario": "Atendimento rápido e organizado. Fila curta no período da manhã.",
  "servicoId": 1,
  "unidadeId": 5
}
```

**Response `201 Created`:**
```json
{
  "id": 33,
  "nota": 4,
  "comentario": "Atendimento rápido e organizado. Fila curta no período da manhã.",
  "usuario": {
    "id": 1,
    "nome": "João Silva"
  },
  "servico": {
    "id": 1,
    "nome": "Emissão de RG"
  },
  "unidade": {
    "id": 5,
    "nome": "Poupatempo Centro"
  },
  "criadoEm": "2024-05-18T14:32:00"
}
```

---

### Resposta de erro padronizada

**Response `403 Forbidden`:**
```json
{
  "timestamp": "2024-05-18T14:35:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Acesso negado: você não tem permissão para realizar esta operação.",
  "path": "/api/servicos"
}
```

**Response `404 Not Found`:**
```json
{
  "timestamp": "2024-05-18T14:36:00",
  "status": 404,
  "error": "Not Found",
  "message": "Serviço com id 99 não encontrado.",
  "path": "/api/servicos/99"
}
```

---

## 🌐 Estrutura Futura do Frontend Angular

O frontend ainda não está implementado. A API foi projetada para ser consumida por um cliente Angular moderno, seguindo esta estrutura planejada:
guia-serv-publico-frontend/
├── src/
│   ├── app/
│   │   ├── core/
│   │   │   ├── auth/           # Guards, Interceptors, AuthService
│   │   │   └── services/       # Serviços HTTP por domínio
│   │   ├── features/
│   │   │   ├── home/           # Página inicial com busca
│   │   │   ├── servicos/       # Listagem e detalhe de serviços
│   │   │   ├── unidades/       # Listagem e detalhe de unidades
│   │   │   ├── avaliacoes/     # Componente de avaliação
│   │   │   └── admin/          # Painel administrativo
│   │   ├── shared/
│   │   │   ├── components/     # Componentes reutilizáveis
│   │   │   └── models/         # Interfaces TypeScript (espelham os DTOs)
│   │   └── app.routes.ts       # Roteamento com Guards por perfil
│   └── environments/
│       ├── environment.ts       # API URL: http://localhost:8080
│       └── environment.prod.ts  # API URL de produção

---

## 🚧 Melhorias Futuras

- [ ] Paginação e ordenação em todas as listagens (`Pageable`)
- [ ] Cache com Spring Cache + Redis para buscas frequentes
- [ ] Upload de imagens para unidades de atendimento
- [ ] Geolocalização: busca de unidades por coordenadas
- [ ] Notificações por e-mail (JavaMailSender)
- [ ] Rate limiting por IP nas rotas públicas
- [ ] Refresh Token para renovação automática de sessão
- [ ] Perfil de produção com configurações otimizadas
- [ ] Pipeline CI/CD com GitHub Actions
- [ ] Testes de integração com Testcontainers
- [ ] Monitoramento com Spring Actuator + Prometheus + Grafana
- [ ] Deploy em cloud (Railway, Render ou AWS EC2)

---

## 🗺️ Roadmap
✅ Fase 1 — Fundação (Concluída)
✅ Setup do projeto Spring Boot 3 + Java 21
✅ Configuração de perfis (dev / test)
✅ Integração com PostgreSQL e H2
✅ Tratamento global de exceções
✅ Fase 2 — Autenticação e Segurança (Concluída)
✅ Registro e login com JWT
✅ Filtro de autenticação (JwtAuthFilter)
✅ Controle de acesso por roles (PUBLIC / USER / ADMIN)
✅ Endpoint /me
✅ Fase 3 — Domínio Principal (Concluída)
✅ CRUD de Categorias
✅ CRUD de Serviços com busca parcial
✅ CRUD de Unidades de Atendimento
✅ Vínculo Serviço ↔ Unidade
✅ Documentos por serviço
✅ Horários de funcionamento
✅ Avaliações de usuários
✅ Fase 4 — Documentação e Containerização (Concluída)
✅ Swagger / OpenAPI 3
✅ Dockerfile e Docker Compose
✅ README profissional
🔄 Fase 5 — Qualidade e Testes (Em andamento)
⬜ Testes unitários com JUnit 5 + Mockito
⬜ Testes de integração com Testcontainers
⬜ Cobertura mínima de 80% (JaCoCo)
⬜ Fase 6 — Frontend Angular
⬜ Setup do projeto Angular 17+
⬜ Integração com a API REST
⬜ Telas de busca, detalhe e avaliação
⬜ Painel administrativo
⬜ Fase 7 — Produção
⬜ Pipeline CI/CD (GitHub Actions)
⬜ Deploy em cloud
⬜ Monitoramento e alertas

---

## 🛡️ Segurança

O sistema implementa múltiplas camadas de proteção:

**Autenticação e Autorização**
- Tokens JWT assinados com HMAC-SHA256 e expiração configurável
- Senhas armazenadas com hash BCrypt (fator de custo 10)
- Controle de acesso baseado em roles via Spring Security
- Rotas protegidas por perfil no nível de cada endpoint

**Boas práticas de API**
- Dados sensíveis (senha, hash) nunca expostos nos DTOs de resposta
- Headers de segurança configurados (CORS, CSRF desabilitado para APIs stateless)
- Validação de entrada com Bean Validation (`@Valid`, `@NotBlank`, `@Email` etc.)
- Tratamento global de exceções evitando vazamento de stack traces

**Recomendações para produção**
- Utilizar variáveis de ambiente para credenciais (nunca hardcoded)
- Definir `JWT_SECRET` com no mínimo 512 bits de entropia
- Configurar HTTPS obrigatório (TLS 1.2+)
- Restringir origens permitidas no CORS para o domínio do frontend
- Ativar logs de auditoria para operações administrativas

---

## ✨ Boas Práticas Aplicadas

**Arquitetura e Design**
- Separação clara de responsabilidades entre as camadas (Controller → Service → Repository)
- DTOs distintos para request e response, sem exposição de entidades JPA na API
- Injeção de dependências via construtor (não via `@Autowired` em campo)
- Constantes e enums para valores fixos (roles, dias da semana etc.)

**Código**
- Lombok para eliminação de boilerplate (`@Getter`, `@Builder`, `@RequiredArgsConstructor`)
- Nomenclatura em português para o domínio (servico, unidade, avaliacao) e inglês para artefatos técnicos
- Tratamento de exceções customizadas com mensagens claras e padronizadas
- Respostas HTTP semânticas (201 Created, 204 No Content, 404 Not Found etc.)

**Banco de Dados**
- Relacionamentos mapeados corretamente com `@ManyToOne`, `@OneToMany`, `@ManyToMany`
- Perfis separados para ambiente de desenvolvimento (PostgreSQL) e testes (H2)
- Uso de `FetchType.LAZY` como padrão para evitar N+1 queries

**Documentação**
- Todos os endpoints documentados via Swagger/OpenAPI
- README completo com exemplos reais de uso
- Estrutura de projeto autoexplicativa

---

## 👨‍💻 Autor

<div align="center">

**Nicholas Focke**

Backend Developer · Java & Spring Boot

[![GitHub](https://img.shields.io/badge/GitHub-nicholasfocke-181717?style=for-the-badge&logo=github)](https://github.com/nicholasfocke)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-nicholas--focke-0A66C2?style=for-the-badge&logo=linkedin)](https://linkedin.com/in/nicholas-focke-833049269)

</div>
