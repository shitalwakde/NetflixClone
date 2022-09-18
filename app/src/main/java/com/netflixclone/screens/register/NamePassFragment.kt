package com.netflixclone.screens.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.netflixclone.databinding.FragmentRegisterNamepassBinding
import com.netflixclone.common.coordinateBtnAndInputs

class NamePassFragment : Fragment() {
    private lateinit var mListener: Listener
    private lateinit var binding: FragmentRegisterNamepassBinding


    interface Listener{
        fun onRegister(fullName: String, password: String)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterNamepassBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        coordinateBtnAndInputs(binding.registerBtn, binding.fullNameInput, binding.passwordInput)

        binding.registerBtn.setOnClickListener {
            val fullName = binding.fullNameInput.text.toString()
            val password = binding.passwordInput.text.toString()

            mListener.onRegister(fullName, password)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }


}