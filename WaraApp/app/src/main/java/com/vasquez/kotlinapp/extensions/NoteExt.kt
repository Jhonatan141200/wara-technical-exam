package com.vasquez.kotlinapp.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vasquez.kotlinapp.storage.AuthenticationRepository

/**
 * @author Vasquez Reyna J
 */


fun Fragment.nToast(value: String) {
    Toast.makeText(requireContext(),value, Toast.LENGTH_LONG).show()
}

fun AuthenticationRepository.showCurrentThread() {
    Log.v("NOTE-THREAD","repository ${Thread.currentThread().name} ${Thread.currentThread().state.name}")
}