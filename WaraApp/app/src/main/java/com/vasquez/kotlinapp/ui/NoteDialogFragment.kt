package com.vasquez.kotlinapp.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * @author Vasquez Reyna J
 */
class NoteDialogFragment : DialogFragment() {

    private var listener: DialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString("TITLE")
        val type = arguments?.getInt("TYPE")

        return AlertDialog.Builder(activity!!)
            .setTitle(title)
            .setPositiveButton("ACEPTAR") { _, _ ->
                listener?.onPositiveListener(null, type ?: 0)
            }
            .setNegativeButton("CANCELAR") { _, _ ->
                listener?.onNegativeListener(null, type ?: 0)
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //TODO para invocar desde un fragment
        if (targetFragment is DialogListener) {
            listener = targetFragment as? DialogListener
        }

        if (context is DialogListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface DialogListener {
        fun onPositiveListener(any: Any?, type: Int)
        fun onNegativeListener(any: Any?, type: Int)
    }
}