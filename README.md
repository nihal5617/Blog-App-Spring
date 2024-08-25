# Blog App Backend in Java Spring Boot

## Descripton
A blog app allows users to create and share their blog posts with a community of readers. Users can write about various topics, express their opinions, and share experiences. The app also enables users to read, comment, and engage with posts from other users, fostering a dynamic, interactive environment. It encourages community interaction through comments, likes, and follows, creating a platform for discussions, feedback, and idea sharing among bloggers.

## How to Run

### 1. Maven Build the Project
If you have installed Maven on your machine then use the below command:
```
mvn clean package
```
If you haven't insatlled Maven on your machine then use below command:
```
./mvnw clean package
```
Note: Go to root directory of the project and execute above command.

### 2. Create a Database
Before running Spring boot blog application, you need to create the MySQL database.
Use the below SQL database to create the MySQL database:
```sql
create database blog_app
```
Database name - blog_app

### 3. Run Spring Boot Project
Use below command to run Spring boot application:
```
mvn spring-boot:run
 ```
Once you run Spring boot application, Hibernate will create the database tables automatically.

### 4. Insert Data
User below Insert SQL statements to insert records into roles table:
```sql
INSERT INTO `myblog.roles` VALUES (1,'ADMIN'),(2,'USER');
```
Now, Spring boot blog application is ready to use.

### 5. Register a Admin and User
Use the postman collection to register user, change the role to Admin for admin user from the code src/main/java/com/springboot/blogapp/service/impl/AuthServiceImpl.java line 70

### You are all Good to run the other requests!!!

