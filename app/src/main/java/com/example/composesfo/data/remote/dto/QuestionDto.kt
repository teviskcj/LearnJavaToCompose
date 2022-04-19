package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Question

data class QuestionDto(
    val answer1: String,
    val answer2: String
)

fun QuestionDto.toQuestion(): Question {
    return Question(
        answer1 = answer1,
        answer2 = answer2
    )
}