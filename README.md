Bridge International Academy - Android App 

Features
-
- List current pupils
- View, Update and delete pupils
- Offline support



How to build this application
-
This is an android studio project, use gradle to clean, build and release

<b>./gradlew build</b> # Build the application in debug

<b>./gradlew clean release</b> # Clean and build the application for release

<b>./gradlew runCheckStyle</b> # Check code standards

<b>./gradlew runUnitTest</b> # Execute all unit test

<b>./gradlew runJacoco</b> # Execute test / code coverage report

Architecture of the application
-
This application follow MVP architecture.
The data module contains all POJO objects required for the application

app module is the application, containing all UI components
app.configuration contains definition for common libraries used across the solution
app.log module that handle android logs for debug/production
app.net module contains the network services and data handlers
app.repository contains the logic required to work with remote/local data

All modules are unit tested and the App module contains a set of integration test for all features of the application

Config folder contains the rules required for keep a standard code (CheckStyle pluging) and a gradle file with all dependencies

API
-
This application uses a Bridge International Academy Test API to make basic CRUD operations

https://bridgetechnicaltest.azurewebsites.net/api/pupils?page=1
- API Parameters:
 - <b>page</b>: Page number to load (Required)

- Response
<pre>
{
       "items": [
         {
           "pupilId": 1,
           "country": "Spain",
           "name": "Julio",
           "image": "http://lorempixel.com/640/480/people?name=Bart Kozey",
           "latitude": 0.0,
           "longitude": 1.0
         }
       ],
       "pageNumber": 1,
       "itemCount": 1,
       "totalPages": 1
     }
</pre>

External libraries
-
- Picasso
- Retrofit2
- Dagger2
- ButterKnife
- RxJava and RxAndroid
- LeakCanary


To do / Improve
-
- Better handle for network/local errors
- Add infinite scrolling to load all pages when the user scroll
- Add filter functionality to pupils list
- Use Java8 / Retrolamba expressions
- Set up travis as CI