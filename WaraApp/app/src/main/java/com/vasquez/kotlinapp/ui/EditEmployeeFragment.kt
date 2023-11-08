package com.vasquez.kotlinapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vasquez.kotlinapp.R
import com.vasquez.kotlinapp.databinding.FragmentEditNoteBinding
import com.vasquez.kotlinapp.model.Employee

/**
 * @author Vasquez Reyna J
 */
class EditEmployeeFragment : Fragment(), NoteDialogFragment.DialogListener {
    //TODO Injectar capa de datos

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private var note: Employee? = null
    private var name: String = ""
    private var desc: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        note = arguments?.getSerializable("NOTE") as? Employee
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_edit_note, container, false)
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populate()
        //events
        binding.btnEditNote.setOnClickListener {
            if (validateForm()) {
                //TODO editar nota
                editNote()
                //TODO regresar a la pantalla anterior
                findNavController().popBackStack()
            }
        }

        //TODO se reemplazó por el menu
        /*
        binding.btnDeleteNote.setOnClickListener {
            showNoteDialog()
        }*/
    }

    //TODO delete button
    //https://developer.android.com/guide/fragments/appbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                //TODO eliminar note
                showNoteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun editNote() {
        /*
        val noteId:String = note?.id?:""
        val nNote = Note(noteId, name, desc,note?.userId)
        */
    }

    private fun validateForm(): Boolean {
        name = binding.edtFirstName.text.toString()
        desc = binding.edtLastName.text.toString()

        if (name.isNullOrEmpty()) {
            return false
        }

        if (desc.isNullOrEmpty()) {
            return false
        }

        return true
    }

    private fun populate() {
        /*
        note?.let {
            binding.eteName.setText(it.name)
            binding.eteDesc.setText(it.description)
        }
        */
    }

    private fun showNoteDialog() {
        val noteDialogFragment = NoteDialogFragment()
        val bundle = Bundle().apply {
            putString("TITLE", "¿Deseas eliminar esta nota?")
            putInt("TYPE", 100)
        }
        noteDialogFragment.arguments = bundle
        noteDialogFragment.setTargetFragment(this, 0)
        noteDialogFragment.show(requireFragmentManager(), "NoteDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditEmployeeFragment()
    }

    override fun onPositiveListener(any: Any?, type: Int) {
        //TODO eliminar nota

        //TODO regresar a la pantalla anterior
        findNavController().popBackStack()
    }

    override fun onNegativeListener(any: Any?, type: Int) {}
}