Steps to set up:-

clone the repo:- 

git clone https://github.com/Avi9012/E_Commerce.git

set ENVIRONMENT variables:- 

DATABASE_NAME (Your MySql database name)

DATABASE_USERNAME (Your MySql server username)

DATABASE_PASSWORD (Your MySql server user password)

run **.\gradlew build -x test** to build the project

run **java -jar .\build\libs\MyECommerce-0.0.1-SNAPSHOT.jar** to start application

Example API request/response:-

API endpoint to place order:- **http://localhost:8080/v1/myECommerce/metrics**, METHOD:- POST

Request Payload:-

```
{
    "userId": "bfea8457-783b-4180-9b9b-05524dd1c065",
    "totalAmount": "80",
    "itemIds": [
        "ord1",
        "ord2",
        "ord3"
    ]
}
```

Expected Response:-

```
{
    "orderId":"4615fb66-4c04-441e-915a-f1a70394014e",
    "userId":"bfea8457-783b-4180-9b9b-05524dd1c065",
    "totalAmount":80.0,
    "status":"PENDING",
    "itemIds":[
        "ord1",
        "ord2",
        "ord3"
    ],
    "createdTime":"2025-03-04T18:09:50.3428263+05:30",
    "completionTime":null
}
```

API Endpoint for metrics:-

**http://localhost:8080/v1/myECommerce/metrics**, METHOD:- GET

Expected Response:-

```
{
    "totalOrdersProcessed": 4,
    "averageProcessingTime": 38.25,
    "pendingOrders": 0,
    "processingOrders": 0,
    "completedOrders": 4
}
Note:- averageProcessingTime is in seconds
```

API Endpoint for order status:-

http://localhost:8080/v1/myECommerce/orderStatus/02879834-b53e-41fa-b6e9-4cf860afc9bd

Expected Response:-

COMPLETED

**Design Decisions and tradeOffs -**

Using MVC (Model-View-Controller) architecture, where controllers will handle incoming requests, service will execute business logic and repositories will handle database operations. 

**Assumptions made during development -**
1. User and items are already exist, therefore **/order** API is expecting that userId and itemIds are valid.
2. User will place a order with at least 1 item and total amount will be greater than 0.