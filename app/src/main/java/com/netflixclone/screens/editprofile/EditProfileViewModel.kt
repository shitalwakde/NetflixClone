package com.netflixclone.screens.editprofile

import android.net.Uri
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.netflixclone.data.UsersRepository
import com.netflixclone.data_models.User
import com.netflixclone.screens.common.BaseViewModel

class EditProfileViewModel (onFailureListener: OnFailureListener,
                   private val usersRepo: UsersRepository) : BaseViewModel(onFailureListener){
        val user: LiveData<User> = usersRepo.getUser()

    fun uploadAndSetUserPhoto(localImage: Uri): Task<Unit> =
        usersRepo.uploadUserPhoto(localImage).onSuccessTask { downloadUrl ->
            usersRepo.updateUserPhoto(downloadUrl!!)
        }.addOnFailureListener(onFailureListener)

    fun updateEmail(currentEmail: String, newEmail: String, password: String): Task<Unit> =
        usersRepo.updateEmail(currentEmail = currentEmail, newEmail = newEmail, password = password)
            .addOnFailureListener(onFailureListener)

    fun updateUserProfile(currentUser: User, newUser: User): Task<Unit> =
        usersRepo.updateUserProfile(currentUser = currentUser, newUser = newUser).
        addOnFailureListener(onFailureListener)
}