package com.msa.supervisor.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zar.core.tools.hilt.HiltProviders
import com.zar.core.tools.hilt.ProgressResponseBody
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.tool.LoggerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
/**
 * create by Ali Soleymani.
 */

@Module
@InstallIn(SingletonComponent::class)
class Providers {


    companion object {
        const val url = ""
    }

    //---------------------------------------------------------------------------------------------- provideUrl
    @Provides
    @Singleton
    fun provideUrl() = url
    //---------------------------------------------------------------------------------------------- provideUrl



    //---------------------------------------------------------------------------------------------- provideApi
    @Provides
    @Singleton
    fun provideApi(@ApplicationContext context: Context) : ApiSupervisor =
        retrofit(context).create(ApiSupervisor::class.java)
    //---------------------------------------------------------------------------------------------- provideApi


    //---------------------------------------------------------------------------------------------- providerLoggerManager
    @Provides
    @Singleton
    fun providerLoggerManager(@ApplicationContext context: Context) = LoggerManager(context)
    //---------------------------------------------------------------------------------------------- providerLoggerManager


    //---------------------------------------------------------------------------------------------- retrofit
    private fun retrofit(@ApplicationContext context: Context): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(httpClient(context))
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .build()
    //---------------------------------------------------------------------------------------------- retrofit


    //---------------------------------------------------------------------------------------------- httpClient
    private fun httpClient(@ApplicationContext context: Context) = OkHttpClient()
        .newBuilder()
        .connectTimeout(0, TimeUnit.SECONDS)
        .readTimeout(0,TimeUnit.SECONDS)
        .writeTimeout(0,TimeUnit.SECONDS)
        .addInterceptor(interceptor(context))
        .addNetworkInterceptor(provideLoggingInterceptor())
        .build()
    //---------------------------------------------------------------------------------------------- httpClient


    //---------------------------------------------------------------------------------------------- interceptor
    private fun interceptor(@ApplicationContext context: Context) = Interceptor { chain ->
        val sharedPreferences=context.getSharedPreferences("secret_shared_prefs", Context.MODE_PRIVATE)
        val token=sharedPreferences.getString(CompanionValues.TOKEN,"")
        val dataOwnerCenterKey=sharedPreferences.getString(CompanionValues.zoneIds,"")
        val newRequest: Request = chain.request().newBuilder()
            .header("OwnerKey", "")
            .build()
        chain.proceed(newRequest)
    }
    //---------------------------------------------------------------------------------------------- interceptor


    private fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    //---------------------------------------------------------------------------------------------- provideLoggingInterceptor


    //---------------------------------------------------------------------------------------------- provideGson
    fun provideGson(): Gson = GsonBuilder().registerTypeAdapter(
        LocalDateTime::class.java,
        HiltProviders.LocalDateTimeDeserializerManager()
    ).create()
    //---------------------------------------------------------------------------------------------- provideGson


}