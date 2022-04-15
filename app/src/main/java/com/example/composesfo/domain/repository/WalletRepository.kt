package com.example.composesfo.domain.repository

import com.example.composesfo.data.remote.dto.WalletDto

interface WalletRepository {
    suspend fun getWalletById(userId: String): WalletDto

    suspend fun createWallet(userId: String, walletDto: WalletDto)
}