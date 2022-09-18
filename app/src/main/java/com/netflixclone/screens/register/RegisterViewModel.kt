package com.netflixclone.screens.register

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.netflixclone.common.SingleLiveEvent
import com.netflixclone.data.UsersRepository
import com.netflixclone.data_models.User
import com.netflixclone.screens.common.BaseViewModel
import com.netflixclone.screens.common.CommonViewModel

class RegisterViewModel(private val commonViewModel: CommonViewModel,
                        private val app: Application,
                        onFailureListener: OnFailureListener,
                        private val usersRepo: UsersRepository) : BaseViewModel(onFailureListener) {

    private var email: String? = null
    private val _goToNamePassScreen = SingleLiveEvent<Unit>()
    private val _goToHomeScreen = SingleLiveEvent<Unit>()
    private val _goBackToEmailScreen = SingleLiveEvent<Unit>()

    val goToNamePassScreen = _goToNamePassScreen
    val goToHomeScreen = _goToHomeScreen
    val goBackToEmailScreen = _goBackToEmailScreen

    fun onEmailEntered(email: String){
        if(email.isNotEmpty()){
            this.email = email
            usersRepo.isUserExistsForEmail(email).addOnSuccessListener { exists ->
                if (!exists){
                    _goToNamePassScreen.call()
                }else {
                    commonViewModel.setErrorMessage("This email already exists")
                }
            }.addOnFailureListener(onFailureListener)
        }else {
            commonViewModel.setErrorMessage("Please enter email")
        }
    }


    fun onRegister(fullName: String, password: String){
        if(fullName.isNotEmpty() && password.isNotEmpty()){
            val localEmail = email
            if(localEmail != null){
                usersRepo.createUser(mkUser(fullName, localEmail), password).addOnSuccessListener {
                    _goToHomeScreen.call()
                }.addOnFailureListener(onFailureListener)
            }else {
                Log.e(RegisterActivity.TAG, "onRegister: email is null")
                commonViewModel.setErrorMessage("Please enter email")
                _goBackToEmailScreen.call()
            }
        }else {
            commonViewModel.setErrorMessage("Please enter full name and password")
        }
    }


    private fun mkUser(fullName: String, email: String): User {
        val username = mkUsername(fullName)
        return User(name = fullName, username = username, email = email)
    }

    private fun mkUsername(fullName: String) =
        fullName.toLowerCase().replace("", "")




}