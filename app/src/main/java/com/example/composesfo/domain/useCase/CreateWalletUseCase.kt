package com.example.composesfo.domain.useCase

import com.example.composesfo.common.Resource
import com.example.composesfo.data.remote.dto.WalletDto
import com.example.composesfo.data.remote.dto.toWallet
import com.example.composesfo.domain.model.Wallet
import com.example.composesfo.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateWalletUseCase @Inject constructor(
    private val repository: WalletRepository
) {
    operator fun invoke(userId: String, walletDto: WalletDto): Flow<Resource<Wallet>> = flow {
        try {
            emit(Resource.Loading<Wallet>())
            repository.createWallet(userId, walletDto)
            emit(Resource.Success<Wallet>(walletDto.toWallet()))
        } catch (e: HttpException) {
            emit(Resource.Error<Wallet>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) { // no internet connection / server is offline
            emit(Resource.Error<Wallet>( "Couldn't reach server. Check your internet connection"))
        }
    }

}