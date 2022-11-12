package com.somekoder.firebase_auth.sample.presentation.authentication

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.somekoder.firebase_auth.sample.presentation.common.viewmodel.BaseViewModel
import com.somekoder.firebase_auth_library.sample.R
import com.somekoder.library.firebase_auth.usecase.CreateUserEmailPasswordUseCase
import com.somekoder.library.firebase_auth.usecase.ForgotPasswordUseCase
import com.somekoder.library.firebase_auth.usecase.LoginEmailPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginEmailPasswordUseCase: LoginEmailPasswordUseCase,
    private val createUserEmailPasswordUseCase: CreateUserEmailPasswordUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : BaseViewModel<AuthenticationIntent, AuthenticationState, AuthenticationEffect>() {

    override fun createInitialState(): AuthenticationState {
        return AuthenticationState(isLoggedIn = Firebase.auth.currentUser != null)
    }

    override fun handleEvent(intent: AuthenticationIntent) {
        when (intent) {
            is AuthenticationIntent.UpdateEmailText -> setState { copy(emailText = intent.emailText) }
            is AuthenticationIntent.UpdateEnterPasswordText -> setState { copy(enterPasswordText = intent.enterPassword) }
            is AuthenticationIntent.UpdateLoginPasswordText -> setState { copy(loginPasswordText = intent.loginPassword) }
            is AuthenticationIntent.UpdateConfirmedPasswordText -> setState { copy(confirmPasswordText = intent.confirmedPassword) }

            is AuthenticationIntent.CreateAccount -> createAccount()
            is AuthenticationIntent.Login -> login(intent)

            is AuthenticationIntent.NavigateToEnterEmailPage -> setEffect { AuthenticationEffect.NavigateToEnterEmailPage }
            is AuthenticationIntent.NavigateToEnterPasswordPage -> tryNavigateToEnterPasswordPage()
            is AuthenticationIntent.NavigateToConfirmPasswordPage -> tryNavigateToConfirmPasswordPage()
        }
    }

    private fun tryNavigateToEnterPasswordPage() {
        // Validate email is valid
        setEffect { AuthenticationEffect.NavigateToEnterPasswordPage }
    }

    private fun tryNavigateToConfirmPasswordPage() {
        // Validate password is long and safe enough
        if (currentState.enterPasswordText.length < MINIMUM_PASSWORD_LENGTH) {
            setEffect { AuthenticationEffect.ShowToastMessage(R.string.fragment_enter_password_error_length, arrayOf(MINIMUM_PASSWORD_LENGTH)) }
        }
        else {
            setEffect { AuthenticationEffect.NavigateToConfirmPasswordPage }
        }
    }

    private fun login(intent: AuthenticationIntent.Login) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val result = loginEmailPasswordUseCase.execute(intent.email, intent.password)
            setState { copy(isLoading = false, isLoggedIn = result.isSuccess) }
            if (result.isFailure) {
                result.exceptionOrNull()?.printStackTrace()
                setEffect { AuthenticationEffect.ShowToastMessage(R.string.error) }
            }
        }
    }

    private fun createAccount() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val result = createUserEmailPasswordUseCase.execute(email = currentState.emailText, password = currentState.enterPasswordText)
            result.exceptionOrNull()?.printStackTrace()
            result.exceptionOrNull()?.let { setEffect { AuthenticationEffect.ShowToastMessage(R.string.error) } }
            result.getOrNull()?.let { setState { copy(isLoggedIn = true, isLoading = false) } }
        }
    }

    companion object {
        private const val MINIMUM_PASSWORD_LENGTH = 8
    }
}