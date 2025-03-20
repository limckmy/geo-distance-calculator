# Geo Distance Calculator

## Overview
Geo Distance Calculator is a REST API that calculates the straight-line (great-circle) distance between two UK postal codes. It uses the Haversine formula to compute the distance based on latitude and longitude.

## Features
- RESTful API to compute the geographic distance between two UK postcodes.
- Uses an offline dataset from [FreeMapTools](https://www.freemaptools.com/download-uk-postcode-lat-lng.htm) for postcode-to-location mapping.
- Implements the Haversine formula for accurate distance calculation.

## Technologies Used
- Java 17
- Spring Boot
- Maven
- RESTful API
- JUnit (for testing)

## Installation & Running the Project

### Prerequisites
- Java 17+
- Maven
- UK postcode dataset from [FreeMapTools](https://www.freemaptools.com/download-uk-postcode-lat-lng.htm) (CSV format)
- Ensure the dataset is placed in `src/main/resources/db/data/ukpostcodes.csv`
- A relational database (PostgreSQL) for data storage

### Clone the repository
```bash
git clone https://github.com/limckmy/geo-distance-calculator.git
cd geo-distance-calculator
```

### Running the Application
```bash
mvn clean package
```
```bash
docker-compose up
```

```bash
java -jar target/geo-distance-calculator-1.0-SNAPSHOT.jar
```

### Testing 
1. Import postman [collection](Geo-Distance.postman_collection.json)
   1. Register User : `POST /v1/auth/register`
   2. Generate Token : `POST /v1/auth/login`
   3. Calculate Distance : `GET /v1/distance?postcode1=AB10 1XG&postcode2=AB12 5GL`


### Postgres access
See [docker-compose.yml](docker-compose.yml)

- PgAdmin web console : http://localhost:5050
    - login
        - email : admin@limckmy.org
        - password : admin
    - register server
        - hostname : postgres_db
        - port : 5432
        - database : geo-distance
        - username : postgres
        - password : password


### Reference
- [OpenAPI](openapi.json)
- [Swagger UI](http://localhost:8080/openapi/swagger-ui.html)