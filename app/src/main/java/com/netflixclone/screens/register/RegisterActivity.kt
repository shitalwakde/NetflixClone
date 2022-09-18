package com.netflixclone.screens.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.netflixclone.R
import com.netflixclone.databinding.ActivityRegisterBinding
import com.netflixclone.screens.BaseActivity
import com.netflixclone.screens.BottomNavActivity

class RegisterActivity : BaseActivity(), EmailFragment.Listener, NamePassFragment.Listener {

    private lateinit var mViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel.goToNamePassScreen.observe(this, Observer {
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, NamePassFragment())
                .addToBackStack(null)
                .commit()
        })
        mViewModel.goToHomeScreen.observe(this, Observer {
            startHomeActivity()
        })
        mViewModel.goBackToEmailScreen.observe(this, Observer {
            supportFragmentManager.popBackStack()
        })

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().add(R.id.frame_layout, EmailFragment())
                .commit()
        }
    }

    override fun onNext(email: String) {
        mViewModel.onEmailEntered(email)
    }

    override fun onRegister(fullName: String, password: String) {
        mViewModel.onRegister(fullName, password)
    }

    private fun startHomeActivity(){
        startActivity(Intent(this, BottomNavActivity::class.java))
        finish()
    }


    companion object {
        const val TAG = "RegisterActivity"
    }
}