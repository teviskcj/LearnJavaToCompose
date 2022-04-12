package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.toUserLogin
import com.example.composesfo.domain.model.UserLogin
import com.example.composesfo.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Resource<List<UserLogin>>> = flow {
        try {
            emit(Resource.Loading<List<UserLogin>>())
            val list = repository.login().values
            val userLogin = list.map { it.toUserLogin() }
            emit(Resource.Success<List<UserLogin>>(userLogin))
        } catch (e: HttpException) {
            emit(Resource.Error<List<UserLogin>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<List<UserLogin>>( "Couldn't reach server. Check your internet connection"))
        }
    }
}