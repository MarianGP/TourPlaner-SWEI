## SWE - Tour Planner
#### https://github.com/MarianGP/TourPlaner-SWEI

### App Architecture
This application is divided into 3 layers

- View Layer
- Business Layer
- Data Access Layer

### Architectural, UX, library decisions

- UX: Each view has its own controller and viewModel 
- Libraries:
  
    1) <ins>log4j</ins>: logging application library for recoding app activity. Used levels: INFO, DEBUG, WARN, ERROR.
       
    2) <ins>lombok</ins>: the mainly used annotations ``@Data, @Builder, @AllArgsConstructor`` allow faster coding process. 
      I realized I should avoid using ``new`` to instantiate new Objects. Instead, using ``Object.builder.build()``
      allows a more flexible architecture. In case you modify the properties of a class having already created
      mocked objects for test with ```new```, every new statement needs to be updated. With Builder this is avoided.
       
    3) <ins>itextpdf - html2pdf</ins>: to create report using html. Which simplifies the styling process.
       
    4) <ins>junit</ins>: for unit testing. used also the ``@Order`` annotation to force the test to follow the same
      execution order.
       
    5) <ins>mockito</ins>: allows mocking objects for each unit test.
       
    6) <ins>gson</ins>: offers JSON data-binding support. Used to deserialize JSON MapQuest API response body into
     a ``Response`` object
       
    7) <ins>checkstyle</ins>: to check coding style and to ensure standards and good practices. currently disabled

    8) <ins>surefire-report</ins>: creates html reports of unit tests. Displayed are details like: Packages and Classes, 
     coverage, test methods names, Failure tests or skipped tests with reason, Execution time taken by each method.
     <a href="file:///C:/Users/mgarc/IdeaProjects/Projects/TourPlaner_SWE/target/apiDocs/index.html" target="_top">Open Report</a>

    9) <ins>javadoc</ins>: for documentation. It automatically generates html documentation for Classes, properties 
     and methods
       
    10) <ins>maven-site-plugin</ins>: Application html summary report.
     <a href="file:///C:/Users/mgarc/IdeaProjects/Projects/TourPlaner_SWE/target/site/index.html" target="_top">Open Report</a>

    11) <ins>jacoco</ins>: to check test unit code coverage
        <a href="./target/site/jacoco/index.html" target="_top">Open Report</a>

       Read more [here](./target/site/index.html)
       Read more [here](C:\Users\mgarc\IdeaProjects\Projects\TourPlaner_SWE\target\site\index.html)
       

### Implemented design pattern


### Unit testing decisions


### Unique feature


### Time spent




