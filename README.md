# Clean Code and Secure Coding Reflection (Reflection 1)

I have implemented two new features using Spring Boot and followed clean code principles and secure coding practices in my source code.

## Clean Code Principles

- **Meaningful Names**: I have used meaningful names for variables, functions, and classes to make the code easy to understand. For example, in the `ProductController` class, I have used meaningful names for the functions such as `createProduct`, `editProduct`, and `deleteProduct`.

- **Function Do One Thing**: I have made sure that each function does only one thing and does it well. For example, in the `ProductService` class, I have created separate functions for creating, editing, and deleting a product.

- **Abstraction of Objects and Data Structures**: I have used abstraction to hide the implementation details of objects and data structures. For example, in the `ProductRepository` class, I have used an interface to define the methods for accessing the product data.

- **Error Handling**: I have handled errors properly by using try-catch blocks and throwing meaningful exceptions. For example, in the `ProductService` class, I have used try-catch blocks to handle errors when creating, editing, or deleting a product.

## Secure Coding Practices

- **Input Data Validation**: I have validated all input data to prevent attacks such as SQL injection and cross-site scripting (XSS).

- **Output Data Encoding**: I have encoded all output data to prevent attacks such as XSS. For example, in the `ProductController` class, I have used the `Thymeleaf` template engine to encode the output data when displaying the product list.

# Reflection 2

After writing the unit test, I felt a sense of completeness and assurance that my code is functioning as expected. However, determining the number of unit tests to write in a class can be challenging. It is essential to ensure that all possible paths and edge cases are covered. To verify that our unit tests are sufficient, we can use code coverage tools to measure how much of our code is executed during testing. 

Code coverage is a valuable metric that helps identify untested parts of the codebase. However, achieving 100% code coverage does not guarantee that the code is free of bugs or errors. It is possible to have 100% code coverage and still have issues if the tests do not cover all potential scenarios or are not written correctly.

Regarding the creation of a new functional test suite to verify the number of items in the product list, replicating the setup procedures and instance variables from `CreateProductFunctionalTest.java` can lead to code duplication, which reduces code quality. The potential clean code issues include:

- **Duplication**: Having the same setup code and instance variables across multiple test classes can lead to maintenance challenges, as changes will need to be replicated in multiple places.
- **Lack of Reusability**: The duplicated code is not reusable, which violates the DRY (Don't Repeat Yourself) principle.

To improve code cleanliness, we can refactor the common setup procedures and instance variables into a base test class that other functional test suites can extend. This approach promotes reusability, reduces duplication, and makes the codebase easier to maintain. Additionally, it can lead to a more organized structure where common functionalities are centralized, improving readability and maintainability.