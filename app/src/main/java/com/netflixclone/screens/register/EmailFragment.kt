package com.netflixclone.screens.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.netflixclone.databinding.FragmentRegisterEmailBinding
import com.netflixclone.common.coordinateBtnAndInputs

class EmailFragment : Fragment() {
    private lateinit var mListener: Listener
    private lateinit var binding: FragmentRegisterEmailBinding

    interface Listener{
        fun onNext(email: String)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterEmailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        coordinateBtnAndInputs(binding.nextBtn, binding.emailInput)

        binding.nextBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            mListener.onNext(email)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }


}