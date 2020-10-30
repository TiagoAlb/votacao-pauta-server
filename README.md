# Votação Pauta
Link do repositório front-end: https://github.com/Tiagoalbuquerque2302/votacao-pauta-client

## Servidor de aplicação
Foi configurado o projeto em um servidor externo, para facilitar os testes da equipe. 
* ATENÇÃO: Recomenda-se não realizar testes de desempenho no servidor externo, devido a baixa capacidade de processamento e a já utilização por outras aplicações.

Link para testes de requisições a API: `http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api`

Link para o sitema web: `http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:3000`

### Usuário admin de login
Email: usuarioadmin@gmail.com

Senha: admin

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
* É necessário realizar a autenticação do usuário para acesso a API. Apenas o cadastro de usuários é permitido sem autenticação.
Ex:

Link externo: `http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api`

Método GET associados: `/associados`

Endpoint completo: `http://ec2-18-229-143-41.sa-east-1.compute.amazonaws.com:8082/api/votacaoPauta/associados`

Seguem abaixo os seguintes endpoints criados para o funcionamento da API:
### GET
Autenticar usuário e receber token de acesso:  `/login`

Validar token de acesso: `/validateLogin`

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
- Unauthorized
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

### NEWPASSWORD
- O campo senha não pode ser nulo!
- O campo senha não pode ser vazio!

### Pauta
- Pauta {id} não encontrada!
- Erro ao incluir pauta!
- Unauthorized
### TITULO
- O campo titulo não pode ser nulo!
- O campo titulo deve possuir no máximo 100 caracteres!
- O campo titulo não pode ser vazio!

### DESCRICAO
- O campo descricao não pode ser nulo!
- O campo descricao deve possuir no máximo 500 caracteres!
- O campo descricao não pode ser vazio!

### AUTOR (campo vinculado automáticamente com autenticação) - Não é necessário informar

### Votação
- Votação {id} não encontrada!
- A votação {id} ainda não terminou! Dados não contabilizaos.
- Erro ao iniciar sessão de votação!
- A pauta {id} está em votação neste momento!
- A pauta {id} já foi votada! Sessão encerrada.
- Unauthorized
### MINUTO
- O tempo mínimo para sessão de votação é de 1 minuto!

### Voto
- Voto {id} não encontrado!
- Sessão de votação encerrada! Não é mais possível votar.
- O associado {id} já realizou seu voto!
- Não foi possível votar!
- Unauthorized
### VOTO
- Campo voto inválido!

## Tratamentos de sucesso da API
- Votação ID: {id} iniciada com sucesso! Término em {dataTermino}
- A pauta ID: {id} foi aprovada! {porcentagem} dos votos favoráveis.
- A pauta ID: {id} foi reprovada! {porcentagem} dos votos contrários.
- A pauta ID: {id} foi votada com empate nos votos, mas sem uma conclusão de aprovação! {porcentagemFavoravel} dos votos favoráveis e {porcentagemContraria} dos votos contrários.

## Exemplos de uso
### Autenticar usuário
```
GET:
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/login

Exemplo de usuário:
Email: usuarioadmin@gmail.com
Senha: admin

Enviar no Header a Authorization no seguinte formato:
Basic + usuario:senha (convertido em Base64)

Ex: usuarioadmin@gmail.com:admin > dXN1YXJpb2FkbWluQGdtYWlsLmNvbTphZG1pbg==

Para testes pode-se utilizar o seguinte site para conversão em Base64: https://www.4devs.com.br/codificar_decodificar_base64

Header:
{
	"Authorization":"Basic dXN1YXJpb2FkbWluQGdtYWlsLmNvbTphZG1pbg=="
}

Retorno:

Corpo:
{
  "id": 3,
  "cnpjCpf": "04134740037",
  "nome": "Usuário Admin",
  "email": "usuarioadmin@gmail.com",
  "creation_date": "2020-10-29@18:36:28.267+0000",
  "permissions": [
    "user",
    "admin"
  ]
}

Header:
token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MywiZXhwIjoxNjA0MDE4ODgxfQ.VqbVvzA1vtZ6iUwpV_G65VeHX4VO0zynqZMGHsRWH-E
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 29 Oct 2020 18:48:01 GMT
```

### Validar token de acesso
```
GET:
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/validateLogin

Exemplo de usuário:
Email: usuarioadmin@gmail.com
Senha: admin

Enviar no Header a Authorization no seguinte formato:
Basic + token de acesso retornado no login

Header:
{
	"Authorization":"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MywiZXhwIjoxNjA0MDE4ODgxfQ.VqbVvzA1vtZ6iUwpV_G65VeHX4VO0zynqZMGHsRWH-E"
}

Retorno:

Corpo:
{
  "id": 3,
  "cnpjCpf": "04134740037",
  "nome": "Usuário Admin",
  "email": "usuarioadmin@gmail.com",
  "creation_date": "2020-10-29@18:36:28.267+0000",
  "permissions": [
    "user",
    "admin"
  ]
}
```

### Listar Associados - Similar a outras listagens
```
GET:
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/associados

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
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/pautas/1

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
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/votacoes/1/status

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
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/associados

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
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/pautas

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
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/pautas/1/votacao?minutes=1

Retorno:
{
  "timestamp": "2020-05-18@08:19:16.373+0000",
  "status": "CREATED",
  "message": "Votação ID: 1 iniciada com sucesso! Término em 18-05-2020 05:20:16"
}
```

### Votar em uma Sessão de Votação
#### * Atenção! Para votar, verifique o ID da votação. Não necessariamente o ID da votação é o mesmo ID da pauta. Quando iniciada a sessão de votação, é retornado o seu ID, que deve ser utilizado para inclusão de novos votos e contagem posterior.
```
POST:
Endpoint: http://ec2-177-71-228-249.sa-east-1.compute.amazonaws.com:8082/api/votacoes/1/associados/1/votos?voto=sim

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
## Páginas do Sistema
### Cadastro de Usuário
Página responsável por fazer o cadastro do Associado no sistema.
<p float="center">
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/cadastro.PNG" width="640" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/cadastro_mobile.jpeg" width="182" />
</p>

### Autenticação
Página responsável pela autenticação do Associado no sistema.
<p float="center">
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/login.PNG" width="640" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/login_mobile.jpeg" width="182" />
</p>

### Home
Página de boas-vindas.
<p float="center">
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/home.PNG" width="640" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/home_mobile.jpeg" width="182" />
</p>

### Pautas
Listagem e visualização das pautas cadastradas. Através desta é possível também ser direcionado para o cadastro de pauta ou iniciar uma votação de pauta.
<p float="center">
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/pautas.PNG" width="350" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/pautas_carregando_paginacao.png" width="350" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/pautas_mobile.jpeg" width="100" />
</p>
<p float="center">
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/pautas_open.PNG" width="640" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/pautas_open_mobile.jpeg" width="185" />
</p>

### Cadastro de Pauta
Página para cadastro de novas pautas.
<p float="center">
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/pautas_cadastro.PNG" width="640" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/pautas_cadastro_mobile.jpeg" width="182" />
</p>

### Iniciar Votação
A página de inicio de votação é responsável por iniciar uma sessão de votação, que possui tempo para aceitação de votos. Através dela deve-se definir a data e hora de conclusão da sessão, tendo como tempo mínimo 1 minuto.
![alt text](https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/iniciar_votacao.PNG)

### Votações
Listagem e visualização das votações já iniciadas. É possível identificar o status da votação (em andamento ou finalizada) e votar naquelas que estiverem ativas.
<p float="center">
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/votacoes.PNG" width="350" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/votacoes_em_votacao.PNG" width="350" />
  <img src="https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/votacoes_em_votacao_mobile.jpeg" width="100" />
</p>

### Envio por email
Não se preocupe! Todos os associados serão avisados da finalização da sessão de votação. O email utilizado para envio será o criado no cadastro do associado.
![alt text](https://github.com/Tiagoalbuquerque2302/votacaoPauta/blob/master/src/main/resources/img/emailVotacaoStatus.PNG)

## Desenvolvimentos futuros
O sistema de votação de pautas foi criado como teste de desenvolvimento, porém para realizações futuras seria interessante:
- Alteração de dados cadastrais de associados e pautas
- Inclusão de foto de perfil do usuário
- Melhora no escopo da mensagem enviada por email
- Definir um escopo do que deveria ser permitido ao usuário admin e user
- Criação de dashboard com resultados das últimas votações
- Melhora no campo de cadastro de pautas, com a possibilidade de customização do texto e possível inclusão de imagens
