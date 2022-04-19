package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.QuestionDto
import com.example.composesfo.data.remote.dto.toQuestion
import com.example.composesfo.domain.model.Question
import com.example.composesfo.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateQuestionUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String, questionDto: QuestionDto): Flow<Resource<Question>> = flow {
        try {
            emit(Resource.Loading<Question>())
            repository.createQuestion(userId,questionDto)
            emit(Resource.Success<Question>(questionDto.toQuestion()))
        } catch (e: HttpException) {
            emit(Resource.Error<Question>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<Question>( "Couldn't reach server. Check your internet connection"))
        }
    }
}