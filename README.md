# 🐾 Petshop Full Stack System

Sistema web completo para gerenciamento de petshop, desenvolvido como projeto full stack com foco em boas práticas de desenvolvimento, arquitetura em camadas e integração entre front-end e back-end.

## 📌 Sobre o Projeto

Este projeto foi desenvolvido com o objetivo de consolidar conhecimentos em desenvolvimento web full stack, incluindo criação de APIs REST, autenticação com JWT, modelagem de banco de dados e construção de interfaces modernas com React.
O projeto ainda está em andamento e na fase do Front-end, com a API praticamente pronta e recebendo pequenas atualizações conforme o front progride.

O sistema permite o gerenciamento completo de clientes, pets, produtos, serviços e agendamentos em um petshop.

---

##  Tecnologias Utilizadas

### Back-end
- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- JPA / Hibernate
- MySQL
- Maven
- Swagger (Documentação da API)

### Front-end
- React
- Vite
- Axios
- React Router

### Outras Ferramentas
- Git & GitHub
- Postman
- Docker (planejado)
- Figma (prototipação)

---

##  Funcionalidades

### Autenticação e Segurança
- Login com JWT
- Senhas criptografadas
- Controle de acesso por roles:
  - CLIENTE
  - FUNCIONARIO
  - ADMIN

### Clientes
- Cadastro de clientes
- Atualização de dados
- Visualização de informações

### Pets
- Cadastro de pets
- Associação com clientes
- Campos detalhados:
  - Nome
  - Espécie
  - Raça
  - Porte
  - Idade
  - Sexo
  - Peso
  - Observações

### Produtos
- Cadastro de produtos
- Controle de estoque
- Listagem de produtos

### Serviços
- Cadastro de serviços
- Gerenciamento de tipos de atendimento

### Agendamentos
- Criação de agendamentos
- Associação com pet e serviço
- Controle de observações

---

## Arquitetura

O projeto segue o padrão:

Controller → Service → Repository → Database

E utiliza:

- DTOs para transferência de dados
- Separação de responsabilidades


