package com.vasquez.kotlinapp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.vasquez.kotlinapp.extensions.nToast
import com.vasquez.kotlinapp.databinding.FragmentAddNoteBinding
import com.vasquez.kotlinapp.storage.EmployeeRepository
import com.vasquez.kotlinapp.storage.remote.EmployeeRaw
import com.vasquez.kotlinapp.storage.remote.NoteApiClient
import com.vasquez.kotlinapp.storage.remote.RemoteEmployeeDataSource
import com.vasquez.kotlinapp.viewmodel.AddEmployeeViewModel
import com.vasquez.kotlinapp.viewmodel.AddViewModelFactory

/**
 * @author Eduardo Medina
 */
class AddNoteFragment : Fragment() {

    private val viewModel by viewModels<AddEmployeeViewModel> {
        AddViewModelFactory(
            EmployeeRepository(
                RemoteEmployeeDataSource(
                    NoteApiClient.instance())))
    }

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!


    private var firstname: String = ""
    private var lastname: String = ""
    private var dni: String = ""
    private var age: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //userId = arguments?.getString("USER_ID","")?:""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_add_note, container, false)
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        binding.btnAddNote.setOnClickListener {
            if (validateForm()) {
                //TODO agregar nota
                val employeeRaw = EmployeeRaw(firstname, lastname, dni, age)
                viewModel.save(employeeRaw)
            }
        }
    }

    private fun setObservers() {
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            showErrorMessage(it ?: "Ocurrió un error")
            //findNavController().popBackStack()
        })

        viewModel.onSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                showErrorMessage("¡Exitoso! + $it")
                //findNavController().popBackStack()
            }
        })
    }

    private fun showErrorMessage(error: String) {
        nToast(error)
    }
    private fun validateForm(): Boolean {
        clearForm()
        firstname = binding.edtFirstName.text.toString().trim()
        lastname = binding.edtLastName.text.toString().trim()
        dni = binding.edtDni.text.toString().trim()
        age = binding.edtEdad.text.toString().toInt()

        if (firstname.isNullOrEmpty()) {
            binding.edtFirstName.error = "Campo nombre inválido"
            return false
        }

        if (lastname.isNullOrEmpty()) {
            binding.edtLastName.error = "Campo apellido inválido"
            return false
        }
        if (dni.isNullOrEmpty()) {
            binding.edtDni.error = "Campo dni inválido"
            return false
        }
        if (age == 0) {
            binding.edtEdad.error = "Campo edad inválido"
            return false
        }

        return true
    }


    private fun showMessage(item: String) {
        //Toast.makeText(requireContext(), "item $item", Toast.LENGTH_SHORT).show()
        Log.v("CONSOLE", "item $item")
        nToast("item $item")
    }

    private fun clearForm() {
        binding.edtFirstName.error = null
        binding.edtLastName.error = null
        binding.edtDni.error = null
        binding.edtEdad.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddNoteFragment()
    }
}