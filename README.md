##### Bitcoin to USD - version 0.1
Bitcoin to USD is a simple Spring boot application which provide below two services
- Latest Bitconin to USD currency exchange rate
- USD exchange rates between two dates

##### Design

##### Controller / REST Access

  Entry point to this application is a Spring controller class (BitCoinRateController) which exposes two REST Endpoints

  1. /rate/latest

    Method Type: GET

    Function: Provide the latest captured Bitcoin to USD currency exchange rate

  2. /rate/{startdate}/{enddate}

    Method Type: GET

    Function: Provide list of exchange rates for Bitcoin to USD between the given start and end date

    **Please note: start and end date should be in DDMMYYYY format**

    Example: /rate/01022019/10052019 represents dates between 01-FEB-2019 to 10-MAY-2019

##### Service / Business logic

The core of the application is a service class (BitcoinExchangeService) which provide below three functionalities

1. A scheduled job which capture current Bitcoin to USD exchange rate and store that in DB.
> When the job gets executed an external endpoint (ExchangeRateProvider) will be called to get the current exchange rate. In order to fetch data from a different external endpoint, create a new ExchangeRateProvider implementation.

2. Method to provide latest captured currency exchange rate
3. Method to provide list of exchange rates between the dates provided as input

**Please note: The scheduler gets executed on a fixed interval which is a configurable value using application.properties file**

##### Data Access & Persistence

  DAO pattern is used to abstract the database access which makes it easy for the business logic (service) to handle data access. At present the data is stored in H2 DB but can be migrated to any persistence storage by just changing the Hibernate dialect.

##### Changing default exchange currency

  Default exchange currency is configured as USD.
  Changing that to a different currency (say EUR) can be done by just changing the below property in application.properties file

  default.exchange.currency=EUR



Java Version
> 1.8 or above

##### How to test ?
run > mvn test

##### How to build ?
run > mvn install

##### How to run ?

cd target

run> java -jar currencyexchange-0.0.1-SNAPSHOT.jar



#### How to access Latest Currency Exchange rate?

```
GET http://localhost:8080/rate/latest

response :

{
   "currency":"USD",
   "rate":5264.99,
   "sampleDate":"2019-04-30T09:03:29.627+0000"
}


```

#### How to fetch exchange rates between two dates
```
GET http://localhost:8080/rate/10012019/10052019

response :
[
   {
      "currency":"USD",
      "rate":5265.50,
      "sampleDate":"2019-04-30T09:03:01.208+0000"
   },
   {
      "currency":"USD",
      "rate":5265.50,
      "sampleDate":"2019-04-30T09:03:09.784+0000"
   },
   {
      "currency":"USD",
      "rate":5264.99,
      "sampleDate":"2019-04-30T09:03:19.718+0000"
   },
   {
      "currency":"USD",
      "rate":5264.99,
      "sampleDate":"2019-04-30T09:03:29.627+0000"
   },
   {
      "currency":"USD",
      "rate":5264.99,
      "sampleDate":"2019-04-30T09:03:39.075+0000"
   },
   ....
]

```

##### Enhancements

- Application can be enhanced to capture multiple currency exchange rates and depending on the user need any currency exchange rate can be served.

- Unit testing is limited to Controller class. As an enhancement all classes needs to be covered.

- REST endpoint path variable validations needs to be added. For example endDate can not be greater than startDate. Valid date format.

- Error handling: Needs to handle failures during fetching currency rate from external service.

##### Postman scripts(Automation)

- Postman script for adding and getting jobs is placed under /automation folder.
