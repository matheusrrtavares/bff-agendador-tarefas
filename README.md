<div align="center">

# 🛡️ BFF Agendador de Tarefas
### API Gateway & Backend For Frontend (BFF) — Distributed Task Scheduler

[![Java](https://img.shields.io/badge/Java-17%2F21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Cloud OpenFeign](https://img.shields.io/badge/Spring_Cloud-OpenFeign-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-cloud-openfeign)
[![Swagger](https://img.shields.io/badge/OpenAPI_3-Swagger_UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

<p align="center">
  <b>Ponto de entrada único (API Gateway/BFF) responsável por centralizar a documentação OpenAPI/Swagger, orquestrar a comunicação síncrona entre microsserviços via Spring Cloud OpenFeign e gerenciar rotinas automatizadas com Cron Jobs (@Scheduled).</b>
</p>

</div>

---

## 🌐 Ecossistema do Projeto (Navegação Polyrepo)

> Este microsserviço faz parte de uma arquitetura distribuída composta por **4 serviços autônomos**. Navegue pelos módulos:

| Microsserviço | Papel Arquitetural | Stack & Persistência | Porta | Repositório |
| :--- | :--- | :--- | :---: | :---: |
| 🛡️ **BFF Agendador** | Gateway, Orquestração OpenFeign, Swagger & Cron | Spring Boot 3, OpenFeign, Swagger | `8083` | 📍 **Você está aqui** |
| 👤 **MS Usuário** | Gestão de Identidade, JWT, JPA e API ViaCEP | Spring Security, JWT, PostgreSQL | `8080` | [🔗 `usuario`](https://github.com/matheusrrtavares/usuario) |
| 📅 **MS Agendador** | Core de Agendamentos, Status e Persistência NoSQL | Spring Data MongoDB, MapStruct | `8081` | [🔗 `agendador-tarefas`](https://github.com/matheusrrtavares/agendador-tarefas) |
| 📧 **MS Notificações** | Envio de E-mails Transacionais com HTML Dinâmico | JavaMailSender, Thymeleaf, SMTP | `8082` | [🔗 `notificacao`](https://github.com/matheusrrtavares/notificacao) |

---

## 📐 Arquitetura & Topologia

```mermaid
flowchart TB
    Client(["💻 Cliente / Frontend / Swagger UI"]) -->|"HTTP / REST + JWT"| BFF["🛡️ bff-agendador-tarefas (:8083)<br/>(Gateway, OpenFeign & @Scheduled)"]

    subgraph Internal ["Rede Interna de Microsserviços"]
        BFF -->|"OpenFeign (Auth, Cadastro, CEP)"| MS_User["👤 usuario (:8080)<br/>PostgreSQL"]
        BFF -->|"OpenFeign (CRUD Tarefas)"| MS_Task["📅 agendador-tarefas (:8081)<br/>MongoDB"]
        BFF -->|"Cron Trigger / Feign (Disparo)"| MS_Notif["📧 notificacao (:8082)<br/>JavaMailSender + Thymeleaf"]
    end

    MS_User -->|"OpenFeign"| ViaCEP(["🌐 API Externa ViaCEP"])
    MS_Notif -->|"SMTP"| MailServer(["📨 Servidor SMTP"])
```

---

## ⚙️ Responsabilidades Técnicas

1. **Padrão Backend For Frontend (BFF):** Centraliza os contratos e simplifica o consumo por clientes web/mobile, evitando que o frontend conheça a topologia interna dos microsserviços.
2. **Orquestração Síncrona com OpenFeign:** Clientes declarativos (`@FeignClient`) para comunicação HTTP com repasse transparente do token `Bearer JWT`.
3. **Agendamento e Cron Jobs (`@Scheduled`):** Rotina configurável em background que busca tarefas prestes a vencer no `MS Agendador`, aciona o disparo no `MS Notificações` e atualiza o status para `NOTIFICADO`.
4. **Documentação Unificada (OpenAPI/Swagger):** Rota `/swagger-ui/index.html` expondo todos os endpoints orquestrados para testes interativos.

---

## 🔄 Fluxo do Cron Job (Disparo Automatizado)

```mermaid
sequenceDiagram
    autonumber
    participant Cron as ⏰ BFF Cron (@Scheduled)
    participant UserMS as 👤 MS Usuário
    participant TaskMS as 📅 MS Agendador
    participant NotifMS as 📧 MS Notificações

    Cron->>UserMS: POST /usuario/login (Autenticação do Sistema)
    UserMS-->>Cron: 200 OK (Retorna Bearer JWT)
    Cron->>TaskMS: GET /tarefas/eventos (Header: Bearer JWT)
    TaskMS-->>Cron: 200 OK (Lista de Tarefas Pendentes)

    loop Para cada tarefa pendente
        Cron->>NotifMS: POST /email (DTO da Tarefa + E-mail do Destinatário)
        NotifMS-->>Cron: 200 OK (E-mail enviado via SMTP)
        Cron->>TaskMS: PATCH /tarefas?status=NOTIFICADO&id={id}
        TaskMS-->>Cron: 200 OK (Status atualizado no MongoDB)
    end
```

---

## 📡 Endpoints Centralizados no BFF (Porta 8083)

| Método | Endpoint | Descrição | Requer JWT? | Destino Interno |
| :---: | :--- | :--- | :---: | :--- |
| `POST` | `/usuario` | Cadastra novo usuário com endereço e telefone | ❌ Não | `usuario (:8080)` |
| `POST` | `/usuario/login` | Realiza autenticação e retorna o Bearer Token | ❌ Não | `usuario (:8080)` |
| `GET` | `/usuario/endereco/{cep}` | Consulta dados de endereço na API externa ViaCEP | ❌ Não | `usuario (:8080)` |
| `GET` | `/usuario` | Busca perfil completo do usuário autenticado | ✅ Sim | `usuario (:8080)` |
| `PUT` | `/usuario` | Atualiza dados cadastrais | ✅ Sim | `usuario (:8080)` |
| `DELETE`| `/usuario` | Exclui a conta do usuário logado | ✅ Sim | `usuario (:8080)` |
| `POST` | `/usuario/endereco` | Cadastra novo endereço para o usuário logado | ✅ Sim | `usuario (:8080)` |
| `POST` | `/usuario/telefone` | Cadastra novo telefone para o usuário logado | ✅ Sim | `usuario (:8080)` |
| `POST` | `/tarefas` | Cria um novo agendamento de tarefa | ✅ Sim | `agendador-tarefas (:8081)` |
| `GET` | `/tarefas/eventos` | Lista todas as tarefas do usuário autenticado | ✅ Sim | `agendador-tarefas (:8081)` |
| `PUT` | `/tarefas` | Atualiza informações de uma tarefa existente | ✅ Sim | `agendador-tarefas (:8081)` |
| `PATCH`| `/tarefas` | Atualiza o status (`PENDENTE`, `NOTIFICADO`, `CANCELADO`)| ✅ Sim | `agendador-tarefas (:8081)` |
| `DELETE`| `/tarefas` | Exclui uma tarefa por ID | ✅ Sim | `agendador-tarefas (:8081)` |

---

## 🚀 Como Executar

### Pré-requisitos
* Java JDK 17 ou 21
* Maven 3.8+ (ou `./mvnw`)
* Microsserviços `usuario` (8080), `agendador-tarefas` (8081) e `notificacao` (8082) em execução

### Execução Local
```bash
# Clonar o repositório
git clone https://github.com/matheusrrtavares/bff-agendador-tarefas.git
cd bff-agendador-tarefas

# Compilar e executar
./mvnw clean spring-boot:run
```

### Documentação Interativa
* **Swagger UI:** `http://localhost:8083/swagger-ui/index.html`
* **OpenAPI Docs:** `http://localhost:8083/v3/api-docs`

---

## 👨‍💻 Autor

Desenvolvido por **Matheus Rodrigues Rocha Tavares**  
*Backend Java Developer | Software Engineering B.S.*  
[LinkedIn](https://www.linkedin.com/in/matheusrrtavares) • [GitHub](https://github.com/matheusrrtavares)
