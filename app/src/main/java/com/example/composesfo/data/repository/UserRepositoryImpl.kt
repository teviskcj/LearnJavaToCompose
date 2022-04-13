package com.example.composesfo.data.repository

import com.example.composesfo.data.remote.SFOApi
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: SFOApi
) : UserRepository {
    override suspend fun register(userId: String, userDto: UserDto) {
        return api.createUser(userId, userDto)
    }

    override suspend fun login(): Map<String, UserDto> {
        return api.getUsers()
    }
}