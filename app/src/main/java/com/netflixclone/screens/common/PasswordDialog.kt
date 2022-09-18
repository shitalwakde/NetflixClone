package com.netflixclone.screens.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.netflixclone.R
import com.netflixclone.databinding.DialogPasswordBinding


class PasswordDialog :DialogFragment() {
    private lateinit var mListener: Listener
    private lateinit var binding: DialogPasswordBinding

    interface Listener{
        fun onPasswordConfirm(password: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPasswordBinding.inflate(layoutInflater)
//        val view = activity!!.layoutInflater.inflate(R.layout.dialog_password, null)
        return AlertDialog.Builder(activity, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(binding.root)
            .setTitle("Please enter your password")
            .setPositiveButton(android.R.string.ok, {_, _ ->
                mListener.onPasswordConfirm(binding.passwordInput.text.toString())
            })
            .setNegativeButton(android.R.string.cancel, {_, _ ->
                //do nothing
            })
            .create()
    }


}