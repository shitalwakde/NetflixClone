package com.netflixclone.screens.login

import android.app.Application
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import com.netflixclone.common.AuthManager
import com.netflixclone.common.SingleLiveEvent
import com.netflixclone.screens.common.BaseViewModel
import com.netflixclone.screens.common.CommonViewModel

class LoginViewModel(private val authManager: AuthManager,
                    private val app: Application,
                    private val commonViewModel: CommonViewModel,
                    onFailureListener: OnFailureListener) : BaseViewModel(onFailureListener){
    private val _goToHomeScreen = SingleLiveEvent<Unit>()
    val goToHomeScreen: LiveData<Unit> = _goToHomeScreen
    private val _goToRegisterScreen = SingleLiveEvent<Unit>()
    val goToRegisterScreen: LiveData<Unit> = _goToRegisterScreen


    fun onLoginClick(email: String, password: String){
        if(validate(email, password)){
            authManager.signIn(email, password).addOnSuccessListener {
                _goToHomeScreen.value = Unit
            }.addOnFailureListener(onFailureListener)
        }   else {
            commonViewModel.setErrorMessage("Please enter email and password")
        }
    }

    private fun validate(email: String, password: String) =
        email.isNotEmpty() && password.isNotEmpty()


    fun onRegisterClick(){
        _goToRegisterScreen.call()
    }
}