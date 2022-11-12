package com.somekoder.library.firebase_auth.usecase

import com.google.firebase.auth.AuthResult

interface CreateUserEmailPasswordUseCase {
    suspend fun execute(email: String, password: String) : Result<AuthResult>
}