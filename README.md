# Book Store API !
Welcome to the Book Store API.
This specialized API is designed to provide and simplify bookstore management services.
You can use it as well to manage the business processes of your existing store.
And to expand it for a larger number of users, as an online store.

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Technologies and Tools Used](#technologies-and-tools-used)
- [Controller Functionalities](#controller-functionalities)
- [Installation and Usage](#installation-and-usage)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)

## Description
This project is a Spring Boot implementation of an Online Book Store.
It encompasses several domain models, including User, Role, Book, Category, ShoppingCart, CartItem, Order, and OrderItem.
Users have the ability to register, sign in, browse books, add them to their shopping cart, and place orders.
Administrators, on the other hand, can manage books, bookshelf sections, as well as view and update order statuses.

## Features
- Enjoy a thrilling search experience with real-time book data. This includes the ability to create, retrieve, update, and delete books.
- Effortlessly navigate genres. Administrators have the power to create, update, and delete categories, as well as retrieve books by category.
- Tailor your reading list with ease. Users can create, retrieve, update, and delete items in their shopping cart.
- Seamlessly handle orders. Create orders, update order status, and retrieve order history hassle-free.
- Easily find books using various filters, such as title, author, price range, and more.
- Our system ensures secure authentication through the implementation of robust security measures, including JWT-based authentication.
- Explore and interact with our API effortlessly using the comprehensive Swagger documentation.

## Technologies and Tools Used

This application is written entirely in the Java programming language using Spring Boot framework.
Also using such technologies as:
- **Spring Data JPA:** For efficient data access and persistence.
- **Spring Security:** To secure the application and user data.
- **MapStruct:** For simplified and efficient mapping between DTOs and entities.
- **Docker:** Containerization for easy deployment and scalability.
- **OpenAPI**: For documenting our RESTful APIs, providing clear and detailed information about available resources,
request payloads, and response formats.
- **Liquibase**: Used to manage and version the database schema, allowing for easy database schema changes 
and ensuring data schema consistency across different environments.

## Controller Functionalities

Our API consists of several controllers, each serving a specific purpose:
- **AuthenticationController**: Responsible for user authentication and registration.
- **BookController**: Performs book-related operations including creating, searching, updating, and deleting books.
- **CategoryController**: Manages categories by allowing administrators to create, update, delete categories, and retrieve books by category.
- **ShoppingCartController**: Manages shopping cart information, allowing users to create, retrieve, update, and delete cart items.
- **OrderController**: Manages orders, including creating orders, updating order status, and retrieving order history.

## Installation and Usage

### Prerequisites

Before you start testing the application, you should make sure that you have installed such technologies as:
- Java Development Kit (JDK);
- MySQL/PostgreSQL or another preferable relational database (not required if running through docker);
- Maven (not required if using maven wrapper);
- Docker (for running project in virtual container);

### Installation

**Running on local machine:**
- Clone repository with the project:
`` git clone https://github.com/OlegStashkiv/Online-Books-Store ``
- Navigate to the project directory: `` cd book-store ``
- Build the project: `` mvn clean install ``
- Run the application: `` mvn spring-boot:run ``
> **Important**: before running application on your local machine you need to configure database connection
> properties in *application.properties* file.

**Running in docker:**

- Repeat 2 first steps from previous step
- Build and run the docker containers: `` docker-compose up -d ``
- Access the application
- Stopping the container: `` docker-compose down ``

> **Additionally**: To work with the application, you can use a previously prepared PostMan collection. 
> This collection is attached to the project: _Book Store.postman_collection_

In addition you can watch a short video to get acquainted with the work flow:
 - [https://www.loom.com/share/c8e1fe76396d4278878da3d5fd323cd1?sid=7bf1a99b-294d-4eea-ae54-cce4fa43c443](https://www.loom.com/share/4a725b65c3d742ee96acde9c2ae7d6cd?sid=c25cab66-f402-4925-8799-8f4eb513b5a1)https://www.loom.com/share/4a725b65c3d742ee96acde9c2ae7d6cd?sid=c25cab66-f402-4925-8799-8f4eb513b5a1
