package com.netflixclone.screens

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.wrappers.InstantApps
import com.netflixclone.R
import com.netflixclone.common.showToast
import com.netflixclone.screens.common.BaseViewModel
import com.netflixclone.screens.common.CommonViewModel
import com.netflixclone.screens.login.LoginActivity

abstract class BaseActivity : AppCompatActivity() {
    lateinit var commonViewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        commonViewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
        commonViewModel.errorMessages.observe(this, {
            it?.let {
                showToast(it)
            }
        })
    }


    fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    companion object{
        const val TAG = "BaseActivity"
    }


    fun showBackIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,
            R.color.black)))
    }


}