package com.android.quiztrivia.dependency

import com.android.quiztrivia.network.QuizApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://opentdb.com/"

    //provide retrofit to the dependency graph
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
    }


    //provide quiz api to the dependency graph
    @Provides
    @Singleton
    fun provideQuizApi(retrofit: Retrofit): QuizApi {
        val quizApi: QuizApi by lazy { retrofit.create(QuizApi::class.java) }
        return quizApi
    }
}