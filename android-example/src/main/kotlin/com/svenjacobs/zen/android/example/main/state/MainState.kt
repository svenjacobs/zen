package com.svenjacobs.zen.android.example.main.state

import com.svenjacobs.zen.android.example.api.model.JsonPost
import com.svenjacobs.zen.core.state.State

data class MainState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val posts: List<JsonPost> = emptyList(),
    val dialogTitle: String? = null,
    val toastMessage: String? = null
) : State
