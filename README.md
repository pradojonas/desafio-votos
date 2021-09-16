# southsystem-desafio-back-votos
 Processo Seletivo para SouthSystem


## Executando o projeto

TODO: Comentar seleção de profile (dev, prod).

#### Dev

O perfil de desenvolvimento não demanda qualquer configuração.
OBS: Os dados serão perdidos sempre que a execução for interrompida, uma vez que esse profile utiliza uma instância H2 em memória.

#### Prod

Diferentemente do anterior, o perfil de produção faz persistência de dados via conexão com um banco Postgres, cujas configurações do datasource estão no arquivo app.prod.properties.

É possível subir uma instância via Docker, utilizando o arquivo 'docker-compose.yml' na raiz desse projeto. Para isso, siga os seguintes passos: 

1. Abra o arquivo 'docker-compose.yml' que se encontra na raiz do projeto;
2. Altere o caminho do volume Docker para um diretório válido, onde serão armazenados os dados da aplicação (Ex: /home/mustella/dockerData/PostgreSQL/);
3. Garanta que a porta 5433 está disponível; caso não esteja, insira uma porta livre em 'docker-compose.yml' e 'app.prod.properties';
4. Abra o terminal no diretório onde está o 'docker-compose.yml' e execute o comando: `docker-compose up -d`