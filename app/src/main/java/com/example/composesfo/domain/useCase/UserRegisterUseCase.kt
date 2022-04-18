package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.data.remote.dto.toUserRegister
import com.example.composesfo.domain.model.UserRegister
import com.example.composesfo.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRegisterUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String, userDto: UserDto): Flow<Resource<UserRegister>> = flow {
        try {
            emit(Resource.Loading<UserRegister>())
            repository.createUser(userId,userDto)
            emit(Resource.Success<UserRegister>(userDto.toUserRegister()))
        } catch (e: HttpException) {
            emit(Resource.Error<UserRegister>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<UserRegister>( "Couldn't reach server. Check your internet connection"))
        }
    }

}