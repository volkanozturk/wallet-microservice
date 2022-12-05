# LeoVegas Wallet Microservice

A simple wallet microservice running on the JVM that manages credit/debit transactions on behalf of players. It publishes a Swagger page with details about the endpoints.
A Postman file is also attached

The documentation could be found at http://localhost:8080/api/swagger-ui/index.html

## Installation

Application can be run with WalletApplication class under com.leovegas.wallet package.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```sh
mvn spring-boot:run
```

Build a Docker image using Maven.
Ensure you have a local Docker installed and running, then type
```sh
./mvnw spring-boot:build-image
```

## Run Actuator

[Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/) can be reached from [local url for Actuator](http://localhost:8080/actuator).

Only health and caches endpoints are enabled by default. Configuration can be updated within the "actuator" section of the related application.properties file.

## Javadoc

You can create Javadoc with the below command or directly from your IDE.

```shell
mvn javadoc:javadoc
```

## Run Swagger UI Documentation

After running the application, just type the  [local url for Swagger UI](http://localhost:8080/swagger-ui/index.html) in your browser.

## Testing

The tests are created using Junit, SpringBoot Test. The unit- and integration tests can be found at
the `/test` folder.

## Code Coverage
Code coverage is not considered for model and dto classes.

| Controller                | Method, %   | Line, %      |
|---------------------------|-------------|--------------|
| PaymentRestController     | 100% (3/3)  | 100% (6/6)   |
| TransactionRestController | 100% (2/2)  | 100% (6/6)   |
| WalletRestController      | 100% (3/3)  | 100% (8/8)   |

| Service                  | Method, %  | Line, %      |
|--------------------------|------------|--------------|
| PaymentServiceImp        | 100% (8/8) | 100% (37/37) |
| TransactionServiceImp    | 100% (4/4) | 100% (8/8)   |
| WalletServiceImp         | 100% (7/7) | 100% (14/14) |

## Used tools and frameworks

Multiple tools are used for developing the Recipe

- Spring Boot (Including the dependencies)
- Mapstruct
- Junit
- Mockito
- H2
- Maven
- OpenAPI

## API details
- Get Player Balance
	- This service can retrieve balance of a specific player. It is GET request which accepts JSON body for balance.

1. [Get Balance By Player](http://localhost:8080/api/v1/wallets/balance)
```
  {
   "playerId": "8271419890"
  }
```
Response:
```
{
    "successful": true,
    "status": 200,
    "data": {
        "createdAt": "2022-12-04 09:40",
        "lastUpdatedAt": "2022-12-04 09:40",
        "playerId": "8271419890",
        "balance": 100.00
    }
}
```

- All Player Balance
	- This service can retrieve balance of all players. It is GET request which accept JSON body for all balance.

2. [Get All Balance Details](http://localhost:8080/api/v1/wallets/all-balance?page=0&size=10)
```
- No Request required. But you might be added params for page and size.
```
Response:
```
{
    "successful": true,
    "status": 200,
    "data": [
        {
            "playerId": "8271419890",
            "balance": 100.00,
            "createdAt": "2022-12-04 09:40",
            "lastUpdatedAt": "2022-12-04 09:40"
        },
        {
            "playerId": "9751473310",
            "balance": 200.00,
            "createdAt": "2022-12-04 09:26",
            "lastUpdatedAt": "2022-12-04 09:26"
        },
        {

            "playerId": "6189707377",
            "balance": 455.00,
            "createdAt": "2022-12-04 09:26",
            "lastUpdatedAt": "2022-12-04 09:26"
        }
    ]
}
```
- Transaction History
	- This service can retrieve transaction history per player. It is GET request which accept JSON body for all balance.
1. [Transaction History Per Player](http://localhost:8080/api/v1/transactions/history)

```
  {
   "playerId": "8271419890"
  }
```
Response:
```
{
    "successful": true,
    "status": 200,
    "data": [
        {
            "transactionId": "7cb024ab-94d2-4a9a-8448-dbb9be1c1d24",
            "amount": 20.00,
            "transactionType": "DEBIT",
            "createdAt": "2022-12-04 09:37",
            "lastUpdatedAt": "2022-12-04 09:37"
        },
        {
            "transactionId": "423e05a0-b9c2-41d9-b953-a2d549b4a83d",
            "amount": 30.00,
            "transactionType": "CREDIT",
            "createdAt": "2022-12-04 09:40",
            "lastUpdatedAt": "2022-12-04 09:40"
        }
    ]
}
```

- Debit Player Transaction
	- This service is used to create a new debit transaction. It is PUT request which accepts JSON body for debit transaction.

1. [Debit Player Transaction](http://localhost:8080/api/v1/payments/debit)

```
{
  "playerId": "8271419890",
  "amount" : 20,
  "transactionId" : "7cb024ab-94d2-4a9a-8448-dbb9be1c1d24"
}
```
Response:
```
{
    "successful": true,
    "status": 200,
    "data": {
        "playerId": "8271419890",
        "balance": 70.00,
        "lastUpdatedAt": "2022-12-04 10:14"
    }
}
```

- Credit Player Transaction
	- This service is used to create a new credit transaction. It is PUT request which accepts JSON body for credit transaction.

2. [Credit Player Transaction](http://localhost:8080/api/v1/payments/credit)

```
{
  "playerId": "8271419890",
  "amount" : 30,
  "transactionId" : "423e05a0-b9c2-41d9-b953-a2d549b4a83d"
}
```
Response:
```
{
    "successful": true,
    "status": 200,
    "data": {
        "playerId": "8271419890",
        "balance": 100.00,
        "lastUpdatedAt": "2022-12-04 10:15"
    }
}
```
