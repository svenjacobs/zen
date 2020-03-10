package com.svenjacobs.zen.android.example.api

import com.svenjacobs.zen.android.example.api.model.JsonPost
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow

class Repository(
    private val client: HttpClient
) {

    suspend fun posts() = flow {
        emit(client.get<List<JsonPost>>("https://jsonplaceholder.typicode.com/posts"))
    }

    suspend fun postsByUser(userId: Int) = flow {
        emit(client.get<List<JsonPost>>("https://jsonplaceholder.typicode.com/posts?userId=$userId"))
    }
}

