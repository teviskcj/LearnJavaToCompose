package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.QuestionDto
import com.example.composesfo.data.remote.dto.UserDto

interface UserRepository {

    suspend fun createUser(userId: String, userDto: UserDto)

    suspend fun login(): Map<String, UserDto>

    suspend fun getUserById(userId: String): UserDto

    suspend fun createQuestion(userId: String, questionDto: QuestionDto)

    suspend fun getQuestionById(userId: String): QuestionDto
}