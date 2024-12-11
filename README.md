
## Movies Battle

#### APIs:
 - Login: http://localhost:8080/login
 - Logout: http://localhost:8080/logout
 - Inicia jogo (mostra 2 opções de filmes): http://localhost:8080/start
 - Finaliza jogo: http://localhost:8080/stop
 - Registrar voto (1 ou 2) (POST): http://localhost:8080/vote/1 
   - enviar UUID no Body ex: {"uuid": "3e4cfbd2-07fd-4929-b6de-113bd5c1e10f"}
 - Mostra ranking trazendo dados da View criada na BD: http://localhost:8080/viewranking
 - Mostra ranking trazendo dados executando query nas tabelas: http://localhost:8080/ranking
 - OpenAPI: http://localhost:8080/api
 - Swagger: http://localhost:8080/swagger.html

#### Considerações do Projeto
 - Banco de dados (H2) criado usando Flyway (scripts de migrations ma pasta resources/db/migration)
 - Ao executar aplicação, BD é preenchido com filmes. As informações dos filmes são trazidas através da OMDb API. A API precisa receber o ID IMDb para retornar as informações. Estes são trazidos do arquvo imdb_data.csv coletado no site do Kaggle.
 - No application.properties é possível configurar quantos filmes serão adicionar no BD
 - Segurança da aplicação: senha armazenada utilizando criptografia bcrypt
 - Foi criado um Monitor para guardar informações de acertos e erros dos usuários, assim como as questões já realizadas

#### Usuários criados:
 - user/password: mickey
 - user/password: minnie
 - user/password: donald