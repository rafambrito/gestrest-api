# 🍴 **GestRest API – Sistema de Gestão de Restaurantes** 

**Fase 2 – Tech Challenge - FIAP Pós Tech – Arquitetura e Desenvolvimento em JAVA**

---

## 📌 Sobre o Projeto

O **GestRest API** é uma aplicação **RESTful** desenvolvida em **Spring Boot** para gestão de usuários de um sistema compartilhado entre restaurantes. O sistema permite o cadastro e gerenciamento de clientes e donos de restaurante, conforme os requisitos do Tech Challenge da Fase 02.

Nesta etapa, o foco está na **gestão de tipos de usuários, restaurantes e itens de cardápio**, além do fortalecimento da arquitetura, testes automatizados e infraestrutura com Docker.

A aplicação segue princípios de **Clean Architecture**, boas práticas do Spring Boot, princípios **SOLID**, versionamento de API e execução padronizada via **Docker Compose**, utilizando **PostgreSQL** como banco de dados relacional.

---

## 🎯 Objetivo da Fase 2

Desenvolver um backend que permita:

- ✅ Gerenciamento de Tipos de Usuário
- ✅ Associação de usuários a seus respectivos tipos
- ✅ Cadastro e gestão de Restaurantes
- ✅ Associação de restaurantes a seus donos
- ✅ Cadastro e gestão de Itens de Cardápio
- ✅ Persistência em banco de dados relacional
- ✅ Testes unitários e de integração
- ✅ Execução padronizada via Docker

---

## 🏗️ Arquitetura

A aplicação foi estruturada seguindo os princípios da **Clean Architecture**, garantindo separação de responsabilidades, testabilidade e facilidade de manutenção.

### Camadas do sistema:

- **Domain**
  - Entidades de negócio
  - Interfaces de repositórios
  - Regras de domínio

- **Application**
  - Casos de uso
  - DTOs
  - Orquestração das regras de negócio

- **Infrastructure**
  - Controllers REST
  - Implementações de repositórios (JPA)
  - Configurações
  - Persistência

Essa abordagem reduz o acoplamento entre as camadas e prepara o sistema para evolução futura.

---

## 👥 Tipos de Usuário

O sistema contempla os seguintes tipos:

- 👨‍🍳 Dono de Restaurante
- 👤 Cliente

Cada usuário é associado a um tipo específico, permitindo controle e diferenciação de responsabilidades no sistema.

---

## 🏪 Restaurante

Campos obrigatórios do cadastro de restaurante:

- 📌 Nome
- 📍 Endereço
- 🍽️ Tipo de cozinha
- ⏰ Horário de funcionamento
- 👤 Dono do restaurante (usuário associado)

---

## 🍽️ Itens do Cardápio

Campos obrigatórios dos itens do cardápio:

- 📌 Nome
- 📝 Descrição
- 💰 Preço
- 🏠 Disponibilidade apenas para consumo no local
- 🖼️ Caminho da imagem do prato

---

## 🚀 Funcionalidades Implementadas

- ✨ CRUD de Tipos de Usuário
- ✨ CRUD de Restaurantes
- ✨ CRUD de Itens de Cardápio
- 🔗 Associação entre usuários e restaurantes
- 📌 Versionamento da API (`/api/v1`)
- ⚠️ Tratamento centralizado de erros
- 🧪 Testes unitários e de integração
- 🐳 Infraestrutura Docker com Docker Compose

---

## 🛠️ Stack Tecnológica

- ☕ Java 21
- 🍃 Spring Boot 3.4.1
- 📊 Spring Data JPA
- ✔️ Spring Validation
- 🐘 PostgreSQL
- 🐳 Docker e Docker Compose
- 🧪 H2 (ambiente de testes)
- 🧰 Maven
- ✂️ Lombok

---

## ⚙️ Perfis de Execução

A aplicação utiliza **profiles do Spring** para separar configurações por ambiente:

| Profile | Descrição |
|------|----------|
| `default` | Execução local via IDE |
| `docker` | Execução via Docker Compose |
| `test` | Execução de testes automatizados com H2 |

---

## 📦 Execução com Docker

### 📋 Pré-requisitos

- Docker
- Docker Compose

### ⚙️ Subindo a aplicação

Na raiz do projeto, execute:

```bash
docker compose up --build
```

### 🚀 Após a inicialização

🌐 API disponível em: http://localhost:8080

🐘 PostgreSQL rodando em container dedicado

### 🧪 Testes Automatizados

Os testes utilizam:

Banco H2 em memória

Profile test

Execução independente de infraestrutura externa

```
mvn clean test
```

```
mvn clean install
```


### 🗄️ **Banco de Dados**

* 🐘 **PostgreSQL**
* 🐳 Banco relacional executado via **Docker**
* 💾 Persistência de dados garantida através de **volumes Docker**
* 📊 Integração via Spring Data JPA e Hibernate

### 📝 **Observações Finais**

* 💡 Projeto estruturado com Clean Architecture

* 📂 Código organizado e preparado para crescimento

* 🧪 Base pronta para cobertura mínima de 80% de testes

* 🛡️ Estrutura preparada para futuras fases (segurança, pedidos, avaliações)


**Projeto desenvolvido como parte do Tech Challenge – Fase 02** 🎓 **Curso de Pós-Graduação – FIAP**

### 👥 **Autor**

* Rafael Mendonça de Brito (RM369933)


