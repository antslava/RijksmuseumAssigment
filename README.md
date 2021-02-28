# Rijksmuseum App
A small app written in Kotlin.

This app loads Art objects from official Rijksmuseum API.
On the main screen, it shows a list of products, and when you click on one of the items, it will open the details page of that item.
The project was developed using Kotlin, Android Jetpack Components (LiveData, ViewModel), Kotlin Coroutines. 
I tried to apply concepts from Clean Architecture to build an easy and testable project.
     
Improvements & next steps
--------
Due to the limited amount of my free time, I have to do some considerations of what I left out:
- Implement empty state
- Implement options to search items, sort items, etc.  
- Implement UI Tests
- Improve Design and show more data.
- Increase the test coverage
- Write custom exception handler for Coroutines
- Improve accessibility of the App  

#### Project structure
--------
- *app* - folder where we place all UI logic
- *data* - is where we keep the repository, data source, and API implementations.
- *di* - Here, we set up our dependency injection.
- *domain* - you can find here UseCases, Domain Models, and Repository interfaces.
- *utils* - Place where we can add classes and helpers to be used throughout the project.

#### Architecture
---
This application using MVVM architecture and applied concepts from Clean Architecture.
I was able to quickly create caching mechanism because Rijksmuseum API is fully based on GET requests. (It's a short-term solution.)

#### Tests
---
I have implemented some tests for the use cases, merger, and model mapping. 

To execute the unit tests, you should run the command below:
```bash
./gradlew test
```

Compatibility
--------
*minSdkVersion* 23 (Android 6.0)  
*targetSdkVersion* 30 (Android 11)
