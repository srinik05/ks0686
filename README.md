# Rental App 

The Rental App is a Spring Boot application for managing tool rentals. It allows users to check out tools for a specified duration and provides rental agreements with details such as tool code, tool type, rental days, charges, discounts, and final charges.

## Table of Contents
- Features 
- Technologies Used
- Setup Instructions
- Usage
- Endpoints
- Tests
- Contributing
- License

## Features

  - Check out tools by providing rental details.
  - Calculate rental agreements based on rental duration, discounts, and tool type.
  - Handle exceptions for invalid requests or tools not found.
  - RESTful API endpoints for tool checkout.

## Technologies Used

- Java 
- Spring Boot
- Rest API
- JUnit (for unit testing)
- Maven
- Sonar Lint

## Getting Started

These instructions will help you set up and run the Rental Application on your local machine for development and testing purposes.

### Prerequisites

- Java 11 / 17
- Maven
- IntelliJ IDEA or any preferred IDE
- Sonar Lint for code coverage 

### Installation

1. Clone the repository to your local machine:

   ```shell
   git clone https://github.com/srinik05/sk0686.git
   
## Steps: 
1. Import the project into your IDE (e.g., IntelliJ IDEA) as a Maven project.
2. Build the project using Maven:

### mvn clean install

mvn spring-boot:run
or
java -jar target/rental-app.jar

The application should now be running at http://localhost:8080/api/tools/checkout 

## Usage
To use the Rental App, you can send HTTP requests to the provided endpoints (see Endpoints) to check out tools and receive rental agreements.

## API Endpoints
The following API endpoints are available:

### POST /api/tools/checkout -  Checkout a tool by providing a JSON payload containing rental details.
#### Example Payload:
{
  "toolCode": "LADW",
  "rentalDays": 5,
  "discountPercent": 10,
  "checkoutDate": "2024-03-01"
}

#### Example Response:

{
  "toolCode": "LADW",
  "toolType": "LADDER",
  "toolBrand": "Werner",
  "rentalDays": 5,
  "checkoutDate": "2024-03-01",
  "dueDate": "2024-03-06",
  "dailyRentalCharge": 1.99,
  "chargeDays": 5,
  "preDiscountCharge": 9.95,
  "discountPercent": 10,
  "discountAmount": 0.995,
  "finalCharge": 8.955
}

Refer to the API documentation or Postman collection for more details on how to use these endpoints.

## Error Handling
The application implements exception handling to provide appropriate error responses. Custom exceptions have been defined to handle specific scenarios. Error responses include a code, message, and trace ID for easier debugging.

## Testing
## POSTMAN Collection:
curl --location --request GET 'http://localhost:8080/api/tools/checkout' \
--header 'Content-Type: application/json' \
--data '{
    "toolCode": "LADW",
    "rentalDays": 1,
    "discountPercent": 1,
    "checkoutDate": null
}'

Unit tests and integration tests have been provided to ensure the functionality of the application. You can run the tests using Maven:
### mvn test

## POSTMAN Scenarios
### Postive case
![image](https://github.com/srinik05/ks0686/assets/83652004/e92d8b1a-9787-4e60-9544-3034f7b01f35)

### Negative Cases
#### ToolCode is invalid
![image](https://github.com/srinik05/ks0686/assets/83652004/7d67ad97-6cdb-4c27-9571-7e20bcc6fefe)

#### RentalDays is invalid or null
![image](https://github.com/srinik05/ks0686/assets/83652004/28d721d2-3484-4b6d-bbf3-db3625cd8f40)

#### Discount Percent is not valid
![image](https://github.com/srinik05/ks0686/assets/83652004/fba27b15-865e-43fe-9cc1-767bcefb0066)

#### CheckoutDate is invalid
![image](https://github.com/srinik05/ks0686/assets/83652004/af9781f2-3210-4f10-8db4-1a1a3f87d832)




