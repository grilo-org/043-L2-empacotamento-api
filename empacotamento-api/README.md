# README.md - API de Empacotamento da Loja do Seu Manoel

## Descrição do Projeto

Esta API foi desenvolvida para automatizar o processo de embalagem dos pedidos da loja de jogos online do Seu Manoel. A API recebe uma lista de pedidos com produtos e suas dimensões, e retorna qual o tamanho de caixa de papelão deve ser usado para cada pedido e quais produtos irão em cada caixa.

## Tecnologias Utilizadas

- Java 11
- Spring Boot 2.7.0
- Swagger/SpringFox para documentação da API
- JUnit para testes unitários
- Docker para containerização

## Estrutura do Projeto

```
empacotamento-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── loja/
│   │   │           └── empacotamento/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── service/
│   │   │               ├── dto/
│   │   │               ├── exception/
│   │   │               ├── config/
│   │   │               └── EmpacotamentoApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── loja/
│                   └── empacotamento/
│                       └── EmpacotamentoServiceTest.java
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── entrada.json
└── saida.json
```

## Como Executar

### Pré-requisitos

- Java 11 ou superior
- Maven
- Docker e Docker Compose

### Compilação e Execução Local

1. Clone o repositório:
```
git clone https://github.com/seu-usuario/empacotamento-api.git
cd empacotamento-api
```

2. Compile o projeto:
```
mvn clean package
```

3. Execute a aplicação:
```
java -jar target/empacotamento-0.0.1-SNAPSHOT.jar
```

### Execução com Docker

1. Construa e inicie o container:
```
docker-compose up --build
```

2. A API estará disponível em: http://localhost:8080

## Documentação da API

A documentação Swagger estará disponível em: http://localhost:8080/swagger-ui/

## Exemplos

### Entrada

Arquivo `entrada.json` com exemplo de requisição:

```json
[
  {
    "id": "pedido-001",
    "produtos": [
      {
        "id": "prod-001",
        "nome": "Jogo de Tabuleiro",
        "altura": 10.0,
        "largura": 30.0,
        "comprimento": 40.0
      },
      {
        "id": "prod-002",
        "nome": "Console Portátil",
        "altura": 5.0,
        "largura": 15.0,
        "comprimento": 20.0
      },
      {
        "id": "prod-003",
        "nome": "Jogo de Cartas",
        "altura": 3.0,
        "largura": 10.0,
        "comprimento": 15.0
      }
    ]
  },
  {
    "id": "pedido-002",
    "produtos": [
      {
        "id": "prod-004",
        "nome": "Console de Videogame",
        "altura": 15.0,
        "largura": 30.0,
        "comprimento": 40.0
      },
      {
        "id": "prod-005",
        "nome": "Controle sem Fio",
        "altura": 5.0,
        "largura": 15.0,
        "comprimento": 20.0
      }
    ]
  }
]
```

### Saída

Arquivo `saida.json` com exemplo de resposta:

```json
[
  {
    "pedidoId": "pedido-001",
    "caixas": [
      {
        "tipo": "Caixa 1",
        "altura": 30.0,
        "largura": 40.0,
        "comprimento": 80.0,
        "produtos": [
          {
            "id": "prod-001",
            "nome": "Jogo de Tabuleiro",
            "altura": 10.0,
            "largura": 30.0,
            "comprimento": 40.0
          },
          {
            "id": "prod-002",
            "nome": "Console Portátil",
            "altura": 5.0,
            "largura": 15.0,
            "comprimento": 20.0
          },
          {
            "id": "prod-003",
            "nome": "Jogo de Cartas",
            "altura": 3.0,
            "largura": 10.0,
            "comprimento": 15.0
          }
        ]
      }
    ]
  },
  {
    "pedidoId": "pedido-002",
    "caixas": [
      {
        "tipo": "Caixa 1",
        "altura": 30.0,
        "largura": 40.0,
        "comprimento": 80.0,
        "produtos": [
          {
            "id": "prod-004",
            "nome": "Console de Videogame",
            "altura": 15.0,
            "largura": 30.0,
            "comprimento": 40.0
          },
          {
            "id": "prod-005",
            "nome": "Controle sem Fio",
            "altura": 5.0,
            "largura": 15.0,
            "comprimento": 20.0
          }
        ]
      }
    ]
  }
]
```

## Testes

Para executar os testes unitários:

```
mvn test
```
