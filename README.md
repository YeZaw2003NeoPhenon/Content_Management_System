# Content Management System (CMS)

## Project Description
I have created a Content Management System (CMS) using modern technologies and frameworks to provide a robust, secure, and efficacious platform for managing content. This CMS supports CRUD (Create, Read, Update, Delete) operations, user authentication . However, this project allows user to do user credentials creation, as well as managing unblemished user authorizations), and image handling through multipart files.

## Features
- ``User Authentication``: Secure login and registration using Spring Security.
- ``User Management``: Login and logout functionalities with Spring Security.
- ``CRUD Operations``: Easily create, read, update, and delete posts.
- ``Image Handling``: Upload and display images using multipart files.
- ``Responsive Design``: User-friendly and responsive UI using Thymeleaf templates with Bootstrap.
- ``Database Integration``: Data persistence using PostgreSQL and MyBatis.
- ``Logging``: Efficient logging using Logback.

## Spring Backend

The backend is implemented using Spring MVC and Spring Security, providing a RESTful API for managing posts and user authentication. It utilizes MyBatis for database interaction and integrates with PostgreSQL for data persistence.

## System Requirements
## Authorizations

- **Admin Access**: Admin users have full CRUD access to posts and user management features.
- **User Access**: Regular users can create, read, update, and delete their own posts.

## Technologies
-------------------------
1. Spring MVC
2. Spring Security
3. Thymeleaf (Html bootstrap javascript)
4. lambork
5. postgresql database
6. mybatis framework
7. File Handling
 
### Running the Application

To run the application locally:

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/your-repo.git
    ```
2. Navigate to the project directory:
    ```bash
    cd your-repo
    ```
3. Build the application:
    ```bash
    mvn clean install
    ```
4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

