package com.somekoder.firebase_auth.sample.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.somekoder.firebase_auth.sample.presentation.common.util.collectLifecycleFlow
import com.somekoder.firebase_auth.sample.presentation.common.util.setTextIfDifferent
import com.somekoder.firebase_auth_library.sample.R
import com.somekoder.firebase_auth_library.sample.databinding.FragmentConfirmPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmPasswordFragment : Fragment() {

    private var _binding: FragmentConfirmPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel by hiltNavGraphViewModels<LoginViewModel>(R.id.nav_graph_authentication)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfirmPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextPassword.doAfterTextChanged {
            viewModel.setIntent(AuthenticationIntent.UpdateConfirmedPasswordText(it.toString()))
        }

        binding.buttonConfirmPassword.setOnClickListener {
            viewModel.setIntent(AuthenticationIntent.CreateAccount)
        }

        collectLifecycleFlow(viewModel.uiState) { state ->
            binding.editTextPassword.setTextIfDifferent(state.confirmPasswordText)
            if (state.isLoggedIn) {
                Toast.makeText(requireContext(), "Logged In", Toast.LENGTH_SHORT).show()
            }
        }

        collectLifecycleFlow(viewModel.uiEffect) { effect ->
            when (effect) {
                is AuthenticationEffect.ShowToastMessage -> {
                    Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
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