# Back-end Challenge
## Introdução

Esta aplicação é uma API RESTful desenvolvida em Java utilizando o Spring Boot.
Ela gerencia pedidos e suas alterações de status conforme as regras de negócio descritas no desafio.

## Tecnologias Utilizadas

* Java 11 
* Spring Boot
* H2 Database (banco de dados em memória)
* Maven (para gerenciamento de dependências)
* JUnit

## Configuração do Ambiente

### Pré-requisitos

* Certifique-se de ter o JDK 11 instalado
* Tenha o Maven instalado
* Git instalado

### Clonando o Repositório

1. Abra o Terminal ou o Git Bash no seu computador;
2. Navegue até a pasta onde deseja clonar o repositório;
3. Execute o comando a seguir: 
```
git clone https://github.com/suellensr/backend-challenge.git
```
4. Pressione ENTER para criar uma cópia local do repositório.
5. Após clonar o repositório, acesse a branch específica para revisão:
```
git checkout suellensr
```

### Executando a Aplicação

1. No terminal, certifique-se que está no endereço do repositório backend-challenge-two
2. Execute o comando abaixo para buildar o projeto
```
mvn clean install
```
3. Execute o comando abaixo para startar a servidor
```
mvn spring-boot:run
```
4. Execute o comando abaixo para rodar apenas os testes
```
mvn test
```

## Utilizando a Aplicação

Para testar a aplicação é necessário usar uma ferramenta para fazer as requisições.
Sugiro a utilização do Postman, que será a ferramenta utilizada nesta explicação.

A aplicação será iniciada em http://localhost:8080.

## Endpoints da API

### Pedido

#### Criar Pedido
* Endpoint: POST /api/pedido
* Payload:
```
{
  "pedido":"123456",
  "itens": [
    {
      "descricao": "Item A",
      "precoUnitario": 10,
      "qtd": 1
    },
    {
      "descricao": "Item B",
      "precoUnitario": 5,
      "qtd": 2
    }
  ]
}
```
* Resposta de Sucesso: HTTP 201 Created

#### Obter Pedido

* Endpoint: GET /api/pedido/{pedidoId}
* Resposta de Sucesso:
```
{
  "pedido":"123456",
  "itens": [
    {
      "descricao": "Item A",
      "precoUnitario": 10,
      "qtd": 1
    },
    {
      "descricao": "Item B",
      "precoUnitario": 5,
      "qtd": 2
    }
  ]
}
```
#### Atualizar Pedido

* Endpoint: PUT /api/pedido/{pedidoId}
* Payload:
```json
{
  "pedido":"123456",
  "itens": [
    {
      "descricao": "Item A",
      "precoUnitario": 15,
      "qtd": 1
    },
    {
      "descricao": "Item B",
      "precoUnitario": 5,
      "qtd": 3
    }
  ]
}
```
* Resposta de Sucesso: HTTP 200 OK


#### Deletar Pedido
* Endpoint: DELETE /api/pedido/{pedidoId}
* Resposta de Sucesso: HTTP 200 OK + mensagem ("Order with id "  + orderId + " has been deleted successfully.")

### Status do pedido

#### Alterar Status
* Endpoint: POST /api/status
* Payload:
```
{
"status": "APROVADO",
"itensAprovados": 3,
"valorAprovado": 20,
"pedido": "123456"
}
```

* Resposta de Sucesso:
```
{
"pedido": "123456",
"status": ["APROVADO"]
}
```

## Database H2

O banco de dados em memória H2 também pode ser consultado a partir da URL http://localhost:8080/h2-console/

## Exemplo de uso

[![Exemplo de Uso](https://img.youtube.com/vi/tUxuFuHxtw8/hqdefault.jpg)](https://youtu.be/tUxuFuHxtw8)
