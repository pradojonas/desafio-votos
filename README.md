# southsystem-desafio-back-votos
Desafio técnico para SouthSystem, suja descrição se encontra [aqui](DESCRICAO.md).

## Requisitos

- Java 11
- Maven 3.8 +
- Docker (Apenas em profile prod)

## Executando o projeto

Para executar o projeto, será necessário configurar o servidor do Kafka para onde serão enviados os resultados das sessões de votação. Na configuração atual, as mensagens são submetidas com o tópico **SESSION_RESULT** no endereço [localhost:9092](localhost:9092). Essas configurações podem ser modificadas através dos arquivos app-dev.properties e app-prod.properties.

Além disso, o projeto possui duas configurações de Spring Profile, e a escolha de uma delas é requisito para executar o projeto.

##### Dev

O perfil 'dev' não demanda configuração além do Kafka, mas os dados produzidos pela aplicação serão perdidos sempre que a execução for interrompida, uma vez que esse profile utiliza uma instância H2 em memória.

Para executar em profile 'dev', abra um terminal na raiz do projeto e execute: 

`mvn clean install`
`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

##### Prod

Diferentemente do anterior, o perfil de produção faz persistência de dados via conexão com um banco Postgres, cujas configurações do datasource estão no arquivo app-prod.properties.

É possível subir uma instância via Docker, utilizando o arquivo 'docker-compose.yml' na raiz desse projeto. Para isso, siga os seguintes passos: 


1. Abra o arquivo 'docker-compose.yml' que se encontra na raiz do projeto;
2. Altere o caminho do volume Docker para um diretório válido, onde serão armazenados os dados da aplicação (Ex: /home/mustella/dockerData/PostgreSQL/);
3. Garanta que a porta 5433 está disponível; caso não esteja, insira uma porta livre em 'docker-compose.yml' e 'app-prod.properties';
4. Garanta que o serviço Docker está disponível através do comando: `docker info`
5. Abra o terminal no diretório onde está o 'docker-compose.yml' e execute o comando: `docker-compose up -d`

Após essa sequência, a instância de banco estará pronta para ser utilizada. Abra um terminal na raiz do projeto e execute:

`mvn clean install`
`mvn spring-boot:run -Dspring-boot.run.profiles=prod`

#### Como utilizar a API

TODO: Add link para a API em funcionamento

TODO: Add link swagger