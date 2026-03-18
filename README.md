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

- **Domain** (`br.com.gestrest.api.domain`)
  - Entidades de negócio
  - Exceções de domínio
  - Contratos de entrada/saída (`ports.in` e `ports.out`)

- **Application** (`br.com.gestrest.api.application`)
  - Casos de uso (implementações em `usecase.impl`)
  - Commands de entrada para casos de uso (`usecase.command`)
  - Orquestração das regras de negócio

- **Adapters/Infrastructure** (`br.com.gestrest.api.adapter` e `br.com.gestrest.api.config`)
  - Adapter In (Web): controllers, DTOs, mapper web, tratamento de exceções
  - Adapter Out (Persistence): entidades JPA, repositories Spring Data, mappers de persistência, adapters de repositório
  - Configurações de beans e wiring

Essa abordagem reduz o acoplamento entre as camadas e prepara o sistema para evolução futura.

---

## 🗂️ Estrutura de Pacotes da Aplicação

Abaixo está a estrutura principal de pacotes de produção em `src/main/java`:

```text
br.com.gestrest.api
├── adapter
│   ├── in
│   │   └── web
│   │       ├── controller
│   │       │   └── doc
│   │       ├── dto
│   │       │   ├── request
│   │       │   └── response
│   │       ├── exception
│   │       └── mapper
│   └── out
│       └── persistence
│           ├── entity
│           ├── mapper
│           └── repository
├── application
│   └── usecase
│       ├── command
│       │   └── usuario
│       └── impl
│           ├── itemcardapio
│           ├── restaurante
│           ├── tipousuario
│           └── usuario
├── config
└── domain
  ├── exception
  └── model
    └── ports
      ├── in
      │   ├── itemcardapio
      │   ├── restaurante
      │   ├── tipousuario
      │   └── usuario
      └── out
```

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

- 📌 Nome (string)
- 📝 Descrição (string)
- 💰 Preço (decimal com 2 casas)
- 🏠 Disponível apenas no local (boolean)
- 🖼️ Caminho da foto (string com máx. 255 caracteres)
- 🔗 Restaurante (ID do restaurante - obrigatório)

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

## � Regras de Negócio Implementadas

- **Tipo de Usuário:** Bloqueio de exclusão se houver usuários associados (409 Conflict)
- **Restaurante:** Apenas usuários com tipo DONO podem criar (403 Forbidden)
- **Restaurante:** Bloqueio de exclusão se houver itens no cardápio (409 Conflict)
- **Usuário:** E-mail e login únicos no sistema (409 Conflict em duplicidade)
- **Item de Cardápio:** Todos os campos (nome, descrição, preço, disponibilidade, foto, restaurante) são obrigatórios

---

## �🛠️ Stack Tecnológica

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
| `default` | Execução local via IDE (usa `src/main/resources/application.yaml`) |
| `docker` | Execução via Docker Compose (configurações específicas para ambiente docker; ativado no `docker-compose.yml` através da variável `SPRING_PROFILES_ACTIVE`) |
| `test` | Execução de testes automatizados com H2 (arquivo `application-test.yml`) |

Observação: o arquivo `application-docker.yml` está incluído no artefato construído e aparece em `target/classes/application-docker.yml` quando o projeto é empacotado; é esse arquivo que o profile `docker` utilizará dentro da imagem Docker.

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

O `docker-compose.yml` define o serviço de banco de dados (Postgres) e do aplicativo. Nota importante: o Compose ativa o profile `docker` para a aplicação (variável de ambiente `SPRING_PROFILES_ACTIVE: docker`) e também injeta as configurações de conexão com o banco via variáveis de ambiente (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`).

### 🚀 Após a inicialização

🌐 API disponível em: http://localhost:8080

🐘 PostgreSQL rodando em container dedicado

**Credenciais usadas via Docker Compose:**

- Usuário: `admin`
- Senha: `admin`

(O `docker-compose.yml` configura o banco com `POSTGRES_USER=admin` e `POSTGRES_PASSWORD=admin` e a aplicação usa as mesmas credenciais quando executada via Docker.)

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


**Ferramentas de teste utilizadas:**

- **JUnit 5** (Jupiter) — framework de execução e escrita de testes.
- **Mockito** — biblioteca de mocking para testes unitários (já disponível via `spring-boot-starter-test`).
- **JaCoCo** — plugin de cobertura configurado no `pom.xml` para gerar relatórios HTML durante a fase `test`.

**Comandos úteis relacionados a testes e cobertura:**

- Rodar todos os testes e gerar relatório JaCoCo:

```sh
./mvnw test
```

- Rodar apenas uma classe de teste:

```sh
./mvnw -Dtest=br.com.gestrest.api.application.usecase.impl.restaurante.CriarRestauranteUseCaseTest test
```

- Rodar apenas um método de teste:

```sh
./mvnw -Dtest=NomeDaClasseDeTeste#nomeDoMetodo test
```

**Local dos relatórios**

- Relatórios do Surefire (resultados dos testes): `target/surefire-reports/`
- Relatório JaCoCo (HTML): `target/site/jacoco/index.html`
- Arquivo binário de cobertura: `target/jacoco.exec`

**Nota sobre Mockito/ByteBuddy:** você pode ver avisos sobre agente dinâmico em alguns ambientes; se preferir, adicione `org.mockito:mockito-inline` como dependência de teste para habilitar o inline mock maker sem avisos.


### 📄 Documentação da API (Swagger)

Após iniciar a aplicação, a documentação interativa Swagger está disponível em:

http://localhost:8080/swagger-ui/index.html


### 📬 Coleção Postman

A coleção do Postman com exemplos de requisições está em:

docs/postman/gestrest_api_collection.json

A coleção inclui cenários positivos e negativos (ex.: tentativa de cadastro com email duplicado -> 409, criação de item para restaurante inexistente -> 404, tentativa de exclusão de restaurante com itens -> 409).


### 🗄️ **Banco de Dados**

* 🐘 **PostgreSQL**
* 🐳 Banco relacional executado via **Docker**
* 💾 Persistência de dados garantida através de **volumes Docker**
* 📊 Integração via Spring Data JPA e Hibernate


**Atenção às credenciais (diferença local vs docker):**

- Execução local (IDE / profile `default`): as configurações em `src/main/resources/application.yaml` apontam por padrão para um Postgres local com usuário `postgres` e senha `postgres` (URL: `jdbc:postgresql://localhost:5432/gestrest`).

- Execução via Docker Compose (profile `docker`): o Compose cria o banco com usuário `admin`/senha `admin` e injeta essas credenciais na aplicação. Verifique `docker-compose.yml` se quiser alterar esse comportamento.



## 🚀 Quickstart (cenário end-to-end)

1) Inicie a aplicação:

```bash
# Em desenvolvimento
./mvnw spring-boot:run

# via Docker Compose
docker compose up --build
```

2) Fluxo rápido via curl (criar tipo, criar usuário, criar restaurante, criar item):

```bash
curl -X POST -H "Content-Type: application/json" -d '{"nome":"Dono de Restaurante"}' http://localhost:8080/api/v1/tipos-usuarios

curl -X POST -H "Content-Type: application/json" -d '{"nome":"Rafael","email":"rafael@example.com","login":"rafael","senha":"senha123","tipoUsuarioId":1}' http://localhost:8080/api/v1/usuarios

curl -X POST -H "Content-Type: application/json" -d '{"nome":"Pizza House","endereco":"Av. Paulista","tipoCozinha":"Italiana","horarioFuncionamento":"11:00 - 23:00","donoId":1}' http://localhost:8080/api/v1/restaurantes

curl -X POST -H "Content-Type: application/json" -d '{"nome":"Pizza Margherita","descricao":"Molho de tomate","preco":45.50,"disponivelSomenteNoLocal":true,"fotoPath":"/imagens/itens/pizza-margherita.jpg","restauranteId":1}' http://localhost:8080/api/v1/itens-cardapio
```

## ⚠️ Formato padrão de erro (ErrorResponse)

Para todas as respostas de erro a API retorna um JSON padronizado com o seguinte formato:

```json
{
  "timestamp": "2026-03-07T00:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/usuarios",
  "errors": [
    { "field": "email", "message": "Email deve ser válido" }
  ]
}
```

Códigos esperados:
- 400 Bad Request -> erros de validação DTO
- 403 Forbidden -> operação não permitida pela regra de negócio (ex: apenas DONO pode criar restaurante)
- 404 Not Found -> recurso não encontrado
- 409 Conflict -> conflito de negócio (ex.: email duplicado, recurso em uso)
- 500 Internal Server Error -> erro genérico



### 📝 **Observações Finais**

* 💡 Projeto estruturado com Clean Architecture

* 📂 Código organizado e preparado para crescimento

* 🧪 Base pronta para cobertura mínima de 80% de testes

* 🛡️ Estrutura preparada para futuras fases (segurança, pedidos, avaliações)


**Projeto desenvolvido como parte do Tech Challenge – Fase 02** 🎓 **Curso de Pós-Graduação – FIAP**

### 👤 **Autor**

* Rafael Mendonça de Brito (RM369933)