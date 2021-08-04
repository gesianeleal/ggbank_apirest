
# Bank GG
Sistema de gestão de contas

#### Recursos

- [Maven](https://maven.apache.org/) 
- [MySQL](https://www.mysql.com/)
- [Spring Boot](https://start.spring.io/) 
- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [PMD-SCAN](https://pmd.github.io/)

#### Dicas para rodar

- Banco de dados em Memória H2 (branch `banco_h2`)

```shell scrip
 mvn spring-boot:run
```

ou 

- Banco de dados em MySQL (branch `master`)

```shell scrip
docker build -t bankgg .

docker run -p 3306:8080 -e SPRING_PROFILES_ACTIVE='prod' -e DATABASE_URL='jdbc:mysql://localhost:3306/gg' -e DATABASE_USERNAME='root' -e DATABASE_PASSWORD='1234' bankgg 
```
 
#### Documentação

API REST de account com Swagger-ui: http://localhost:8080/swagger-ui.html


#
### Exemplos de utilização da API

#### Endpoints de contas
Na criação da conta é retornado o id.
 
```shell scrip
# cria conta
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ "documentNumber": 79878979 }' 'http://localhost:8080/accounts'

# consulta conta por id
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/accounts/1'
```

Tanto na criação como na consulta deve retornar.
```json
{
    "accountId": 2,
    "documentNumber": 89789879
}
```

#### Endpoints de transação
Na criação da transação é retornado o id.
 
```shell scrip
# Lista todas transações
curl -X GET --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"accountId": 0, "page": 1,  "qtd": 10 }' 'http://localhost:8080/transactions'
```

Na criação da transação deve retornar.
```json
{
    "transactionId": 1,
    "accountId": 1,
    "operationtypeId": 2,
    "amount": -9.87,
    "eventDatetime": "2021-08-04T12:58:35.755+00:00"
}
```
