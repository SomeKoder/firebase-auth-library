package com.somekoder.library.firebase_auth.usecase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface LoginEmailPasswordUseCase {
    suspend fun execute(email: String, password: String) : Result<AuthResult>
}