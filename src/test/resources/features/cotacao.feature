#language: pt

Funcionalidade: : Teste de API de Cotação

  Cenario: Criar e validar cotação
  Dado que eu crio uma nova cotação
  Entao o status code deve ser 201
  E eu extraio o id_cotacao e situacao_cotacao
  E que eu consulto a cotação criada
  Entao o status code deve ser de 200
  E a situacao_cotacao deve ser "teste"