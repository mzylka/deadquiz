package com.example.deadquiz

import com.example.deadquiz.data.DeadlockItem
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET


private const val BASE_URL =
    "https://assets.deadlock-api.com/v2/"

private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface DeadlockApiService {
    @GET("items")
    suspend fun getItems(): List<DeadlockItem>
}

object DeadlockApi {
    val retrofitService: DeadlockApiService by lazy {
        retrofit.create(DeadlockApiService::class.java)
    }
}