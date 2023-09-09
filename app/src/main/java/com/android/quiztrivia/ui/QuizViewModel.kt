package com.android.quiztrivia.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quiztrivia.network.QuizApi
import com.android.quiztrivia.util.model.Item
import com.android.quiztrivia.util.model.Quiz
import com.android.quiztrivia.util.model.QuizStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class QuizViewModel(private val quizApi: QuizApi,private val item: Item) : ViewModel() {
    private val _counter: MutableLiveData<Int> = MutableLiveData(1)
    private val _quiz: MutableLiveData<Quiz> = MutableLiveData()
    private var quizList: List<Quiz> = listOf()

    private var scoreCounter: Int = 0
    val counter: LiveData<Int> get() = _counter
    val quiz: LiveData<Quiz> get() = _quiz

    companion object {
        private const val TAG = "viewModel"
    }

    init {
        fetchQuizList() //single time performer
    }

    //private functions
    /**
     * RECEIVED THE RESULT FROM SERVER AND STORED IT
     */
    private fun fetchQuizList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                quizList = quizApi.getSpecificQuiz(category = item.apiNumber).results
                Log.i(TAG, "Response from server: ${quizList.size}")
                //perform next tasks
                receiveQuiz() //show quiz on screen for first time
            } catch (e: Exception) {
                //show exception to the user
                //Toast.makeText(MainActivity(), "Unable to Fetch Quiz", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Exception occurred fetchQuizFrom Server: $e")
            }
        }
    }

    //the below both will run on main dispatcher because it is necessary to add them in ui ASAP
    //producer
    private fun getQuizFromList(error: () -> Unit): Flow<Quiz> {
        /**
         * received the result and used flow to emit the list
         */
        return flow {
            Log.i(TAG, "Counter Value: ${counter.value!!-1}")
            val tempQuiz = quizList[counter.value!!-1]
            tempQuiz.apply {
                Log.i(TAG, "TempQuiz Before: $this")
                //insert correct ans in incorrect array and shuffle it
                incorrectAnswer.add(this.correctAnswer)
                incorrectAnswer.shuffle()
                Log.i(TAG, "TempQuiz After: $tempQuiz")
                emit(tempQuiz)
            }
        }.catch {
            error()
            Log.e(TAG, "Error occurred: $it")
        }.flowOn(Dispatchers.Main)
    }
    //receiver
    private fun receiveQuiz() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val receivedQuiz = getQuizFromList {
                    Toast.makeText(MainActivity(), "Unable to Fetch Quiz", Toast.LENGTH_SHORT)
                        .show()
                }
                receivedQuiz.collect {
                    _quiz.value = it
                    Log.i(TAG, "Quiz received values are\n${_quiz.value}")
                }
            } catch (e: Exception) {
                //perform some task
                Log.e(TAG, "Exception in consumer: $e")
            }
        }
    }


    /* HELPERS */
    //increase counter
    fun increaseCounter(answer: String, onSubmit: () -> Unit) {
        if (counter.value!! <= quizList.size-1) {
            Log.i(TAG, "Correct SCore is: $scoreCounter")
            if (answer == quiz.value!!.correctAnswer) scoreCounter += 1
            _counter.value = _counter.value!! + 1
            receiveQuiz()
        } else {
            Log.i(TAG, "Last Score is $scoreCounter")
            onSubmit()
        }
    }

    //on submit show the score
    fun showScore(): Int = scoreCounter

    //show quiz length
    fun showQuizListSize(): Int = if(quizList.isNotEmpty()) quizList.size else 0
}