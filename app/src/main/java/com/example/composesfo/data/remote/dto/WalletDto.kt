package com.example.composesfo.data.remote.dto

import com.example.composesfo.domain.model.Wallet

data class WalletDto(
    val phone: String,
    val walletAmount: Int
)

fun WalletDto.toWallet(): Wallet {
    return Wallet(
        phone = phone,
        walletAmount = walletAmount
    )
}