# Trivia-Android-App-With-Heroku-Dashboard
Distributed Systems Project 4 - Android App with Web Service Dashboard deployed to Heroku

### Description
This application takes parameters of category, number of questions, difficulty and type of questions from the user 
and provides them with questions from the Open Trivia Database API (https://opentdb.com/api_config.php) 
which is brought to us from the good folks at PIXELTAIL GAMES LLC.

### The App
The app layout involves a few basic dropdown menus (Spinners) for the user to choose a category,
difficulty and the type of questions. Input for the number of questions is and editable text box. 

<img src="screenshots/Task 2 Layout.JPG" alt="Android App Layout" title="Android App Layout" width=256>

As can be seen above, the application defaults the input so that "Get Questions" can be pressed without any effort from
the user and display questions.  
Once the "Get Questions" button is pressed, the application then sends a request to an application deployed to Heroku 
which in turn makes the HTTP GET request to the Open Trivia Database API. The deployed web service also has a dashboard
that allows users to see three interesting analytical facts about how the app has been used as well as a log of past
user interactions.

