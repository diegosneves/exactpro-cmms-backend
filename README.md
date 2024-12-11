# ExactPro CMMS - backend <img align="center" src="https://raw.githubusercontent.com/devicons/devicon/6910f0503efdd315c8f9b858234310c06e04d9c0/icons/linux/linux-original.svg" alt="Linux" width="40" height="40">


![Spring](https://img.shields.io/badge/Spring-6DB33F?style=plastic&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=plastic&logo=openjdk&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=plastic&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=plastic&logo=kubernetes&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=plastic&logo=linux&logoColor=black)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=plastic&logo=swagger&logoColor=black)

## CMMS (Computerized Maintenance Management System)

### Sistema de Gestão de Manutenção Computadorizado

### O que é um CMMS?

Um **CMMS** é um sistema especializado que auxilia empresas na **gestão de tarefas de manutenção**, **controle de
equipamentos**, **calendários de manutenção preventiva**, **emissão de relatórios e laudos técnicos**, além do *
*gerenciamento de ativos**.

### Por que este sistema é um CMMS?

Este projeto foi desenvolvido para atender às necessidades de empresas que buscam uma solução completa para **gestão de
manutenção industrial**. Entre as principais funcionalidades estão:

- **Gestão de Manutenção Preventiva e Corretiva**: Focado em gerenciar manutenções de válvulas e caldeiras, além de
  programar atividades preventivas, alinhando-se perfeitamente às funcionalidades típicas de um CMMS.
- **Emissão de Relatórios e Laudos**: Permite a emissão de relatórios técnicos e laudos para clientes, garantindo a
  documentação e conformidade sobre o status dos equipamentos.
- **Monitoramento e Registro de Equipamentos**: Oferece ferramentas para acompanhar o estado dos equipamentos, registrar
  dados importantes e definir datas para intervenções preventivas, assegurando uma gestão eficiente e proativa.

---

### Arquitetura do Projeto <img align="center" alt="Diego-Kubernetes" height="20" width="140" src="https://img.shields.io/badge/Clean%20Architecture-DD0031?style=plastic&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNzAiIGhlaWdodD0iNzAiIHZpZXdCb3g9IjAgMCA3MCA3MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHJlY3Qgd2lkdGg9IjcwIiBoZWlnaHQ9IjcwIiByeD0iMzUiIGZpbGw9IiNGRTJFNzUiLz4KPGcgc3Ryb2tlPSIjMDAwIiBzdHJva2Utd2lkdGg9IjMiIGZpbGw9Im5vbmUiPgo8Y2lyY2xlIGN4PSIzNSIgY3k9IjM1IiByPSIxNi41Ii8+CjxyZWN0IHg9IjEzIiB5PSIxMyIgd2lkdGg9IjQ0IiBoZWlnaHQ9IjQ0IiByeD0iMTYuNSIvPgo8Y2lyY2xlIGN4PSIzNSIgY3k9IjM1IiByPSIxNi41Ii8+CjwvZz4KPC9zdmc+Cg==&logoColor=white"/>

Este projeto utiliza a `Clean Architecture`, uma abordagem que visa fornecer uma estrutura robusta e escalável para o
desenvolvimento de sistemas complexos. A Clean Architecture se caracteriza por:

- **Separação de Responsabilidades**: Dividimos o código em camadas, cada uma com uma responsabilidade clara, o que
  ajuda a manter o código organizado e facilmente testável.
- **Independência de Frameworks**: As regras de negócio não dependem de frameworks específicos, o que facilita a
  manutenção e a evolução do sistema.
- **Testabilidade**: A separação clara de responsabilidades facilita a criação de testes unitários e de integração,
  garantindo que cada componente do sistema funciona corretamente.
- **Facilidade de Manutenção**: A estrutura modular permite que mudanças e adições sejam implementadas de maneira
  isolada, reduzindo o risco de impacto em outras partes do sistema.

A estrutura típica de uma Clean Architecture é dividida nas seguintes camadas:

1. **Camada de Entidades**: Contém as regras de negócio e entidades fundamentais do sistema.
2. **Camada de Caso de Uso**: Define os casos de uso da aplicação, orquestrando a lógica de negócio.
3. **Camada de Interface**: Responsável pela interface com os usuários (UI/UX) e por definir contratos de interfaces de
   entrada/saída.
4. **Camada de Infraestrutura**: Implementa os detalhes técnicos e frameworks necessários, como bancos de dados, APIs,
   etc.

Esse esquema garante que cada camada dependa apenas das camadas mais internas, promovendo assim um design que facilita a
escalabilidade, manutenção e adaptação do sistema às novas necessidades.

Este projeto utiliza a `Clean Architecture`, uma abordagem que visa fornecer uma estrutura robusta e escalável para o
desenvolvimento de sistemas complexos. A Clean Architecture se caracteriza por:

- **Separação de Responsabilidades**: Dividimos o código em camadas, cada uma com uma responsabilidade clara, o que
  ajuda a manter o código organizado e facilmente testável.
- **Independência de Frameworks**: As regras de negócio não dependem de frameworks específicos, o que facilita a
  manutenção e a evolução do sistema.
- **Testabilidade**: A separação clara de responsabilidades facilita a criação de testes unitários e de integração,
  garantindo que cada componente do sistema funciona corretamente.
- **Facilidade de Manutenção**: A estrutura modular permite que mudanças e adições sejam implementadas de maneira
  isolada, reduzindo o risco de impacto em outras partes do sistema.

A estrutura típica de uma Clean Architecture é dividida nas seguintes camadas:

1. **Camada de Entidades**: Contém as regras de negócio e entidades fundamentais do sistema.
2. **Camada de Caso de Uso**: Define os casos de uso da aplicação, orquestrando a lógica de negócio.
3. **Camada de Interface**: Responsável pela interface com os usuários (UI/UX) e por definir contratos de interfaces de
   entrada/saída.
4. **Camada de Infraestrutura**: Implementa os detalhes técnicos e frameworks necessários, como bancos de dados, APIs,
   etc.

Esse esquema garante que cada camada dependa apenas das camadas mais internas, promovendo assim um design que facilita a
escalabilidade, manutenção e adaptação do sistema às novas necessidades.

---

## <img align="center" src="https://raw.githubusercontent.com/devicons/devicon/6910f0503efdd315c8f9b858234310c06e04d9c0/icons/swagger/swagger-original.svg" alt="Swagger" width="40" height="30"> Swagger - ExactPro CMMS

[<img align="center" src="https://img.shields.io/badge/Swagger-85EA2D?style=plastic&logo=swagger&logoColor=black" alt="Swagger" width="80" height="20">](http://localhost:8080/swagger-ui/index.html)

---

## <img align="center" alt="Diego-Docker" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-plain.svg"> Docker Compose (`compose.yaml`)

O arquivo `compose.yaml` é utilizado para definir e gerenciar os serviços Docker necessários para a aplicação. Ele permite organizar e configurar múltiplos contêineres de forma simples e eficiente.

No caso deste projeto, o `compose.yaml` configura dois serviços principais:

1. **database**: Um contêiner executando o MySQL.
2. **exactpro-app**: O contêiner da aplicação.

Trecho do `compose.yaml`:

```yaml
services:
  database:
    image: "mysql:8.2.0-oraclelinux8"
    container_name: exactpro_mysql_db
    environment:
      - MYSQL_DATABASE=${DB_NAME}
      - MYSQL_ROOT_PASSWORD=${DB_PASSWORD}
    ports:
      - "3307:3306"
    volumes:
      - db-mysql-exactpro:/var/lib/mysql

  exactpro-app:
    image: diegoneves/exactpro-cmms-backend:latest
    container_name: exactpro-api
    ports:
      - "8080:8080"
      - "8081:5005"
    depends_on:
      - database
    environment:
      - DB_HOST=database
      - DB_PORT=3306
      - DB_NAME=${DB_NAME}
      - DB_USERNAME=${DB_USERNAME}
      - DB_PASSWORD=${DB_PASSWORD}
    entrypoint: sh -c "dockerize -wait tcp://$${DB_HOST}:$${DB_PORT} -timeout 20s && java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar infrastructure/target/exactpro-cmms.jar"

volumes:
  db-mysql-exactpro:
```

#### Arquivo `.env`

O arquivo `.env` é utilizado para armazenar variáveis de ambiente de forma segura e prática. Ele permite separar configurações sensíveis e específicas do ambiente de execução do código da aplicação.

Exemplo de um arquivo `.env`:

```dotenv
DB_USERNAME=root
DB_PASSWORD=local_password
DB_NAME=exactpro_db
DB_PORT=3307
DB_HOST=localhost
```
### Benefícios de Usar `compose.yaml` e `.env`

- **Facilidade de Configuração**: Permite definir configurações e relações entre múltiplos serviços em um único arquivo.
- **Separação de Responsabilidades**: Mantém variáveis sensíveis fora do código-fonte, melhorando a segurança e facilitando a manutenção.
- **Flexibilidade**: Facilita a troca entre diferentes ambientes (desenvolvimento, teste, produção) alterando apenas o arquivo `.env`.

### Executando os Serviços

Para iniciar os serviços definidos no `compose.yaml`, execute:
```sh
docker-compose up -d
```

Certifique-se de que o arquivo `.env` esteja no mesmo diretório do `compose.yaml` para que o Docker Compose carregue as variáveis adequadamente.

### Resumo

- **Arquivo `compose.yaml`**: Gerencia e configura os serviços Docker.
- **Arquivo `.env`**: Armazena variáveis de ambiente de forma segura.

---