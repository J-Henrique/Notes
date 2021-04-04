package com.jhbb.data.di

import com.jhbb.data.api.NotesApi
import com.jhbb.data.mapper.ErrorMapperImpl
import com.jhbb.data.repository.NotesRepositoryImpl
import com.jhbb.domain.common.ErrorMapper
import com.jhbb.domain.repository.NotesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataModule {
    fun getDataModules(baseUrl: String) = module {
        single { NotesRepositoryImpl(get(), get()) as NotesRepository }
        factory { provideNotesApi(get()) }
        factory { ErrorMapperImpl() as ErrorMapper}
        single { provideRetrofit(baseUrl) }
    }


    private fun provideRetrofit(baseUrl: String): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(loggingInterceptor)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideNotesApi(retrofit: Retrofit): NotesApi = retrofit.create(NotesApi::class.java)
}