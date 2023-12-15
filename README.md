# Rest API applying TDD methodology

## Description
This is a simple REST API to simulate a simple university system (at moment only students and courses) 
applying TDD methodology. I would like to make a special mention to the Youtube playlist `https://www.youtube.com/playlist?list=PLqq9AhcMm2oPdXXFT3fzjaKLsVymvMXaY`
that helped me a lot to understand the TDD methodology and how to apply it in a real project.

## Folder structure for the project
All the implementation code (business logic) is in the folder `src/main/java` and the tests are in the folder `src/tests`.

### inside `src/main/java` follow the next structure:
```
└── 📁tdduniversity 
    └── TddUniversityApplication.java      # Entry point of the application
    └── 📁course                           # All the code related to the course entity
        └── 📁converters                   # Converters to convert from entity to dto and viceversa
        └── 📁dtos                         # Data transfer objects
        └── 📁exceptions                   # Custom exceptions for the course entity
    └── 📁security                         # All the code related to the security of the application
    └── 📁student                          # All the code related to the student entity (similar structure as course)
        └── 📁converters
        └── 📁dtos
        └── 📁exceptions
    └── 📁system                           # All the code related to the system entity (utility classes and exception handler)
    └── 📁user                             # All the code for handling users
```

### inside `src/test` follow the next structure:
```
└── 📁tdduniversity
    └── TddUniversityApplicationTests.java          # Entry point of the tests
    └── 📁course                                    # All the tests for the course entity
        └── CourseControllerIT.java                 # Integration tests for the course controller
        └── CourseControllerTest.java               # Unit tests for the course controller
        └── CourseServiceTest.java                  # Unit tests for the course service
    └── 📁student                                   # All the tests for the student entity (similar structure as course tests)
        └── StudentControllerTest.java
        └── StudentServiceTest.java
    └── 📁user                                      # All the tests for the user entity (similar structure as course tests)
        └── UserControllerTest.java
        └── UserServiceTest.java
```

## Before running the application 
1. Install Java 17 JDK
2. Install Docker Desktop
3. Install Postman (optional) or any other tool to test the API
4. Install IntelliJ IDEA (recommended)


## How to run the application
1. Clone the repository
2. Open the project with your favorite IDE (I recommend to use IntelliJ IDEA)
3. Run Docker Desktop server
4. Run the command `docker compose up` in the root folder of the project
5. Run the application, executing the main method of the class `TddUniversityApplication.java`
6. Test the application with Postman or use the Swagger UI at `http://localhost:8080/swagger-ui.html`

### For test endpoints with Swagger UI or Postman (use Basic Auth) you need to authenticate first with the following credentials:
```
username: user1
password: password1
```
I included a file called `tdd-university.postman_collection.json` with endpoints to test the API with Postman.

### Set up your own database (optional)
For testing purposes this project use `Postgres` image from Docker, 
 so you don't need to install Postgres in your machine. But If you want to use your own Postgres database,
 you can change the configuration in the file `application-dev.yml` and set the values for the properties 
so you don't need to run steps 3 and 4 of the section `How to run the application`.

### If you have any question or suggestion, please let me know. Thanks!