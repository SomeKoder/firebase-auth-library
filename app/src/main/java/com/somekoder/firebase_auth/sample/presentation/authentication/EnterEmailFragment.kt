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
import com.somekoder.firebase_auth_library.sample.databinding.FragmentEnterEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterEmailFragment : Fragment() {

    private var _binding: FragmentEnterEmailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by hiltNavGraphViewModels<LoginViewModel>(R.id.nav_graph_authentication)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEnterEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonConfirmEmail.setOnClickListener {
            viewModel.setIntent(AuthenticationIntent.NavigateToEnterPasswordPage)
        }

        binding.editTextEmail.doAfterTextChanged {
            viewModel.setIntent(AuthenticationIntent.UpdateEmailText(it.toString()))
        }

        collectLifecycleFlow(viewModel.uiState) { state ->
            binding.editTextEmail.setTextIfDifferent(state.emailText)
        }

        collectLifecycleFlow(viewModel.uiEffect) { effect ->
            when (effect) {
                is AuthenticationEffect.NavigateToEnterPasswordPage -> {
                    val action = EnterEmailFragmentDirections.actionEnterEmailFragmentToEnterPasswordFragment()
                    findNavController().navigate(action)
                }
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