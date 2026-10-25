package ru.kre4.chad.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ChadApi {

    @GET("channels")
    suspend fun getChannels(): List<String>

    @GET("channel/{name}")
    suspend fun getMessages(
        @Path("name") name: String,
        @Query("limit") limit: Int = 20,
        @Query("lastKnownId") lastKnownId: Long? = null,
        @Query("reverse") reverse: Boolean = false,
    ): List<Message>

    @Streaming
    @GET("thumb/{path}")
    suspend fun getThumb(@Path(value = "path", encoded = true) path: String): ResponseBody

    @Streaming
    @GET("img/{path}")
    suspend fun getImage(@Path(value = "path", encoded = true) path: String): ResponseBody
}
