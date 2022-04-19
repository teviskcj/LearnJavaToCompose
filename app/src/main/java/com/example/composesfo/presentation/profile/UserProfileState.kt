package com.example.composesfo.presentation.profile

import com.example.composesfo.domain.model.UserProfile

data class UserProfileState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val error: String = ""
)
