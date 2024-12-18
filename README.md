# Documentation for API Metrics Tracking Spring Boot Project

## Table of Contents
1. [Project Overview](#project-overview)
2. [Tech Stack](#tech-stack)
3. [Architecture](#architecture)
4. [Low-Level Design](#low-level-design)
5. [API Endpoints](#api-endpoints)
6. [Testing](#testing)
7. [Conclusion](#conclusion)

---

## Project Overview

This project is a RESTful API built using Spring Boot that tracks the metrics of various APIs, including total hits, successful hits, and failed hits. The application interacts with a MySQL database to store and retrieve metrics data.

## Tech Stack

- **Backend Framework**: Spring Boot
- **Database**: MySQL
- **Java Version**: Java 11 or higher
- **Build Tool**: Maven
- **Development Environment**: IntelliJ IDEA or Eclipse
- **API Testing Tool**: Postman or cURL

## Architecture

The architecture of the application follows a layered approach, which includes:

1. **Controller Layer**: Handles incoming HTTP requests and returns responses. It acts as an interface between the client and the service layer.
2. **Service Layer**: Contains the business logic of the application. It interacts with the repository layer to perform CRUD operations.
3. **Repository Layer**: Interacts with the database using Spring Data JPA. It contains methods to execute custom queries for retrieving metrics.
4. **Model Layer**: Represents the data structure of the application. In this case, it includes the `ApiMetrics` entity.


## Low-Level Design

### 1. Model Class

**ApiMetrics.java**
```java
package com.example.apimetrics.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ApiMetrics {
    @Id
    private String apiName;
    private int totalHits;
    private int successfulHits;
    private int failedHits;

    // Getters and Setters
}
```
2. Repository Interface
ApiMetricsRepository.java
```
package com.example.apimetrics.repository;

import com.example.apimetrics.model.ApiMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApiMetricsRepository extends JpaRepository<ApiMetrics, String> {
    @Query("SELECT a.totalHits FROM ApiMetrics a WHERE a.apiName = :apiName")
    Integer findTotalHitsByApiName(@Param("apiName") String apiName);

    @Query("SELECT a.successfulHits FROM ApiMetrics a WHERE a.apiName = :apiName")
    Integer findSuccessfulHitsByApiName(@Param("apiName") String apiName);

    @Query("SELECT a.failedHits FROM ApiMetrics a WHERE a.apiName = :apiName")
    Integer findFailedHitsByApiName(@Param("apiName") String apiName);
}
```
3. Service Class
ApiMetricsService.java

```
package com.example.apimetrics.service;

import com.example.apimetrics.model.ApiMetrics;
import com.example.apimetrics.repository.ApiMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiMetricsService {
    @Autowired
    private ApiMetricsRepository repository;

    public List<ApiMetrics> getAllMetrics() {
        return repository.findAll();
    }

    public ApiMetrics getMetricsByApiName(String apiName) {
        return repository.findById(apiName).orElse(null);
    }

    public void updateMetrics(ApiMetrics metrics) {
        repository.save(metrics);
    }

    public Integer getTotalHits(String apiName) {
        return repository.findTotalHitsByApiName(apiName);
    }

    public Integer getSuccessfulHits(String apiName) {
        return repository.findSuccessfulHitsByApiName(apiName);
    }

    public Integer getFailedHits(String apiName) {
        return repository.findFailedHitsByApiName(apiName);
    }
}
```
4. Controller Class
ApiMetricsController.java

```
package com.example.apimetrics.controller;

import com.example .apimetrics.model.ApiMetrics;
import com.example.apimetrics.service.ApiMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class ApiMetricsController {
    @Autowired
    private ApiMetricsService service;

    @GetMapping
    public List<ApiMetrics> getAllMetrics() {
        return service.getAllMetrics();
    }

    @GetMapping("/{apiName}")
    public ApiMetrics getMetricsByApiName(@PathVariable String apiName) {
        return service.getMetricsByApiName(apiName);
    }

    @PostMapping
    public void updateMetrics(@RequestBody ApiMetrics metrics) {
        service.updateMetrics(metrics);
    }

    @GetMapping("/{apiName}/total-hits")
    public Integer getTotalHits(@PathVariable String apiName) {
        return service.getTotalHits(apiName);
    }

    @GetMapping("/{apiName}/successful-hits")
    public Integer getSuccessfulHits(@PathVariable String apiName) {
        return service.getSuccessfulHits(apiName);
    }

    @GetMapping("/{apiName}/failed-hits")
    public Integer getFailedHits(@PathVariable String apiName) {
        return service.getFailedHits(apiName);
    }
}
```
### API Endpoints
http://localhost:8080/api/metrics
http://localhost:8080/api/metrics/{apiName}
## Testing

This section describes how to test the API Metrics Tracking Spring Boot application using Postman and cURL. 

### Manual Testing with Postman

1. **Download and Install Postman**:
   - If you haven't already, download and install Postman from [Postman's official website](https://www.postman.com/downloads/).

2. **Start Your Spring Boot Application**:
   - Ensure that your Spring Boot application is running. You should see logs indicating that the application has started successfully.

3. **Create a New Collection in Postman**:
   - Open Postman and create a new collection to organize your API requests.

4. **Test the Endpoints**:

   #### 1. Get All Metrics
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/metrics`
   - **Description**: Retrieves all API metrics.
   - **Expected Response**: A JSON array of `ApiMetrics` objects.

   #### 2. Get Metrics by API Name
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/metrics/{apiName}`
   - **Description**: Retrieves metrics for a specific API.
   - **Example**: `http://localhost:8080/api/metrics/exampleApi`
   - **Expected Response**: A JSON object representing the metrics for `exampleApi`.

   #### 3. Update Metrics
   - **Method**: `POST`
   - **URL**: `http://localhost:8080/api/metrics`
   - **Description**: Updates or creates metrics for a specific API.
   - **Body**: Select "raw" and choose "JSON" from the dropdown. Use the following JSON:
     ```json
     {
         "apiName": "exampleApi",
         "totalHits": 100,
         "successfulHits": 90,
         "failedHits": 10
     }
     ```
   - **Expected Response**: No content (204) or a success message.

   #### 4. Get Total Hits
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/metrics/{apiName}/total-hits`
   - **Example**: `http://localhost:8080/api/metrics/exampleApi/total-hits`
   - **Expected Response**: An integer representing the total hits for `exampleApi`.

   #### 5. Get Successful Hits
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/metrics/{apiName}/successful-hits`
   - **Example**: `http://localhost:8080/api/metrics/exampleApi/successful-hits`
   - **Expected Response**: An integer representing the successful hits for `exampleApi`.

   #### 6. Get Failed Hits
   - **Method**: `GET`
   - **URL**: `http://localhost:8080/api/metrics/{apiName}/failed-hits`
   - **Example**: `http://localhost:8080/api/metrics/exampleApi/failed-hits`
   - **Expected Response**: An integer representing the failed hits for `exampleApi`.

### Automated Testing with JUnit

You can also implement automated tests using JUnit. Here’s a brief overview of how to set up JUnit tests:

1. **Add JUnit Dependency**:
   Ensure that you have the JUnit dependency in your `pom.xml` file:
   ```xml
   <dependency>
       <groupId>org.junit.jupiter</groupId>
       <artifactId>junit-jupiter</artifactId>
       <version>5.7.0</version>
       <scope>test</scope>
   </dependency>
   ```
   Create Test Classes: Create test classes for your service and controller layers to validate the functionality. For example:
   
   ```
   @SpringBootTest
public class ApiMetricsServiceTest {
    @Autowired
    private ApiMetricsService service;

    @Test
    public void testGetAllMetrics() {
        List<ApiMetrics> metrics = service.getAllMetrics();
        assertNotNull(metrics);
    }
}
   ```
mvn test
```
### Conclusion
Feel Free to Star and Fork the repo.