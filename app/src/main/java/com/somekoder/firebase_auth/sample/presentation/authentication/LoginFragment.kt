package com.somekoder.firebase_auth.sample.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.somekoder.firebase_auth.sample.presentation.common.util.collectLifecycleFlow
import com.somekoder.firebase_auth.sample.presentation.common.util.setTextIfDifferent
import com.somekoder.firebase_auth_library.sample.R
import com.somekoder.firebase_auth_library.sample.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by hiltNavGraphViewModels<LoginViewModel>(R.id.nav_graph_authentication)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            viewModel.setIntent(AuthenticationIntent.Login(email = binding.editTextEmail.text.toString(), password = binding.editTextPassword.text.toString()))
        }

        binding.textViewCreateAccount.setOnClickListener {
            viewModel.setIntent(AuthenticationIntent.NavigateToEnterEmailPage)
        }

        binding.editTextEmail.doAfterTextChanged {
            viewModel.setIntent(AuthenticationIntent.UpdateEmailText(it.toString()))
        }

        binding.editTextPassword.doAfterTextChanged {
            viewModel.setIntent(AuthenticationIntent.UpdateLoginPasswordText(it.toString()))
        }

        collectLifecycleFlow(viewModel.uiState) { state ->
            binding.editTextEmail.setTextIfDifferent(state.emailText)
            binding.editTextPassword.setTextIfDifferent(state.loginPasswordText)
            binding.progressBar.isVisible = state.isLoading
            if (state.isLoggedIn) {
                Toast.makeText(requireContext(), "Logged In", Toast.LENGTH_SHORT).show()
            }
        }

        collectLifecycleFlow(viewModel.uiEffect) { effect ->
            when (effect) {
                is AuthenticationEffect.ShowToastMessage -> {
                    Toast.makeText(requireContext(), getString(effect.message, *effect.args), Toast.LENGTH_SHORT).show()
                }
                is AuthenticationEffect.NavigateToEnterEmailPage -> {
                    val action = LoginFragmentDirections.actionLoginFragmentToEnterEmailFragment()
                    findNavController().navigate(action)
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}