package com.example.composesfo.common

class CurrentUserState {
    companion object {
        lateinit var userId: String
        var currentAmount = 0
        var reloadAmount = 0
        var totalAmount = 0
        var language = "English"
    }
}