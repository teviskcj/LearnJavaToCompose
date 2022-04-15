package com.example.composesfo.presentation.wallet

import com.example.composesfo.domain.model.Wallet

data class WalletState(
    val isLoading: Boolean = false,
    val wallet: Wallet? = null,
    val error: String = ""
)
