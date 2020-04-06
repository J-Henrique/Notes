package com.jhbb.notes.network

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "http://10.0.2.2:3000/"

val networkModule = module {
    single { provideRetrofit() }
    factory { provideNotesApi(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideNotesApi(retrofit: Retrofit): NotesApi = retrofit.create(NotesApi::class.java)