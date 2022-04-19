package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.toUserProfile
import com.example.composesfo.domain.model.UserProfile
import com.example.composesfo.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
private val repository: UserRepository
) {
    operator fun invoke(userId: String): Flow<Resource<UserProfile>> = flow {
        try {
            emit(Resource.Loading<UserProfile>())
            val userProfile = repository.getUserById(userId).toUserProfile()
            emit(Resource.Success<UserProfile>(userProfile))
        } catch (e: HttpException) {
            emit(Resource.Error<UserProfile>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<UserProfile>( "Couldn't reach server. Check your internet connection"))
        }
    }
}