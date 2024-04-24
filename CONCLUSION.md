
# Ticket-101 Code Review (back-end)

## Description

The provided code implements a RESTful API for a loan decision engine. The endpoint accepts POST requests with a request body containing the customer's personal ID code, requested loan amount, and loan period. It then processes the request and returns a response containing the approved loan amount and period, along with any error messages if applicable.

# Code Review
The codereview demonstrates several positive aspects that were done well by the intern:

- **Clear Constants:** The ***DecisionEngineConstants*** class effectively organizes and centralizes constants used throughout the codebase, enhancing maintainability and readability.
- **REST Endpoint Clarity:** The ***DecisionEngineController*** class provides a clear and concise ***REST*** endpoint for handling loan decision requests. The controller method is well-documented, detailing the request and response behavior, which aids developers in understanding its functionality.
- **Error Handling:** The codebase implements robust error handling mechanisms, including catching specific exceptions and returning appropriate ***HTTP*** responses with meaningful error messages. This ensures a better user experience and helps in debugging and troubleshooting.
- **DTO Usage:** The ***DecisionRequest*** and ***DecisionResponse*** classes serve as Data Transfer Objects (DTOs), encapsulating request and response data, respectively. This promotes clarity and consistency in data exchange between the client and server.
- **Service Layer Separation:** The ***DecisionEngine*** class encapsulates the business logic for calculating approved loan amounts and periods, adhering to the Single Responsibility Principle (SRP) and promoting maintainability and testability.

# Tests Code Review

- The test suite provides ***comprehensive coverage*** for both the controller and service layers of the application, ensuring that **critical functionality is thoroughly tested**.
- Tests effectively validate both ***success and error scenarios*** contributing to the robustness and reliability of the application.
- The use of mocking and unit testing ensures that ***individual components are tested in isolation*** enabling focused testing and easier debugging.
- The ***clarity and readability*** of the test code enhance maintainability and facilitate future modifications or additions to the codebase.


# Improvements:

**1. Java Constants: int vs Integer:**

- Changed the data type of constants from Integer to int for better performance and memory utilization. Since these constants are primitive values, using int is more appropriate than Integer.

**2. Annotation removal:**
- Removed the ***@Component*** annotation from the ***DecisionResponse*** class.
- The ***@Component*** annotation was removed because it is not necessary for this class to be managed by Spring's component scanning and instantiation. The ***DecisionResponse*** class serves as a plain data object (DTO) to hold response data for the REST endpoint and does not require any Spring-specific functionality.
- By removing the @Component annotation, the class is decoupled from Spring framework concerns, making it more reusable and easier to understand.

**3. Extend Exception vs RuntimeException:**
- The exceptions ***InvalidLoanAmountException***, ***InvalidLoanPeriodException***, ***InvalidPersonalCodeException***, and ***NoValidLoanException*** were originally extending Throwable, which makes them checked exceptions. They were modified to extend ***RuntimeException*** instead of Throwable. This change was made to convert them into unchecked exceptions.

- Checked exceptions (those extending Throwable but not RuntimeException) impose a burden on developers to handle or declare them, which can clutter code and hinder readability, especially in cases where the exceptions are unlikely to occur or are not recoverable.
- By extending RuntimeException, these exceptions become unchecked, indicating that they are typically caused by programming errors or conditions that should be fixed rather than caught and handled.
- This change aligns with common Java best practices, where checked exceptions are typically reserved for exceptional conditions that can be reasonably recovered from, while unchecked exceptions are used for programming errors or conditions that are unlikely to occur in normal operation.


**4. SOLID:**
- Moved the input validation logic from the DecisionEngine class to separate packages (utils and validators) to adhere to the Single Responsibility Principle (SRP) and improve code organization and maintainability.
- Introduced an ***InputValidator*** interface in the utils package to define the contract for input validation.
- Implemented the input validation logic in the ***EstonianInputValidator*** class in the validators package, which now implements the InputValidator interface.
- The ***EstonianInputValidator*** class is responsible for validating Estonian-specific inputs, such as personal ID codes, loan amounts, and loan periods, promoting better separation of concerns.
- This refactoring makes the codebase more modular and easier to extend, test, and maintain in the long run.

**5. Credit modifier method changes:**
- Readability: The switch expression improves readability by clearly defining the credit modifier logic based on the segment.
- Maintainability: Using a switch expression makes it easier to add or modify segments in the future, enhancing maintainability.
- Redundancy: The switch expression removes redundant checks and reduces the chance of errors compared to the previous if-else statements.

**6. Removing shared instance:**
- Concurrency: By removing the shared instance of ***DecisionResponse*** in ***DecisionEngineController***, it prevents potential concurrency issues where multiple requests could modify the same object simultaneously, leading to unpredictable behavior.
- Scope: Creating a new instance of DecisionResponse within the method ensures that each request is handled independently, with its own instance of the response object.
- Readability: The refactoring simplifies the controller class and makes the flow of creating and returning the response more straightforward.

**7. Removing unnecessary variable:**
- It was unnecessary to hold the creditModifier in a ***DecisionEngine*** class
- Thread Safety: The creditModifier variable is used solely within the ***calculateApprovedLoan*** method. Since it's not accessed or modified by multiple threads simultaneously, there's no need to make it a class-level variable. Storing it as a local variable within the method ensures thread safety and prevents potential concurrency issues.
- Scope: The ***creditModifier*** variable is only relevant within the context of the calculateApprovedLoan method. Storing it as a class variable increases its scope unnecessarily, potentially leading to confusion about its purpose and lifetime.

**8. Global Exception Handler:**
- This separates the concerns of error handling from the controller logic, making it more modular and easier to maintain.
- It makes error handling easier, more consistent, and less scattered throughout all code.





