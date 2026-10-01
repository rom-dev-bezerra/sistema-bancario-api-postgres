# 🏦 Sistema Bancário API — Edição PostgreSQL & Docker

An API RESTful para um sistema bancário desenvolvida em Java com Spring Boot. Este projeto representa a **terceira evolução** da arquitetura do sistema no meu portfólio, focando em persistência robusta, conteinerização de infraestrutura e isolamento de ambientes de banco de dados.

## 🚀 A Linha de Evolução do Projeto
Para demonstrar meu crescimento técnico, a arquitetura deste sistema foi construída em três etapas distintas de evolução:
1. **Versão 1.0 (Memória Local):** Armazenamento de dados baseado em estruturas de dados em memória (`Lists`/`Maps`). Foco inicial nas regras de negócio de transações e contas.
2. **Versão 2.0 (H2 Database):** Migração para banco de dados relacional embarcado (H2). Introdução do Spring Data JPA e mapeamento objeto-relacional.
3. **Versão 3.0 (Esta Versão - Postgres + Docker):** **Evolução atual.** Substituição do banco embarcado pelo **PostgreSQL** real de produção, com toda a infraestrutura de banco de dados conteinerizada utilizando **Docker** e **Docker Compose** para garantir ambientes idênticos em qualquer máquina de desenvolvimento.

---

## 🛠️ Tecnologias Utilizadas
* **Java 17** ou superior
* **Spring Boot 3.x** (Spring Data JPA, Spring Web)
* **PostgreSQL** (Banco de dados relacional de produção)
* **Docker & Docker Compose** (Orquestração do container do banco de dados)
* **Maven** (Gerenciador de dependências e automação de build)

---

## ⚙️ Arquitetura de Infraestrutura (Docker)
O projeto conta com um arquivo `docker-compose.yml` pré-configurado na raiz para subir a instância do banco de dados isolada:

```yaml
version: '3.8'
services:
  postgres-db:
    image: postgres:latest
    container_name: sistema_bancario_postgres
    environment:
      POSTGRES_USER: seu_usuario
      POSTGRES_PASSWORD: sua_senha
      POSTGRES_DB: sistema_bancario
    ports:
      - "5432:5432"
```

---

## 🏁 Como Executar o Projeto Localmente

### Pré-requisitos
Antes de começar, certifique-se de ter instalado em sua máquina:
* [Git](https://git-scm.com)
* [Docker Desktop](https://docker.com) instalado e rodando
* Java JDK (versão 17+)
* Uma IDE de sua preferência (ex: IntelliJ IDEA)

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com
   cd sistema-bancario-api-postgres
   ```

2. **Suba o Banco de Dados com Docker:**
   Abra o seu terminal na raiz do projeto e execute o comando abaixo para criar e rodar o container do PostgreSQL em segundo plano:
   ```bash
   docker compose up -d
   ```

3. **Configuração de Ambiente (`application.properties`):**
   Verifique se as propriedades de conexão em `src/main/resources/application.properties` correspondem às credenciais configuradas no Docker Compose:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_bancario
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Execute a aplicação:**
   Você pode rodar a aplicação diretamente pela sua IDE (executando a classe principal com `@SpringBootApplication`) ou via terminal usando o Maven Wrapper incluído no projeto:
   ```bash
   ./mvnw spring-boot:run
   ```
   A API estará disponível em `http://localhost:8080`.

---

## 🛣️ Principais Endpoints da API (Exemplos)

Aqui você pode listar as rotas principais que desenvolveu (ajuste conforme os seus Controllers reais):

* `POST /contas` - Criação de uma nova conta bancária.
* `GET /contas/{id}` - Busca os detalhes de uma conta específica.
* `POST /transacoes/transferir` - Executa uma transferência entre duas contas.
* `GET /transacoes/extrato/{contaId}` - Retorna o histórico financeiro de uma conta.

---

## ✒️ Autor
* **Rômulo Bezerra** - [Seu Perfil do GitHub](https://github.com/rom-dev-bezerra)
