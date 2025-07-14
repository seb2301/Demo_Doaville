# DoaVille - Plataforma de Doações Sustentáveis

**API REST para gestão de doações, solicitações e usuários, promovendo o consumo consciente e o reaproveitamento de itens.**

---

## ✍️ Autor

Desenvolvido por **Sebastião Soares da Silva Filho**

- [LinkedIn](https://www.linkedin.com/in/sebfullstack/)
- [GitHub](https://github.com/seb2301)

---

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security + JWT**
- **PostgreSQL**
- **Maven**
- **Jakarta Bean Validation**
- **JUnit 5 + Mockito** (Testes automatizados)
- **Jacoco** (Relatórios de cobertura de testes)
- **Swagger/OpenAPI** (Documentação da API)

---

## 📚 Funcionalidades

- **CRUD de Itens de Doação:** Cadastro, edição, remoção e listagem de itens disponíveis para doação.
- **CRUD de Usuários (ADMIN):** Gestão completa dos usuários do sistema (função restrita ao administrador).
- **Solicitação de Doações:** Usuários podem solicitar itens, registrar e acompanhar suas solicitações.
- **Filtro de Solicitações por Item:** Visualize solicitações relacionadas a um item específico.
- **Autenticação e Autorização:** Controle de acesso robusto usando JWT, com perfis ADMIN e USER.
- **Validação e Tratamento de Erros:** Validação automática de dados de entrada e tratamento global de exceções.
- **Cobertura de Testes:** Projeto com testes unitários e de integração para garantir robustez e qualidade.
- **Documentação Automática:** API documentada via Swagger para fácil consulta e testes.

---

## 🏗️ Como rodar localmente

### 1. Clone o repositório

```bash
git clone https://github.com/seb2301/doaville-api.git
cd doaville-api
```

### 2. Configure o banco de dados

Crie um banco de dados PostgreSQL e configure as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/doaville
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

> 💡 **Dica:** Você pode usar Railway, Render ou ElephantSQL para subir um banco em nuvem se preferir.

### 3. Compile e execute a aplicação

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em:  
[http://localhost:8080](http://localhost:8080)

### 4. Acesse a documentação da API

Com o projeto rodando, acesse:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧑‍💻 Testes Automatizados

Execute todos os testes com:

```bash
mvn test
```

Relatórios de cobertura Jacoco estarão em `target/site/jacoco/index.html`

---

## 🔒 Segurança

- **JWT:** Rotas protegidas exigem token JWT válido.

**Perfis de acesso:**

- `ADMIN` - acesso total
- `USER` - acesso restrito

Para acessar rotas protegidas, obtenha um token via endpoint de autenticação e envie no header:

```http
Authorization: Bearer <token>
```

---

## 🗂️ Estrutura do Projeto

```text
src/
 ├─ main/
 │   ├─ java/
 │   │   └─ com/
 │   │       └─ doaville/
 │   │           ├─ controller/
 │   │           ├─ service/
 │   │           ├─ repository/
 │   │           ├─ dto/
 │   │           ├─ entity/
 │   │           ├─ security/
 │   │           ├─ exception/
 │   │           └─ config/
 │   └─ resources/
 │       └─ application.properties
 └─ test/
     └─ java/
         └─ com/
             └─ doaville/
```

---

## 📝 Contribuição

- Faça um fork do projeto
- Crie uma branch para sua feature (`git checkout -b feature/nome`)
- Commit suas alterações (`git commit -am 'feat: minha feature'`)
- Push para a branch (`git push origin feature/nome`)
- Crie um Pull Request

---

## 📧 Contato

Em caso de dúvidas, sugestões ou colaboração, entre em contato:

- [LinkedIn](https://www.linkedin.com/in/sebfullstack/)
- [GitHub](https://github.com/seb2301)
- Email: sebastiao.filho2301@gmail.com

