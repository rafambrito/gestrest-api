# 📚 Documentação da API REST - GestRest API

## Visão Geral

A **GestRest API** é uma aplicação RESTful desenvolvida em Spring Boot para gerenciamento de restaurantes, usuários e cardápios. A documentação técnica está organizada de acordo com as camadas de arquitetura Clean Architecture e segue os padrões OpenAPI 3.0 (Swagger).

---

## 📖 Estrutura de Documentação

### Interfaces de Documentação (Controllers Doc)

Cada controller possui uma interface correspondente no pacote `br.com.gestrest.api.adapter.in.web.controller.doc` que define a documentação técnica completa de cada endpoint:

#### 1. **TipoUsuarioControllerDoc**
- **Localização:** `/api/v1/tipos-usuarios`
- **Responsabilidade:** Gerenciamento de tipos de usuários (Cliente, Dono de Restaurante)
- **Endpoints:** 5 operações CRUD

#### 2. **UsuarioControllerDoc**
- **Localização:** `/api/v1/usuarios`
- **Responsabilidade:** Gerenciamento de usuários do sistema
- **Endpoints:** 5 operações CRUD

#### 3. **RestauranteControllerDoc**
- **Localização:** `/api/v1/restaurantes`
- **Responsabilidade:** Gerenciamento de restaurantes
- **Endpoints:** 5 operações CRUD

#### 4. **ItemCardapioControllerDoc**
- **Localização:** `/api/v1/itens-cardapio`
- **Responsabilidade:** Gerenciamento de itens do cardápio
- **Endpoints:** 5 operações (CRUD + listar por restaurante)

---

## 🎯 Padrão de Documentação

Cada método em uma interface `*ControllerDoc` implementa:

### 1. Anotação `@Operation`
Define a operação da API com:
- `summary`: Título breve do endpoint
- `description`: Descrição detalhada da funcionalidade
- `tags`: Categorias para agrupamento

### 2. Anotação `@ApiResponses`
Define as respostas possíveis com:
- Código HTTP (200, 201, 400, 404, 409, etc)
- Descrição do status
- Exemplos JSON de resposta
- Esquema de dados

### 3. Anotação `@Parameter`
Define parâmetros de entrada com:
- `description`: Descrição do parâmetro
- `required`: Se é obrigatório
- `example`: Valor de exemplo

### 4. Anotação `@RequestBody`
Define o corpo da requisição com:
- `description`: Descrição do conteúdo
- `required`: Se é obrigatório
- `content`: Tipo MIME e schema
- Exemplos de payload

---

## 🔗 Implementação nos Controllers

Cada controller implementa sua interface de documentação:

```java
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerDoc {
    // implementação dos métodos
}
```

Dessa forma, a documentação fica automaticamente associada aos endpoints.

---

## 📊 Exemplos de Endpoints

### 1. Criar Tipo de Usuário
```
POST /api/v1/tipos-usuarios
Content-Type: application/json

{
  "nome": "Cliente"
}

Resposta 201:
{
  "id": 1,
  "nome": "Cliente"
}
```

### 2. Criar Usuário
```
POST /api/v1/usuarios
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "login": "joao123",
  "senha": "senha123",
  "endereco": "Rua ABC 123",
  "tipoUsuarioId": 1
}

Resposta 201:
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "login": "joao123",
  "endereco": "Rua ABC 123",
  "tipoUsuario": {
    "id": 1,
    "nome": "Cliente"
  }
}
```

### 3. Criar Restaurante
```
POST /api/v1/restaurantes
Content-Type: application/json

{
  "nome": "Pizza House",
  "endereco": "Rua das Flores 123",
  "tipoCozinha": "Italiana",
  "horarioFuncionamento": "11:00 - 22:00",
  "donoId": 1
}

Resposta 201:
{
  "id": 1,
  "nome": "Pizza House",
  "endereco": "Rua das Flores 123",
  "tipoCozinha": "Italiana",
  "horarioFuncionamento": "11:00 - 22:00",
  "donoId": 1
}
```

### 4. Criar Item do Cardápio
```
POST /api/v1/itens-cardapio
Content-Type: application/json

{
  "nome": "Pizza Margherita",
  "descricao": "Pizza clássica com mozzarela e tomate",
  "preco": 45.50,
  "restauranteId": 1
}

Resposta 201:
{
  "id": 1,
  "nome": "Pizza Margherita",
  "descricao": "Pizza clássica com mozzarela e tomate",
  "preco": 45.50,
  "restauranteId": 1
}
```

---

## 🛠️ Como Acessar a Documentação Interativa

A documentação interativa via Swagger UI está disponível em:

```
http://localhost:8080/swagger-ui.html
```

Através dessa interface, você pode:
- Visualizar todos os endpoints
- Ver exemplos de requisição e resposta
- Executar testes diretos na API
- Explorar os schemas de dados

---

## 📝 Código HTTP Status

| Status | Significado | Caso de Uso |
|--------|-------------|-----------|
| 200 | OK | GET, PUT bem-sucedido |
| 201 | Created | POST bem-sucedido |
| 204 | No Content | DELETE bem-sucedido |
| 400 | Bad Request | Validação falhou |
| 404 | Not Found | Recurso não encontrado |
| 409 | Conflict | Conflito (FK, duplicate, etc) |
| 500 | Server Error | Erro interno do servidor |

---

## ✅ Validações por Endpoint

### Tipos de Usuário
- ✓ Nome obrigatório e não vazio
- ✓ Sem duplicatas

### Usuários
- ✓ Nome obrigatório
- ✓ Email válido e único
- ✓ Login único
- ✓ Senha obrigatória
- ✓ Tipo de usuário deve existir

### Restaurantes
- ✓ Nome obrigatório
- ✓ Endereço obrigatório
- ✓ Tipo de cozinha obrigatório
- ✓ Horário de funcionamento obrigatório
- ✓ Dono (usuário) deve existir

### Itens do Cardápio
- ✓ Nome obrigatório
- ✓ Preço > 0
- ✓ Restaurante deve existir
- ✓ Descrição opcional

---

## 🔐 Segurança (Fase Futura)

Os endpoints estão preparados para implementação de segurança através de:
- Spring Security
- JWT (JSON Web Tokens)
- Role-based access control (RBAC)

---

## 📦 Integração com Ferramentas

### Postman
Collection disponível em: `/docs/postman/gestrest-api.postman_collection.json`

### Curl (Exemplos)

**Criar Tipo de Usuário:**
```bash
curl -X POST http://localhost:8080/api/v1/tipos-usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome": "Cliente"}'
```

**Listar Usuários:**
```bash
curl -X GET http://localhost:8080/api/v1/usuarios
```

**Deletar Restaurante:**
```bash
curl -X DELETE http://localhost:8080/api/v1/restaurantes/1
```

---

## 🔄 Fluxos de Negócio

### 1. Cadastro de Novo Restaurante
1. Criar Tipo de Usuário (se não existir)
2. Criar Usuário (tipo: Dono de Restaurante)
3. Criar Restaurante (associar ao usuário)
4. Adicionar Itens ao Cardápio

### 2. Buscar Cardápio de um Restaurante
1. GET `/api/v1/restaurantes/{id}`
2. GET `/api/v1/itens-cardapio/restaurante/{restauranteId}`

---

## 📞 Suporte e Contribuição

Para dúvidas ou sugestões sobre a documentação, abra uma issue no repositório do projeto.

---

**Última atualização:** 16 de Fevereiro de 2026  
**Versão da API:** v1  
**Status:** ✅ Produção
