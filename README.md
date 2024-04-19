# e-payment system

Gerenciamento de transações financeiras.

Um projeto de aprendizagem, com o objetivo colocar em prática:

- Injeções de dependência com `Spring`
- Testes automatizados com `JUnit` e `Mockito`

## Conceitos

- Um `usuário` cadastrado pode abrir uma `conta corrente` para efetuar `transações`
- O sistema suporta 3 tipos de transações: transferências, saques e depósitos

*Caso uma transação falhe, os recursos não são transferidos.
