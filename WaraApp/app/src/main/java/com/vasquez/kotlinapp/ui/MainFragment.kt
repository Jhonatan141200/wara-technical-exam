package com.vasquez.kotlinapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasquez.kotlinapp.R
import com.vasquez.kotlinapp.databinding.FragmentMainBinding
import com.vasquez.kotlinapp.extensions.nToast
import com.vasquez.kotlinapp.model.Employee
import com.vasquez.kotlinapp.model.User
import com.vasquez.kotlinapp.storage.EmployeeRepository
import com.vasquez.kotlinapp.storage.remote.NoteApiClient
import com.vasquez.kotlinapp.storage.remote.RemoteEmployeeDataSource
import com.vasquez.kotlinapp.viewmodel.EmployeeViewModel
import com.vasquez.kotlinapp.viewmodel.EmployeeViewModelFactory

/**
 * @author Vasquez Reyna J
 */
class MainFragment : Fragment() {

    private val viewModel by viewModels<EmployeeViewModel> {
        EmployeeViewModelFactory(
            EmployeeRepository(
                RemoteEmployeeDataSource(
                    NoteApiClient.instance())))
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var user: User? = null
    private lateinit var employeeList: List<Employee>;
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getSerializable("USER") as? User
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        ui()
    }

    private fun ui() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter =
            EmployeeAdapter(emptyList()) { itEmployee ->
                showMessage("item $itEmployee")
                goToNote(itEmployee)
            }
        binding.recyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener {
            //TODO ir a crear nota
            goToAddNote()
        }
        binding.edtBuscador.addTextChangedListener {
            val dni = binding.edtBuscador.text.toString()
            adapter.update(employeeList.filter {
                it.dni.contains(dni);
            })
        }

    }

    private fun setObservers() {
        viewModel.notes.observe(viewLifecycleOwner, Observer {
            employeeList = it;
            adapter.update(it)
        })
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            showMessage(it ?: "Ocurrió un error")
        })
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        viewModel.fetchEmployees()
    }

    private fun goToAddNote() {
        //startActivity(Intent(requireContext(), AddNoteActivity::class.java))
        findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment, Bundle().apply {
            //putString("USER_ID", user?.id ?: "")
        })
    }

    private fun goToNote(employee: Employee) {
        /*val bundle = Bundle()
        bundle.putSerializable("NOTE", note)
        val intent = Intent(requireContext(), EditNoteActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)*/
        findNavController().navigate(R.id.action_mainFragment_to_editNoteFragment, Bundle().apply {
            putSerializable("NOTE", employee)
        })
    }

    private fun showMessage(item: String) {
        //Toast.makeText(requireContext(), "item $item", Toast.LENGTH_SHORT).show()
        Log.v("CONSOLE", "item $item")
        nToast("item $item")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}