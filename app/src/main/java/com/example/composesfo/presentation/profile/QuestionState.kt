package com.example.composesfo.presentation.profile

import com.example.composesfo.domain.model.Question

data class QuestionState(
    val isLoading: Boolean = false,
    val question: Question? = null,
    val error: String = ""
)
