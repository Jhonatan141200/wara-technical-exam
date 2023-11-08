package com.vasquez.kotlinapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vasquez.kotlinapp.R
import com.vasquez.kotlinapp.databinding.FragmentLogInBinding
import com.vasquez.kotlinapp.extensions.nToast
import com.vasquez.kotlinapp.model.User
import com.vasquez.kotlinapp.storage.AuthenticationRepository
import com.vasquez.kotlinapp.storage.remote.NoteApiClient
import com.vasquez.kotlinapp.storage.remote.RemoteAuthenticationDataSource
import com.vasquez.kotlinapp.viewmodel.LogInViewModel
import com.vasquez.kotlinapp.viewmodel.LogInViewModelFactory

/**
 * @author Vasquez Reyna J
 */
class LogInFragment : Fragment() {

    private val viewModel by viewModels<LogInViewModel> {
        LogInViewModelFactory(
            AuthenticationRepository(
                RemoteAuthenticationDataSource(
                    NoteApiClient.instance()
                )
            )
        )
    }

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("CONSOLE", "LogInFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun validateForm(): Boolean {
        if (binding.editTextTextEmailAddress.text.isEmpty()) {
            binding.editTextTextEmailAddress.error = "Campo email requerido"
            return false
        }
        if (!Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$").matches(binding.editTextTextEmailAddress.text.toString())) {
            binding.editTextTextEmailAddress.error = "Ingrese un email válido"
            return false
        }
        if (binding.editTextTextPassword.text.isEmpty()) {
            binding.editTextTextPassword.error = "Campo password requerido"
            return false
        }
        return true
    }

    private fun showErrorMessage(error: String) {
        nToast(error)
    }

    private fun goToMainView(user: User) {
        findNavController().navigate(R.id.action_logInFragment_to_mainFragment,
            Bundle().apply {
                putSerializable("USER", user)
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("CONSOLE", "LogInFragment onViewCreated")
        setObservers()

        //mock
        binding.editTextTextEmailAddress.setText("jhona141200@gmail.com")
        binding.editTextTextPassword.setText("Wara$.")

        binding.button.setOnClickListener {
            if (validateForm()) {
                val username = binding.editTextTextEmailAddress.text.toString().trim()
                val password = binding.editTextTextPassword.text.toString().trim()

                viewModel.login(username, password)
            }
        }
    }

    private fun setObservers() {
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            showErrorMessage(it ?: "Ocurrió un error")
        })

        viewModel.isAuthenticated.observe(viewLifecycleOwner, Observer {
            it?.let { itUser ->
                showErrorMessage("¡Bienvenido ${it.firstName}!")
                goToMainView(itUser)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = LogInFragment()
    }
}