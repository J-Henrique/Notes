package com.jhbb.notes.data.api

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "https://my-json-server.typicode.com/"

val apiModule = module {
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