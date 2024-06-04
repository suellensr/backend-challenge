# Back-end Challenge
## Introdução

Esta aplicação é uma API RESTful desenvolvida em Java utilizando o Spring Boot.
Ela gerencia pedidos e suas alterações de status conforme as regras de negócio descritas no desafio.

## Tecnologias Utilizadas

Java 11 
Spring Boot 2.5
H2 Database (banco de dados em memória)
Maven (para gerenciamento de dependências)
Configuração do Ambiente
Pré-requisitos
Certifique-se de ter o JDK 11 instalado.
Tenha o Maven instalado.
Clonando o Repositório
bash
Copiar código
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
Construindo o Projeto
bash
Copiar código
mvn clean install
Executando a Aplicação
bash
Copiar código
mvn spring-boot:run
A aplicação será iniciada em http://localhost:8080.

Endpoints da API
Pedido
Criar Pedido
Endpoint: POST /api/pedido
Payload:
json
Copiar código
{
"pedido": "123456",
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
Resposta de Sucesso: HTTP 201 Created
Obter Pedido
Endpoint: GET /api/pedido/{pedidoId}
Resposta de Sucesso:
json
Copiar código
{
"pedido": "123456",
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
Atualizar Pedido
Endpoint: PUT /api/pedido/{pedidoId}
Payload:
json
Copiar código
{
"pedido": "123456",
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
Resposta de Sucesso: HTTP 200 OK
Deletar Pedido
Endpoint: DELETE /api/pedido/{pedidoId}
Resposta de Sucesso: HTTP 204 No Content
Mudança de Status de Pedido
Alterar Status
Endpoint: POST /api/status
Payload:
json
Copiar código
{
"status": "APROVADO",
"itensAprovados": 3,
"valorAprovado": 20,
"pedido": "123456"
}
Resposta de Sucesso:
json
Copiar código
{
"pedido": "123456",
"status": ["APROVADO"]
}
Testes
A aplicação inclui testes unitários e de integração. Para executá-los, utilize o seguinte comando:

bash
Copiar código
mvn test
Utilizando o Postman
Importar Coleção
Importe a coleção de endpoints fornecida no arquivo postman_collection.json (disponível no repositório).
Executar Requests
Utilize os endpoints conforme descritos acima para criar, atualizar, buscar, deletar pedidos e alterar seus status.
Considerações Finais
Siga as práticas de SOLID para assegurar que o código seja extensível e mantenível.
Utilize o banco de dados H2 em memória para simplificar o processo de testes e desenvolvimento.