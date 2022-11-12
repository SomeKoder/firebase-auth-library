package com.somekoder.library.firebase_auth.usecase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class CreateUserEmailPasswordUseCaseImpl(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : CreateUserEmailPasswordUseCase {
    override suspend fun execute(email: String, password: String) : Result<AuthResult> = withContext(coroutineContext) {
        try {
            Result.success(Firebase.auth.createUserWithEmailAndPassword(email, password).await())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}