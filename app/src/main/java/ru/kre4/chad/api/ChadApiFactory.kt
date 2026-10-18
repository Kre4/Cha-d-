package ru.kre4.chad.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object ChadApiFactory {

    private const val BASE_URL = "https://faerytea.name/" // http://213.189.221.170:8008/  ??

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun create(
        okHttpClient: OkHttpClient = OkHttpClient(),
        baseUrl: String = BASE_URL,
    ): ChadApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ChadApi::class.java)
    }
}
