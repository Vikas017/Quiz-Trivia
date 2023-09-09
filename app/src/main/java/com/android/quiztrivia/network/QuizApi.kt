package com.android.quiztrivia.network

import com.android.quiztrivia.util.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {

    @GET("api.php")
    suspend fun getSpecificQuiz(
        @Query("amount") amount: Int = 10,
        @Query("category") category: Int, //category is different
        @Query("difficulty") difficulty: String = "easy",
        @Query("type") type: String = "multiple"
    ): NetworkResponse

}