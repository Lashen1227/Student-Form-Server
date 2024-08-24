# Student Form - JavaEE

## Overview

The **Student Form** project is a web-based application built using JavaEE, Apache Tomcat, and MySQL. It provides a simple frontend interface for managing student data through a form. This project supports full CRUD  operations on student records stored in a MySQL database, with data handling through JDBC. Additionally, APIs are managed using Postman for testing and interaction.

## Features

- **Add Student**: Submit a form to add a new student to the database.
- **View Students**: Retrieve and display a list of all students.
- **Update Student**: Modify existing student details.
- **Delete Student**: Remove a student from the database.
- **Database Integration**: Uses MySQL for persistent data storage.
- **API Testing**: Utilize Postman for testing APIs.

## Project Structure

- **Front End**: Contains the HTML, CSS, and JavaScript files.
  - `index.html`: Main HTML file for the form interface.
  - `style.css`: CSS file for styling the form and other UI components.
  - `script.js`: JavaScript file for client-side form validation and interactions.
- **Server**: Contains JavaEE backend code to handle HTTP requests and perform CRUD operations.
- **Database**: MySQL database to store student data, connected using JDBC.

## Installation and Setup

### Prerequisites

- Java Development Kit (JDK 17 or higher)
- Apache Tomcat Server
- MySQL Server
- Postman (for API testing)
- CORS Unblock Chrome Extension

### Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Lashen1227/Student-Form-Server.git
   ```
   
2. **Import the Project in Your IDE**:
   - Open your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
   - Import the project as a Maven or JavaEE project.

3. **Configure Apache Tomcat**:
   - Download and install Apache Tomcat.
   - Configure the server in your IDE or use the terminal.

4. **Setup MySQL Database**:
   - Install MySQL and create a database named `student_form_javaee`.
   - Run the SQL scripts provided in the `database` folder to create the necessary tables.

5. **Connect JDBC to MySQL**:
   - Ensure the JDBC driver is added to your project’s classpath.
   - Update the database connection settings in the `Server` code to match your MySQL configuration.

7. **Run the Project**:
   - Deploy the project on Apache Tomcat server.
   - Open `http://localhost:8080/students` in your browser.
   - Ensure the CORS Unblock extension is enabled to allow interactions between the frontend and backend.

## Usage

- Open the form in your browser to add, update, view, or delete student records.
- Use Postman to send HTTP requests to the backend APIs:
  - **GET** `/students` - Retrieve all students.
  - **POST** `/students` - Add a new student.
  - **PUT** `/students/{id}` - Update an existing student.
  - **DELETE** `/students/{id}` - Delete a student.

## Technologies Used

- **Backend**: JavaEE, Apache Tomcat, JDBC
- **Frontend**: HTML, CSS, JavaScript
- **Database**: MySQL
- **API Testing**: Postman
