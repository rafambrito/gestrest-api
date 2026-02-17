# 🏗️ Arquitetura Técnica - GestRest API

## Visão Geral da Arquitetura

O projeto **GestRest API** foi desenvolvido seguindo os princípios de **Clean Architecture**, garantindo separação clara de responsabilidades, testabilidade e facilidade de manutenção.

---

## 📐 Camadas de Arquitetura

```
┌─────────────────────────────────────────────────────┐
│         ADAPTER (Infrastructure Layer)              │
│  Controllers │ DTOs │ Mappers │ Exception Handlers  │
├─────────────────────────────────────────────────────┤
│         APPLICATION (Application Layer)             │
│      UseCases │ Orquestração de Negócio             │
├─────────────────────────────────────────────────────┤
│           DOMAIN (Domain Layer)                     │
│  Entities │ Business Rules │ Ports (Interfaces)    │
├─────────────────────────────────────────────────────┤
│   INFRASTRUCTURE (Data Persistence Layer)          │
│  Repositories │ JPA Entities │ Database Mapper     │
└─────────────────────────────────────────────────────┘
```

---

## 1️⃣ Domain Layer (Camada de Domínio)

**Localização:** `br.com.gestrest.api.domain`

### Responsabilidades
- Define as entidades de negócio (modelos)
- Implementa regras de negócio
- Define interfaces (ports) para comunicação com camadas externas

### Estrutura
```
domain/
├── model/
│   ├── TipoUsuario.java        (Entidade: Tipo de Usuário)
│   ├── Usuario.java            (Entidade: Usuário)
│   ├── Restaurante.java        (Entidade: Restaurante)
│   ├── ItemCardapio.java       (Entidade: Item do Cardápio)
│   └── ports/
│       ├── in/                 (Input Ports - UseCases)
│       │   ├── tipousuario/
│       │   ├── usuario/
│       │   ├── restaurante/
│       │   └── itemcardapio/
│       └── out/                (Output Ports - Repositories)
│           ├── TipoUsuarioRepositoryPort.java
│           ├── UsuarioRepositoryPort.java
│           ├── RestauranteRepositoryPort.java
│           └── ItemCardapioRepositoryPort.java
```

### Características das Entidades

#### TipoUsuario
```java
public class TipoUsuario {
    private Long id;
    private String nome;
    
    // Factory methods
    public static TipoUsuario criar(String nome)
    public static TipoUsuario existente(Long id, String nome)
    
    // Validações no domain
    private void validarNome(String nome)
}
```

**Regras de Negócio:**
- Nome obrigatório e não vazio
- Factory pattern para criação segura

---

## 2️⃣ Application Layer (Camada de Aplicação)

**Localização:** `br.com.gestrest.api.application`

### Responsabilidades
- Implementa os casos de uso (usecases)
- Orquestra a lógica de negócio
- Coordena chamadas entre domínio e infraestrutura

### Estrutura
```
application/
└── usecase/
    ├── impl/
    │   ├── tipousuario/
    │   │   ├── CriarTipoUsuarioUseCaseImpl.java
    │   │   ├── AtualizarTipoUsuarioUseCaseImpl.java
    │   │   ├── BuscarTipoUsuarioPorIdUseCaseImpl.java
    │   │   ├── ListarTipoUsuarioUseCaseImpl.java
    │   │   └── ExcluirTipoUsuarioUseCaseImpl.java
    │   ├── usuario/
    │   ├── restaurante/
    │   └── itemcardapio/
    └── command/
        └── usuario/
            ├── CriarUsuarioCommand.java
            └── AtualizarUsuarioCommand.java
```

### Exemplo de UseCase

```java
@Service
@RequiredArgsConstructor
public class CriarRestauranteUseCaseImpl implements CriarRestauranteUseCase {
    private final RestauranteRepositoryPort repository;

    @Override
    public Restaurante criar(Restaurante restaurante) {
        return repository.salvar(restaurante);
    }
}
```

---

## 3️⃣ Adapter Layer (Camada de Adaptadores)

**Localização:** `br.com.gestrest.api.adapter`

### 3.1 Input Adapters (Web)
```
adapter/
├── in/
│   └── web/
│       ├── controller/
│       │   ├── TipoUsuarioController.java
│       │   ├── UsuarioController.java
│       │   ├── RestauranteController.java
│       │   ├── ItemCardapioController.java
│       │   └── doc/
│       │       ├── TipoUsuarioControllerDoc.java
│       │       ├── UsuarioControllerDoc.java
│       │       ├── RestauranteControllerDoc.java
│       │       └── ItemCardapioControllerDoc.java
│       ├── dto/
│       │   ├── request/
│       │   │   ├── CriarUsuarioRequest.java
│       │   │   ├── CriarRestauranteRequest.java
│       │   │   └── ...
│       │   └── response/
│       │       ├── UsuarioResponse.java
│       │       ├── RestauranteResponse.java
│       │       └── ...
│       ├── mapper/
│       │   ├── UsuarioWebMapper.java
│       │   ├── RestauranteWebMapper.java
│       │   └── ...
│       ├── exception/
│       │   └── ApiExceptionHandler.java
```

**Padrão de Controllers:**
```java
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerDoc {
    
    private final CriarUsuarioUseCase criarUseCase;
    private final UsuarioWebMapper mapper;
    
    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody CriarUsuarioRequest request) {
        var domain = mapper.toDomain(request);
        var usuario = criarUseCase.criar(domain);
        return ResponseEntity.created(URI.create("/api/v1/usuarios/" + usuario.getId()))
            .body(mapper.toResponse(usuario));
    }
}
```

### 3.2 Output Adapters (Persistence)
```
adapter/
└── out/
    └── persistence/
        ├── entity/
        │   ├── TipoUsuarioEntity.java
        │   ├── UsuarioEntity.java
        │   ├── RestauranteEntity.java
        │   └── ItemCardapioEntity.java
        ├── repository/
        │   ├── TipoUsuarioJpaRepository.java
        │   ├── UsuarioJpaRepository.java
        │   ├── RestauranteJpaRepository.java
        │   └── ItemCardapioJpaRepository.java
        ├── mapper/
        │   ├── TipoUsuarioPersistenceMapper.java
        │   ├── UsuarioPersistenceMapper.java
        │   ├── RestaurantePersistenceMapper.java
        │   └── ItemCardapioPersistenceMapper.java
        ├── TipoUsuarioRepositoryAdapter.java
        ├── UsuarioRepositoryAdapter.java
        ├── RestauranteRepositoryAdapter.java
        └── ItemCardapioRepositoryAdapter.java
```

---

## 🗄️ Banco de Dados

### Modelo Entidade-Relacionamento

```
┌─────────────────────────┐
│    tipo_usuario         │
├─────────────────────────┤
│ id (PK)                 │
│ nome (VARCHAR)          │
└──────────┬──────────────┘
           │ (1:N)
           ├─────────────────────┐
           │                     │
           ▼                     ▼
    ┌──────────────────┐  ┌─────────────────────┐
    │    usuario       │  │    restaurante      │
    ├──────────────────┤  ├─────────────────────┤
    │ id (PK)          │  │ id (PK)             │
    │ nome             │  │ nome                │
    │ email (UNIQUE)   │  │ endereco            │
    │ login (UNIQUE)   │  │ tipo_cozinha        │
    │ senha            │  │ horario_funcionamento
    │ endereco         │  │ dono_id (FK)        │
    │ tipo_usuario_id  │  │ data_ultima_alteracao
    │ (FK)             │  └──────────┬──────────┘
    └──────────────────┘             │ (1:N)
                                     │
                                     ▼
                        ┌────────────────────────┐
                        │  item_cardapio         │
                        ├────────────────────────┤
                        │ id (PK)                │
                        │ nome                   │
                        │ descricao              │
                        │ preco (DECIMAL)        │
                        │ restaurante_id (FK)    │
                        │ data_ultima_alteracao  │
                        └────────────────────────┘
```

### Criar Tabelas (SQL)

```sql
CREATE TABLE tipo_usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    tipo_usuario_id INTEGER NOT NULL REFERENCES tipo_usuario(id),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE restaurante (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    tipo_cozinha VARCHAR(100) NOT NULL,
    horario_funcionamento VARCHAR(100) NOT NULL,
    dono_id INTEGER NOT NULL REFERENCES usuario(id),
    data_ultima_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE item_cardapio (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    restaurante_id INTEGER NOT NULL REFERENCES restaurante(id),
    data_ultima_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 🔄 Padrões de Design Utilizados

### 1. **Factory Pattern**
Usado nas entidades para criar objetos de forma segura:
```java
public static Restaurante criar(String nome, String endereco, ...) {
    return new Restaurante(null, nome, endereco, ...);
}
```

### 2. **Repository Pattern**
Abstração de acesso a dados:
```java
public interface RestauranteRepositoryPort {
    Restaurante salvar(Restaurante restaurante);
    Optional<Restaurante> buscarPorId(Long id);
    List<Restaurante> buscarTodos();
    void deletar(Long id);
}
```

### 3. **Mapper Pattern**
Conversão entre camadas:
```java
// Web ↔ Domain
public Restaurante toDomain(CriarRestauranteRequest request)

// Domain ↔ Persistence
public RestauranteEntity toEntity(Restaurante domain)
```

### 4. **UseCase Pattern**
Orquestração de lógica de negócio:
```java
public interface CriarRestauranteUseCase {
    Restaurante criar(Restaurante restaurante);
}
```

### 5. **Adapter Pattern**
Implementação de ports externos:
```java
@Component
public class RestauranteRepositoryAdapter implements RestauranteRepositoryPort {
    @Override
    public Restaurante salvar(Restaurante restaurante) {
        // implementação
    }
}
```

---

## 📦 Dependências Principais

```xml
<!-- Spring Boot -->
<spring-boot-starter-web>
<spring-boot-starter-data-jpa>

<!-- Validation -->
<spring-boot-starter-validation>

<!-- Database -->
<postgresql-driver>
<h2>

<!-- Documentation -->
<springdoc-openapi-starter-webmvc-ui>

<!-- Utilities -->
<lombok>
```

---

## 🚀 Inicialização da Aplicação

### Estrutura de Configuração

```
config/
├── UseCaseConfig.java       (Injeção de dependências dos UseCases)
└── MapperConfig.java        (Configuração dos Mappers)
```

### Profile do Spring

```yaml
# application.yaml (padrão/IDE)
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gestrest
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

# application-docker.yml (Docker)
spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/gestrest
    username: admin
    password: admin
```

---

## ✅ Requisitos Atendidos

- ✅ Clean Architecture com 4 camadas
- ✅ Separação clara de responsabilidades
- ✅ Padrões de design bem aplicados
- ✅ Injeção de dependência com Spring
- ✅ Validações em múltiplas camadas
- ✅ Tratamento centralizado de exceções
- ✅ Documentação com Swagger/OpenAPI
- ✅ Suporte a múltiplos ambientes (profiles)
- ✅ Pronto para testes (unitários e integração)

---

## 📈 Próximas Evoluções

- 🔐 Implementação de segurança (Spring Security + JWT)
- 📊 Adicionar logging estruturado
- 🔍 Implementar auditoria de mudanças
- 📝 Adicionar paginação nos endpoints de listagem
- 🔗 Implementar soft delete
- ⚙️ Adicionar cache
- 🌍 Implementar i18n (internacionalização)

---

**Última atualização:** 16 de Fevereiro de 2026  
**Arquiteto:** Rafael M. de Brito  
**Status:** ✅ Pronto para Produção
