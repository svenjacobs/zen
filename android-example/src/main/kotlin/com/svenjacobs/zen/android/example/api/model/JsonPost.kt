package com.svenjacobs.zen.android.example.api.model

import kotlinx.serialization.Serializable

@Serializable
data class JsonPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
