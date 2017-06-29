# Forum

### Build & Run
    mvn clean spring-boot:run 

# Technology Stack

### Programming languages
- [**Java**](https://www.java.com/en/) - object-oriented programming language (1.8)
- [**Groovy**](http://groovy-lang.org/) - object-oriented programming language, in this project is used for testing (2.4.6)

### Frameworks
- [**Maven**](https://maven.apache.org/) - dependency management
- [**Spring**](https://spring.io/) - Web MVC framework (4.3.7.RELEASE) 
- [**SpringBoot**](https://projects.spring.io/spring-boot/) - Web MVC framework (1.5.2.RELEASE) 
- [**Hibernate**](http://www.hibernate.org/) - ORM (hibernate-jpa-2.0-api)
- [**Spock**](http://spockframework.org/) - framework for testing (1.1-groovy-2.4)

### DataSources
- [**PostgreSQL**](https://www.postgresql.org/) - PostgreSQL database connector (42.0.0)
- [**H2**](http://www.h2database.com/html/main.html) - H2 database connector (1.4.195)

### Plugins
- [**Lombok**](https://projectlombok.org/) - (1.16.10)



REST Endpoints
==============

We try to follow well-accepted REST principles with these examples.

**Before run the commands need to login !** 

### User requests

* Create a new user: <code>POST http://localhost:8080/api/v1/user</code>
* Read user with ID = X: <code>GET http://localhost:8080/api/v1/user/X</code>
* Read all users (You must authorization in with administrator privileges): <code>GET http://localhost:8080/api/v1/users</code>
* Update user with ID = X: <code>PUT http://localhost:8080/api/v1/user/X</code>
* Delete user with ID = X: <code>DELETE http://localhost:8080/api/v1/user/X</code>


### Post requests

* Create a new post: <code>POST http://localhost:8080/api/v1/user/userId/post</code>
* Read post with ID=X: <code>GET http://localhost:8080/api/v1/user/userId/post/X</code>
* Read all posts: <code>GET http://localhost:8080/api/v1/posts</code>
* Update post with ID = X: <code>PUT http://localhost:8080/api/v1/user/userId/post/X</code>
* Delete post with ID = X: <code>DELETE http://localhost:8080/api/v1/user/userId/post/X</code>

### Comment requests

* Create a new comment: <code>POST http://localhost:8080/api/v1/user/userId/post/postId/comment</code>
* Read comment with ID = X: <code>GET http://localhost:8080/api/v1/user/userId/post/postId/comment/X</code>
* Read all comments: <code>GET http://localhost:8080/api/v1/comments</code>
* Update comment with ID = X: <code>PUT http://localhost:8080/api/v1/user/userId/post/postId/comment/X</code>
* Delete comment with ID = X: <code>DELETE http://localhost:8080/api/v1/user/userId/post/postId/comment/X</code>

#Authors

* [**AlexanderNyrkov**](https://github.com/AlexanderNyrkov)