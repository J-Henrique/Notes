package com.jhbb.data.di

import com.jhbb.data.api.NotesApi
import com.jhbb.data.mapper.ErrorMapperImpl
import com.jhbb.data.repository.NotesRepositoryImpl
import com.jhbb.domain.common.ErrorMapper
import com.jhbb.domain.repository.NotesRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "http://10.0.2.2:3000/"

val modules = module {
    single { NotesRepositoryImpl(get(), get()) as NotesRepository }
    single { provideRetrofit() }
    factory { provideNotesApi(get()) }
    factory { ErrorMapperImpl() as ErrorMapper}
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideNotesApi(retrofit: Retrofit): NotesApi = retrofit.create(NotesApi::class.java)