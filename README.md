# Votação Pauta

## Servidor de aplicação
Foi configurado o projeto em um servidor externo, para facilitar os testes da equipe. 
* ATENÇÃO: Recomenda-se não realizar testes de desempenho no servidor externo, devido a baixa capacidade de processamento e a já utilização por outras aplicações.

Link para testes de requisições a API: `http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta`

## Resumo
A aplicação foi realizada na linguagem Java, utilizado o framework Spring Boot na versão 2.3.0 e o Maven para controle de dependências. Junto ao Spring, foi utilizado o framework Hibernate, para realizar o mapeamento das classes Java e conversão em tabelas no banco de dados. A API REST desenvolvida possui endpoints com métodos dos tipos GET e POST, para criação e busca de dados. Não foi necessária a utilização de métodos PUT devido a não atualização de nenhuma informação para o projeto.

## Executar projeto
No CMD, para o sistema operacional Windows, acesse o pacote .jar do projeto e execute o seguinte comando para rodar o código compilado:
`java -jar votacaoPautaJar.jar`

## Banco de dados
O gerenciador de banco de dados escolhido para integrar a aplicação foi o MySQL, com as seguintes configurações:

Usuário: root

Senha: root

### Diagrama de banco de dados da aplicação
![alt text](https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/votacaoPautaDatabaseModel.png)

## Endpoints
* Para testes, os métodos descritos na continuidade podem ser escritos completando o link inicial deste documento. 
Ex:

Link externo: `http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta`

Método GET associados: `/associados`

Endpoint completo: `http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/associados`

Seguem abaixo os seguintes endpoints criados para o funcionamento da API:
### GET
Listar associados disponíveis para votação: `/associados`

Buscar associado específico: `/associados/{id}`

Consultar disponibilidade votação api externa (consulta em `https://user-info.herokuapp.com/users/`): `/associados/consultaCnpjCpf/{cnpjCPf}`

Listar pautas: `/pautas`

Buscar pauta específica: `/pautas/{id}`

Listar votacoes: `/votacoes`

Buscar votacao específica: `/votacoes/{id}`

Buscar status de votacao: `/votacoes/{id}/status`

Listar votos: `/votos`

Buscar voto específico: `/votos/{id}`

* Todas as listas são paginadas e possuem os parâmetros opcionais page e page_results, que informam, respectivamente, o número da página e a quantidade máxima de resultados por página.

### POST
Cadastrar associado: `/associados`

Cadastrar pauta: `/pautas`

Iniciar sessão de votação para uma pauta (O parâmetro minutes é opcional. Neste caso será utilizado 1 como padrão.
Caso seja acrescentado 0 uma exceção será lançada): `/pautas/{id}/votacao?minutes={minutos}`

Realizar voto em uma sessão de votação (O parâmetro voto é obrigatório e pode ser colocado com acentuação. Caso não seja preenchido, uma exceção será lançada): `/votacoes/{idVotacao}/associados/{idAssociado}/votos?voto={voto}`

## Tratamentos de erros da API
### Associado
- Associado {id} não encontrado!
- Erro ao incluir associado!
### CNPJ/CPF
- Este CNPJ ou CPF já está cadastrado!
- Formato de CNPJ ou CPF inválido!
- O campo cnpjCpf não pode ser nulo!
- O campo cnpjCpf deve possuir no máximo 14 caracteres!
- O campo cnpjCpf não pode ser vazio!

### EMAIL
- Este email já está cadastrado!
- Email inválido!
- O campo email não pode ser nulo!
- O campo email não pode ser vazio!

### NOME
- O campo nome não pode ser nulo!
- O campo nome deve possuir no máximo 100 caracteres!
- O campo nome não pode ser vazio!

### Pauta
- Pauta {id} não encontrada!
- Erro ao incluir pauta!
### TITULO
- O campo titulo não pode ser nulo!
- O campo titulo deve possuir no máximo 100 caracteres!
- O campo titulo não pode ser vazio!

### DESCRICAO
- O campo descricao não pode ser nulo!
- O campo descricao deve possuir no máximo 500 caracteres!
- O campo descricao não pode ser vazio!

### Votação
- Votação {id} não encontrada!
- A votação {id} ainda não terminou! Dados não contabilizaos.
- Erro ao iniciar sessão de votação!
- A pauta {id} está em votação neste momento!
- A pauta {id} já foi votada! Sessão encerrada.
### MINUTO
- O tempo mínimo para sessão de votação é de 1 minuto!

### Voto
- Voto {id} não encontrado!
- Sessão de votação encerrada! Não é mais possível votar.
- O associado {id} já realizou seu voto!
- Não foi possível votar!
### VOTO
- Campo voto inválido!

## Tratamentos de sucesso da API
- Votação ID: {id} iniciada com sucesso! Término em {dataTermino}
- A pauta ID: {id} foi aprovada! {porcentagem} dos votos favoráveis.
- A pauta ID: {id} foi reprovada! {porcentagem} dos votos contrários.
- A pauta ID: {id} foi votada com empate nos votos, mas sem uma conclusão de aprovação! {porcentagemFavoravel} dos votos favoráveis e {porcentagemContraria} dos votos contrários.

## Exemplos de uso
### Listar Associados - Similar a outras listagens
```
GET:
Endpoint: http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/associados

Retorno:
{
  "content": [
    {
      "id": 1,
      "cnpjCpf": "03599666083",
      "nome": "Tiago Albuquerque",
      "email": "tiago.mauricio.albuquerque@gmail.com",
      "create_date": "2020-05-18@08:17:00.064+0000"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 1,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

### Buscar Pauta Específica - Similar a outras buscas específicas
```
GET:
Endpoint: http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/pautas/1

Retorno:
{
  "id": 1,
  "titulo": "Pauta teste 1",
  "descricao": "Descricao teste da pauta 1 criada no sistema",
  "create_date": "2020-05-18@08:18:16.820+0000"
}
```

### Buscar Status de Votação
```
GET:
Endpoint: http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/votacoes/1/status

Retorno:
{
  "id": 1,
  "votacao": {
    "id": 1,
    "pauta": {
      "id": 1,
      "titulo": "Pauta teste 1",
      "descricao": "Descricao teste da pauta 1 criada no sistema",
      "create_date": "2020-05-18@08:18:16.820+0000"
    },
    "start_date": "2020-05-18@08:19:16.279+0000",
    "end_date": "2020-05-18@08:20:16.388+0000",
    "open": false,
    "minutes": 1
  },
  "qtdSim": 1,
  "qtdNao": 0,
  "qtdVotos": 1,
  "resultado": "A pauta ID: 1 foi aprovada! 100.0% dos votos favoráveis."
}
```

### Cadastrar Associado
```
POST:
Endpoint: http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/associados

Corpo:
{
	"nome":"Tiago Albuquerque",
	"cnpjCpf": "03599666083",
	"email": "tiago.mauricio.albuquerque@gmail.com"
}

Retorno:
{
  "id": 1,
  "cnpjCpf": "03599666083",
  "nome": "Tiago Albuquerque",
  "email": "tiago.mauricio.albuquerque@gmail.com",
  "create_date": "2020-05-18@08:17:00.064+0000"
}
```

### Cadastrar Pauta
```
POST:
Endpoint: http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/pautas

Corpo:
{
	"titulo":"Pauta teste 1",
	"descricao":"Descricao teste da pauta 1 criada no sistema"
}

Retorno:
{
  "id": 1,
  "titulo": "Pauta teste 1",
  "descricao": "Descricao teste da pauta 1 criada no sistema",
  "create_date": "2020-05-18@08:18:16.820+0000"
}
```

### Iniciar Sessão se Votação
```
POST:
Endpoint: http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/pautas/1/votacao?minutes=1

Retorno:
{
  "timestamp": "2020-05-18@08:19:16.373+0000",
  "status": "CREATED",
  "message": "Votação ID: 1 iniciada com sucesso! Término em 18-05-2020 05:20:16"
}
```

### Votar em uma Sessão de Votação
```
POST:
Endpoint: http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/votacoes/1/associados/1/votos?voto=sim

Retorno:
{
  "id": 1,
  "associado": {
    "id": 1,
    "cnpjCpf": "03599666083",
    "nome": "Tiago Albuquerque",
    "email": "tiago.mauricio.albuquerque@gmail.com",
    "create_date": "2020-05-18@08:17:00.064+0000"
  },
  "voto": true,
  "create_date": "2020-05-18@08:20:05.778+0000"
}
```

## Envio por email
Não se preocupe! Todos os associados serão avisados da finalização da sessão de votação. O email utilizado para envio será o criado no cadastro do associado.

### Exemplo de retorno por email
![alt text](https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/emailVotacaoStatus.PNG)

## Desenvolvimentos futuros
A API de votação de pautas foi desenvolvida como teste de desenvolvimento, porém para realizações futuras seria interessante:
- Criação de front-end para votação
- Autenticação de usuários para impedir a votação de um usuário com as credenciais de outro
- Camada de segurança, com o controle de requisições
- Alteração de dados cadastrais de associados e pautas
- Melhora no escopo da mensagem enviada por email
