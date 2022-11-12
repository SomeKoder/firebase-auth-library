package com.somekoder.firebase_auth.sample.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.somekoder.firebase_auth.sample.presentation.common.util.collectLifecycleFlow
import com.somekoder.firebase_auth.sample.presentation.common.util.setTextIfDifferent
import com.somekoder.firebase_auth_library.sample.R
import com.somekoder.firebase_auth_library.sample.databinding.FragmentEnterPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterPasswordFragment : Fragment() {

    private var _binding: FragmentEnterPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel by hiltNavGraphViewModels<LoginViewModel>(R.id.nav_graph_authentication)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEnterPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonConfirmPassword.setOnClickListener {
            viewModel.setIntent(AuthenticationIntent.NavigateToConfirmPasswordPage)
        }

        binding.editTextPassword.doAfterTextChanged {
            viewModel.setIntent(AuthenticationIntent.UpdateEnterPasswordText(it.toString()))
        }

        collectLifecycleFlow(viewModel.uiState) { state ->
            binding.editTextPassword.setTextIfDifferent(state.enterPasswordText)
        }

        collectLifecycleFlow(viewModel.uiEffect) { effect ->
            when (effect) {
                is AuthenticationEffect.NavigateToConfirmPasswordPage -> {
                    val action = EnterPasswordFragmentDirections.actionEnterPasswordFragmentToConfirmPasswordFragment()
                    findNavController().navigate(action)
                }
                is AuthenticationEffect.ShowToastMessage -> {
                    Toast.makeText(requireContext(), getString(effect.message, *effect.args), Toast.LENGTH_SHORT).show()
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