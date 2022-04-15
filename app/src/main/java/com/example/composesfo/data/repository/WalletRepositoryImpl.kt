package com.example.composesfo.data.repository

import com.example.composesfo.data.remote.SFOApi
import com.example.composesfo.data.remote.dto.WalletDto
import com.example.composesfo.domain.repository.WalletRepository
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val api: SFOApi
) : WalletRepository {
    override suspend fun getWalletById(userId: String): WalletDto {
        return api.getWalletById(userId)
    }

    override suspend fun createWallet(userId: String, walletDto: WalletDto) {
        return api.createWallet(userId, walletDto)
    }
}