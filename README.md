# Epayment

![Java 17](https://img.shields.io/badge/java-v17-orange)
![Spring Boot 3.3.0](https://img.shields.io/badge/spring_boot-v3.3.0-green)
![Apache Kafka 3.7.0](https://img.shields.io/badge/kafka-v3.7.0-blue)

## Motivação

Estudar os princípios de uma arquitetura baseada em eventos, no contexto da migração
de um monolito em micro-serviços. Partindo desde a definição dos eventos de domínio
até a sua distribuição com mensageria no padrão publish/subscribe.

O assincronismo das trocas entre os componentes permite que os mesmos possam interagir
com uma maior flexibilidade e tolerância a falhas.

## Requisitos

- O sistema deve permitir a manutenção de contas bancárias e a efetuação de transferências
- Cada alteração no sistema deve estar associada a um evento
  - As variações na conta de um cliente devem ser notificadas por email de forma sequencial
- Todos os eventos devem ser gravados localmente no sistema (logging)
  - As informações pessoais do usuário devem ser filtradas

## Contexto de implementação

Para o desenvolver a comunicação, foi utilizado o Apache Kafka: uma plataforma
distribuída de streaming de eventos com servidores (brokers) que intermediam
a troca de mensagens.

A arquitetura do Kafka garante que, enquanto uma categoria de eventos (tópico)
possa ser processada em paralelo por um grupo de consumidores, dentro de uma
subcategoria (partição) o fluxo de mensagens seja sequencial.

## Interface fornecida

A interação com o usuário é realizada por meio de requisições http, listadas a seguir.

### Criação de conta

Abre uma conta bancária com um crédito inicial. Emite o evento `AccountCreated`.

```txt
POST /account, body: { fullName="John Doe", email="john@mail", password="123" }
```

### Remoção de conta

Apaga os dados pessoais de uma conta bancária. Emite o evento `AccountDeleted`.

```txt
DELETE /account, body: { email="john@mail" }
```

### Transferência de recursos

Realiza uma transação financeira entre duas contas bancárias. Emite o evento
`BalanceChanged` com a alteração no saldo de cada cliente. Caso a transferência não
seja válida, o estado anterior é recuperado e o evento `TransactionFailed` é lançado.

```txt
POST /transfer, body: { senderEmail="sender@mail", receiverEmail="receiver@mail", amount=10.01 }
```

### Listagem de dados pessoais

Consulta as informações de uma conta: nome completo, email e saldo.

```txt
GET /account, body: { email="john@mail" }
```

### Listagem de transações

Consulta o histórico de transações de uma conta.

```txt
GET /account/transfers, body: { email="john@mail" }
```

## Configuração do ambiente

Para acessar o servidor em modo de desenvolvimento, siga as etapas:

1. Clone o projeto
2. Inicialize o Kafka: obtenha a imagem no Docker Hub e execute o container na porta 9092

```bash
docker pull apache/kafka:3.7.0
docker run -p 9092:9092 apache/kafka:3.7.0
```

3. Defina as variáveis ambiente `.env.properties` na raiz do projeto, modificando
o template `.env.example.properties`

4. Compile e execute a aplicação com maven

### Testes automatizados

Para garantir a qualidade do sistema, foi desenvolvido um conjunto de testes
unitários e de integração, com JUnit e Mockito. Execute o comando `./mvnw test`
para ver as estatísticas.

## Contato

Desenvolvido por Andre Schlichting • <andresch.dev@gmail.com>
