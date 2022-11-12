package com.somekoder.library.firebase_auth.usecase

interface ForgotPasswordUseCase {
    suspend fun execute(email: String)
}