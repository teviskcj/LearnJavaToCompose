package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.UserDto

interface UserRepository {

    suspend fun register(userId: String, userDto: UserDto)

    suspend fun login(): Map<String, UserDto>
}