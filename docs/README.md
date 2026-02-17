# 📚 Documentação - GestRest API

Bem-vindo à documentação completa da **GestRest API**! Aqui você encontrará todas as informações necessárias para entender, integrar e estender a aplicação.

---

## 📖 Documentos Disponíveis

### 1. 📐 **[ARCHITECTURE.md](./ARCHITECTURE.md)**
Documentação técnica completa da arquitetura do projeto.

**O que você encontrará:**
- Visão geral de Clean Architecture
- Descrição detalhada de cada camada (Domain, Application, Adapter, Infrastructure)
- Estrutura de diretórios
- Padrões de design utilizados (Factory, Repository, Mapper, UseCase, Adapter)
- Modelo de dados (ER Diagram, SQL)
- Dependências principais
- Fluxo de inicialização

**Para quem é:** Arquitetos, desenvolvedores senior, pessoal de DevOps

---

### 2. 📡 **[API_DOCUMENTATION.md](./API_DOCUMENTATION.md)**
Guia completo para integração com a API REST.

**O que você encontrará:**
- Estrutura de documentação (Interfaces de Controllers)
- Padrão de anotações Swagger/OpenAPI
- Exemplos de requisição/resposta para cada endpoint
- Código HTTP status codes
- Validações por entidade
- Fluxos de negócio
- Integração com Postman e Curl

**Para quem é:** Desenvolvedores frontend, integradores, QA

---

### 3. 🚀 **[SETUP.md](./SETUP.md)** *(Próximo documento)*
Guia de configuração e deployment.

---

## 🔗 Endpoints da API

| Recurso | Método | Endpoint | Descrição |
|---------|--------|----------|-----------|
| **Tipo de Usuário** | | | |
| | POST | `/api/v1/tipos-usuarios` | Criar novo tipo |
| | GET | `/api/v1/tipos-usuarios/{id}` | Buscar por ID |
| | GET | `/api/v1/tipos-usuarios` | Listar todos |
| | PUT | `/api/v1/tipos-usuarios/{id}` | Atualizar |
| | DELETE | `/api/v1/tipos-usuarios/{id}` | Deletar |
| **Usuário** | | | |
| | POST | `/api/v1/usuarios` | Criar novo usuário |
| | GET | `/api/v1/usuarios/{id}` | Buscar por ID |
| | GET | `/api/v1/usuarios` | Listar todos |
| | PUT | `/api/v1/usuarios/{id}` | Atualizar |
| | DELETE | `/api/v1/usuarios/{id}` | Deletar |
| **Restaurante** | | | |
| | POST | `/api/v1/restaurantes` | Criar novo restaurante |
| | GET | `/api/v1/restaurantes/{id}` | Buscar por ID |
| | GET | `/api/v1/restaurantes` | Listar todos |
| | PUT | `/api/v1/restaurantes/{id}` | Atualizar |
| | DELETE | `/api/v1/restaurantes/{id}` | Deletar |
| **Item do Cardápio** | | | |
| | POST | `/api/v1/itens-cardapio` | Criar novo item |
| | GET | `/api/v1/itens-cardapio/{id}` | Buscar por ID |
| | GET | `/api/v1/itens-cardapio/restaurante/{restauranteId}` | Listar por restaurante |
| | PUT | `/api/v1/itens-cardapio/{id}` | Atualizar |
| | DELETE | `/api/v1/itens-cardapio/{id}` | Deletar |

**Total: 20 endpoints**

---

## 🎯 Guias Rápidos

### Para Desenvolvedores
1. Leia [ARCHITECTURE.md](./ARCHITECTURE.md) para entender a estrutura
2. Consulte [API_DOCUMENTATION.md](./API_DOCUMENTATION.md) para os endpoints
3. Explore o código-fonte em `src/main/java`

### Para DevOps/SysAdmin
1. Leia o [README.md](../README.md) da raiz do projeto
2. Configure Docker Compose conforme descrito
3. Acompanhe os logs em `target/`

### Para QA/Testes
1. Use o [Postman Collection](./postman/) para testar endpoints
2. Consulte [API_DOCUMENTATION.md](./API_DOCUMENTATION.md) para validações
3. Execute `mvn test` para testes automatizados

---

## 🌐 Acessar Swagger UI

Quando a aplicação está rodando localmente:

```
http://localhost:8080/swagger-ui.html
```

Lá você pode:
- Visualizar todos os endpoints interativamente
- Ver esquemas de requisição/resposta
- Testar endpoints em tempo real
- Explorar validações

---

## 📊 Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.4.1
- **ORM:** JPA/Hibernate
- **Banco de Dados:** PostgreSQL (produção) | H2 (testes)
- **Container:** Docker & Docker Compose
- **Documentação:** OpenAPI 3.0 / Swagger
- **Build:** Maven
- **Padrão Arquitetural:** Clean Architecture

---

## 🔐 Segurança (Roadmap)

- [ ] Autenticação com JWT
- [ ] Autorização com Spring Security
- [ ] Rate limiting
- [ ] CORS configuração
- [ ] Helmet headers

---

## 📞 Suporte

Para dúvidas sobre a documentação:

1. Verifique os documentos listados acima
2. Explore o código-fonte com comentários
3. Consulte a seção de exemplos nos documentos
4. Abra uma issue no repositório

---

## ✅ Checklist de Documentação

- ✅ Arquitetura técnica documentada
- ✅ API REST documentada com Swagger
- ✅ Padrões de design explicados
- ✅ Exemplos de uso
- ✅ Fluxos de negócio
- ⏳ Guias de deployment (em breve)
- ⏳ Guias de contribuição (em breve)
- ⏳ FAQ (em breve)

---

## 📈 Versionamento

- **Versão da API:** v1
- **Data:** 16 de Fevereiro de 2026
- **Status:** 🟢 Documentação Completa

---

**Última atualização:** 16 de Fevereiro de 2026
