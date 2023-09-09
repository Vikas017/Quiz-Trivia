package com.android.quiztrivia.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.quiztrivia.network.QuizApi
import com.android.quiztrivia.ui.QuizViewModel
import com.android.quiztrivia.util.model.Item
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

class ViewModelFactory(private val quizApi: QuizApi, val item: Item): ViewModelProvider.Factory {
    companion object {
        private const val TAG = "vieModelProvider"
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            Log.i(TAG, "ViewModelProvider called for setting item in the viewmodel")
            return QuizViewModel(quizApi, item) as T
        }
        throw IllegalArgumentException("Wrong Parameter")
    }
}