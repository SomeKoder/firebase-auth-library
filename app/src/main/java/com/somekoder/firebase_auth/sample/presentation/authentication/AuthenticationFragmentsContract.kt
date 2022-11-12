package com.somekoder.firebase_auth.sample.presentation.authentication

sealed class AuthenticationIntent {

    data class Login(val email: String, val password: String) : AuthenticationIntent()
    object CreateAccount : AuthenticationIntent()

    data class UpdateEmailText(val emailText: String) : AuthenticationIntent()
    data class UpdateEnterPasswordText(val enterPassword: String) : AuthenticationIntent()
    data class UpdateConfirmedPasswordText(val confirmedPassword: String) : AuthenticationIntent()
    data class UpdateLoginPasswordText(val loginPassword: String) : AuthenticationIntent()

    object NavigateToEnterEmailPage : AuthenticationIntent()
    object NavigateToEnterPasswordPage : AuthenticationIntent()
    object NavigateToConfirmPasswordPage : AuthenticationIntent()
}

data class AuthenticationState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val emailText: String = "",
    val loginPasswordText: String = "",
    val enterPasswordText: String = "",
    val confirmPasswordText: String = "",
)

sealed class AuthenticationEffect {
    class ShowToastMessage(val message: Int, val args: Array<Any> = emptyArray()) : AuthenticationEffect()
    object NavigateToEnterPasswordPage : AuthenticationEffect()
    object NavigateToConfirmPasswordPage : AuthenticationEffect()
    object NavigateToEnterEmailPage : AuthenticationEffect()
}