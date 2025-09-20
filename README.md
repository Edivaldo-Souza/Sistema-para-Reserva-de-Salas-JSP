# Tutorial de Instalação
#### 1- Criar  arquivo .env na mesma pasta do arquivo docker-compose.yml com o seguinte conteúdo
```
COMPOSE_PROFILES=dev
```
#### 2 - Criar arquivo .env na pasta reservasalas com o seguinte conteúdo
```
DB_URL=jdbc:postgresql://db:5432/reservasalas
DB_USER=postgres
DB_PASSWORD=postgres
```

#### 3 - Na pasta com o arquivo docker-compose.yml executar o seguinte comando

```
docker compose build
```

#### 4 - Na mesma pasta executar o seguinte comando

```
docker compose up
```
Obs: Caso o passo não 5 funcione, execute o passo 4 mais uma vez e tente novamento o passo 5

#### 5 - Inserir a seguinte url em um navegador 

```
http://localhost:8081/user/login
```

#### 6 - Credenciais iniciais do administrador

```
nome:admin
senha:admin
```