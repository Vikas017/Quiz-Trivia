package com.android.quiztrivia.util.model

import com.squareup.moshi.Json

//we will cal this network model openly
data class NetworkResponse(
    @Json(name = "response_code") val responseCode: Int,
    val results: List<Quiz>
)

data class Quiz(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @Json(name = "correct_answer") val correctAnswer: String,
    @Json(name = "incorrect_answers") var incorrectAnswer: MutableList<String>
)