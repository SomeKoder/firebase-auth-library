package com.somekoder.library.firebase_auth.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class ForgotPasswordUseCaseImpl(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : ForgotPasswordUseCase {
    override suspend fun execute(email: String): Unit = withContext(coroutineContext) {
        Firebase.auth.sendPasswordResetEmail(email).await()
    }
}