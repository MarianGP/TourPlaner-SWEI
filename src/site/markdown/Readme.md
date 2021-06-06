## SWE - Tour Planner
#### https://github.com/MarianGP/TourPlaner-SWEI

## App Architecture
This desktop application is developed based on the GUI frameworks JavaFX. 
A user creates (bike-, hike-, running-, etc) tours in advance and manages
the logs and statistical data of accomplished tours.
The tours, logs and directions are stored in a postreSQL database.
The tour information is collected from the MapQuest API and the map images are stored
locally on a file-system.

This application is divided into 3 layers (view, business and data access layers). 
The application configuration (application.properties file in the config package) determines which 
classes from the BL and DAL will be instantiated. More details below.

### 1) View Layer
This layer manages the GUI and the user interaction<br>
Views: 3 dialogs (add/edit Tour, add/edit TourLog, exportReport) and a toursView for displaying Tour and Log List.


#### 1) View: 
- fxml file with the GUI structure and layout

#### 2) Controller: 
- create bindings between view element and viewModel properties, 
- controls opening and closing views. 
- instantiates its viewModel and not classes from other layers. 
- initializes properties of its viewModel according to property values of the (previous) view which 
  initialized this new view.
#### 3) ViewModel: 
- GUI input fields are validated
- imports IAppManager class from the BL
- converts model format into strings to be displayed in the GUI

### 2) Business Layer
- This layer imports class from the DAL which are instantiated depending on which concrete DALFactory implementation is chosen.
An IAppManager interface allows choosing between the AppManagerMock (which returns hardcoded), and the AppManagerDB class
(which connects to the DAL). An AppManagerFactory return a singleton of the chosen IAppManager concrete implementation.

- This layer calls the DAL to manage Tours, Logs and TourDirection, creation of reports, calculate TourStats and manage 
TourDirection's

### 3) Data Access Layer - DAL
- Controls data access. The only classes which can access out of this layer are classes from the model and config packages.
This layer contains package for managing connection with MapQuest API, FileAccess 
(for exporting and importing files information), PDFBuilder (creation of reports), and DB Connection 
(allowed with an entity, service, repository structure). Mapper classes are used to map entity objects from the db to
the application models

## Architectural, UX, library decisions

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
       

## Implemented design pattern

#### 1) Singleton: 
- To instantiate the BL AppManager objects using the ``AppManagerFactory``. 
  The factory returns a singleton of a concrete AppManager abstraction.
  This prevents that more than one instance/object of the same class is created.

#### 2) Visitor Pattern: 
- In the App class a new dialog view can be initialized using the openDialog() method
The view fxml file is loaded and fxmlLoader controller can be saved into a ``IController`` interface class variable.
This allows to call methods from any Controller class, which implement the ``IController`` interface (Double Dispatch) 

#### 3) Abstract Factory:
- ``IDALFactory`` with DALOracleFactory and DALPostgresFactory depending on which SQL DB is to be implemented.

#### 4) Observer Pattern:
- For binding GUI elements with viewModel

### Unit testing decisions
I started creating unit test from the DAL to ensure before testing higher layers that the basic functionality was achieved
and focusing afterwards in only the logic of the method not the others related to it. Some tests can not be considered 
as unit test because they test for example that the API is working properly.
JUnit allows to determine the order of execution of the test which allows for example to create a Tour, then search 
a term from this specific Tour and to delete it at the end. Usually test are executed in a "random" order. 
This option made the test development easier and more efficient.


Total test: 34

### Unique feature
Displays the steps/direction of each tour in the Step Tab of the GUI. 
The maneuvers are to be found in the steps objects of the API response and stored into TourDirection objects.
The creation of the viewList slows down when needing to access in each loop to the online images. 
That's the reason why the most frequent ones were stored locally. The ones which are not stored locally are loaded
from online url.

### Time spent
100 hrs


