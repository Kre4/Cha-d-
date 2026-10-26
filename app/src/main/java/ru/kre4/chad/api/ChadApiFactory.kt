package ru.kre4.chad.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object ChadApiFactory {

    const val BASE_URL = "https://faerytea.name/" // http://213.189.221.170:8008/  ??

    val clientInstance by lazy { create() }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun thumbUrl(link: String): String = "${BASE_URL}thumb/$link"

    fun imgUrl(link: String): String = "${BASE_URL}img/$link"

    private fun create(
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
