# Jr WebAPI Developer Assignment - Rewards API (Spring Boot)

## Description

Small RESTful API that calculates customers' reward points from purchase history for a given interval (default is 3 months). 
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction. 
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Assumptions:

1. A purchase, aka transaction, can include multiple products in varying quantities 
2. There should be one endpoint that returns a summary of all customers’ reward points as a json
3. H2 database is enough to demonstrate solution 
4. Points should be calculated on the fly (not stored in the db) 
5. The reward rules are stored in the DB, not hardcoded 
6. The app’s default time zone is America/Chicago
7. The default period of time for the summary is 3 months.
   
## Technologies

The Spring framework 6.2.10 (Spring Boot 3.5.5, Spring Web, Spring Data), H2 database

## Installation

* Clone the repository
```
git clone https://github.com/nvp85/rewards-program-api.git
```
* In the project folder run
```
mvn spring-boot:run
```
The app will run on http://localhost:8080.

## EndPoint
```
GET /api/rewards/summary
```
Query parameters (all optional):

fromDate — inclusive start date (YYYY-MM-DD). Default: today − 3 months.

toDate — inclusive end date (YYYY-MM-DD). Default: today.

timeZone - IANA tz identifier, default: America/Chicago.

Example:
```
curl "http://localhost:8080/api/rewards/summary?fromDate=2025-06-01&toDate=2025-08-31"
```

Response JSON example:
```
    {
        "fromDate": "2025-06-01T00:00:00-05:00",
        "toDate": "2025-09-30T23:59:59-05:00",
        "customers": [
            {
                "customerId": 1,
                "customerName": "Alice",
                "totalPoints": 380,
                "pointsPerMonth": [
                    {"month": "2025-07", "points": 224},
                    {"month": "2025-08", "points": 74},
                    {"month": "2025-09", "points": 82}
                ]
            },
            {
                "customerId": 2,
                "customerName": "Bob",
                "totalPoints": 314,
                "pointsPerMonth": [
                    {"month": "2025-07", "points": 0},
                    {"month": "2025-08", "points": 192},
                    {"month": "2025-09", "points": 122}
                ]
            }
        ]
    }
```
## Seeding Data for tests

```
src/main/resources/data.sql
```

