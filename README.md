# Quiz-Trivia
### Description
  **Quiz Trivia** is an Android Specific Application which basically let 
  the user to choose the certain * type * of the quiz due to which the user will be able to get 
  the corresponding selected question of the quiz.
***Note*** The details have not been entered yet.
### Features of the App
  - Quiz fetching from the server
  - Showing Available quiz list
  - Screen
      - Home: List of Available quiz and the types of specific questions for the quiz 
      - Detail: The details about the specific selected quiz. 
      - Quiz: The quiz starts from here. The questions and options are received from the server.
      - Result: Result of the quiz
### Result Criteria
  - 10 -> Amazing
  - 9-8 -> Excellent
  - 7-6 -> Cool
  - less than 6 -> Fail
   
### Programming Features
  - Recycler View for list of available quiz
  - Advanced Kotlin concepts like flow, concurrency, and synchronization
  - Dependency Injection

### Libraries Used
  - Android jetpack libraries
  - Lifecycle component
  - Retrofit for data fetching
  - Misho for parsing the data

### Response Sample - JSON
{  
    "response_code": 0,  
    "results": [{  
        "category": "Entertainment: Books",  
        "type": "multiple",  
        "difficulty": "easy",  
        "question": "Which is NOT a book in the Harry Potter Series?",  
        "correct_answer": "The House Elf",  
        "incorrect_answers": ["The Chamber of Secrets", "The Prisoner of Azkaban", "The Deathly Hallows"]  
    }]  
}

### Demo Video
Kindly [`Click Here`](https://drive.google.com/file/d/1fomrDPmopmgx1aowVsdmFBBC0TdnST2o/view?usp=sharing) to see the video

Try to upgrade this repo

#### THANK YOU ####
