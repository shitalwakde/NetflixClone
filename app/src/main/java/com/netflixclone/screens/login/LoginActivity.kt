package com.netflixclone.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.netflixclone.R
import com.netflixclone.common.KeyboardVisibilityEvent
import com.netflixclone.common.KeyboardVisibilityEventListener
import com.netflixclone.common.coordinateBtnAndInputs
import com.netflixclone.databinding.ActivityLoginBinding
import com.netflixclone.screens.BaseActivity
import com.netflixclone.screens.BottomNavActivity
import com.netflixclone.screens.register.RegisterActivity

class LoginActivity : BaseActivity(), KeyboardVisibilityEventListener, View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e(TAG, "onCreate")

        KeyboardVisibilityEvent.setEventListener(this, this)
        coordinateBtnAndInputs(binding.loginBtn, binding.emailInput, binding.passwordInput)

        binding.loginBtn.setOnClickListener(this)
        binding.createAccountText.setOnClickListener(this)


        mViewModel.goToHomeScreen.observe(this, Observer {
            startActivity(Intent(this, BottomNavActivity::class.java))
            finish()
        })

        mViewModel.goToRegisterScreen.observe(this, Observer {
            startActivity(Intent(this, RegisterActivity::class.java))
        })

        mAuth = FirebaseAuth.getInstance()
    }



    override fun onClick(view: View) {
        when(view.id){
            R.id.login_btn ->
                mViewModel.onLoginClick(
                    email = binding.emailInput.text.toString(),
                    password = binding.passwordInput.text.toString()
                )
            R.id.create_account_text -> mViewModel.onRegisterClick()
        }
    }


    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if(isKeyboardOpen){
            binding.createAccountText.visibility = View.GONE
        }else {
            binding.createAccountText.visibility = View.VISIBLE
        }
    }


    companion object{
        const val TAG = "LoginActivity"
    }


}