### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

I have 14 years of experience as a developer spread across many industries and languages.
I have 5 years of experience using Java. 4 years using Spring and 1 year using Spring Boot.

Below are the applications I developed in Java:
- Trading platform using CQRS Axon Framework and fix protocol.
- Financial static data normalizer.
- Low latency/high throughput trading test application to test quantum network for government project.
- 2FA login system.


#### Regarding the submission

Below is what I have changed.
- System tests have been added for `EmployeeController` endpoints.
- Logs have been improved. A request id is generated and added to each request. The implemented logger will
  retrieve the request id from the request context and prepend it to each log message.
- Some documentation have been added to the swagger client.
- Improved error handling.

I implemented what I considered to be high priority changes. 

There is no specific context so I did not change api signatures or things which requires consideration of the
requirements. I believe that in most cases, some architecture pattern already exist in real environments regarding
authentication/authorization. I did not implement anything regarding security as the implementation would most likely
be dictated by the company solution. 
