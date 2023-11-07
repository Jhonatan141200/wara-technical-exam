package com.vasquez.kotlinapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vasquez.kotlinapp.databinding.RowNoteUpBinding
import com.vasquez.kotlinapp.model.Employee

/**
 * @author Eduardo Medina
 */
class NoteAdapter(
    private var notes: List<Employee>,
    private val itemCallback: (note: Employee) -> Unit?
) : RecyclerView.Adapter<
        NoteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        /*val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_note_up,
            parent, false
        )
        return NoteViewHolder(view)*/
        val viewBinding =
            RowNoteUpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    fun update(data: List<Employee>) {
        notes = data
        notifyDataSetChanged()

        //DiffUtils
        /*val diffCallback = NoteDiffUtilCallback(notes, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        notes = data
        diffResult.dispatchUpdatesTo(this)*/
    }

    //class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    inner class NoteViewHolder(private val viewBinding: RowNoteUpBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(note: Employee) {
            viewBinding.tviName.text = note.firstName?.capitalize()
            viewBinding.tviDesc.text = note.lastName
            viewBinding.tviDate.text = ""

            viewBinding.root.setOnClickListener {
                itemCallback(note)
            }
        }
    }
}