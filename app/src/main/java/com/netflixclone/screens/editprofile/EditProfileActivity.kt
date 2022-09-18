package com.netflixclone.screens.editprofile

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.netflixclone.common.loadUserPhoto
import com.netflixclone.common.showToast
import com.netflixclone.data_models.User
import com.netflixclone.databinding.ActivityEditProfileBinding
import com.netflixclone.screens.BaseActivity
import com.netflixclone.screens.common.CameraHelper
import com.netflixclone.screens.common.PasswordDialog
import com.netflixclone.screens.common.setupAuthGuard

class EditProfileActivity : BaseActivity(), PasswordDialog.Listener {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var mUser: User
    private lateinit var mPendingUser: User
    private lateinit var mCamera: CameraHelper
    private lateinit var mViewModel: EditProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backImage.setOnClickListener { (finish()) }
        binding.saveImage.setOnClickListener { updateProfile() }
        binding.changePhotoText.setOnClickListener { mCamera.takeCameraPicture() }

        setupAuthGuard{
            mViewModel.user.observe(this, Observer {
                it?.let {
                    mUser = it
                    binding.nameInput.setText(mUser.name)
                    binding.usernameInput.setText(mUser.username)
                    binding.websiteInput.setText(mUser.website)
                    binding.bioInput.setText(mUser.bio)
                    binding.emailInput.setText(mUser.email)
                    binding.profileImage.loadUserPhoto(mUser.photo)
                    binding.phoneInput.setText(mUser.phone?.toString())
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == mCamera.REQUEST_CODE && resultCode == RESULT_OK) {
            mViewModel.uploadAndSetUserPhoto(mCamera.imageUri!!)
        }
    }

    override fun onPasswordConfirm(password: String) {
        if(password.isNotEmpty()){
        mViewModel.updateEmail(
            currentEmail = mUser.email,
            newEmail = mPendingUser.email,
            password = password)
            .addOnSuccessListener { updateUser(mPendingUser) }
        }else {
            showToast("You should enter your password")
        }
    }

    private fun updateUser(user: User){
        mViewModel.updateUserProfile(currentUser =  mUser, newUser = user)
            .addOnSuccessListener {
                showToast("Profile saved")
                finish()
            }
    }

    private fun updateProfile(){
        mPendingUser = readInputs()
        val error = validate(mPendingUser)
        if (error == null) {
            if (mPendingUser.email == mUser.email) {
                updateUser(mPendingUser)
            } else {
                PasswordDialog().show(supportFragmentManager, "password_dialog")
            }
        } else {
            showToast(error)
        }
    }

    private fun readInputs(): User {
        return User(
            name = binding.nameInput.text.toString(),
            username = binding.usernameInput.text.toString(),
            email = binding.emailInput.text.toString(),
            website = binding.websiteInput.text.toString(),
            bio = binding.bioInput.text.toString(),
            phone = binding.phoneInput.text.toString().toLongOrNull()
        )
    }

    private fun validate(user: User): String? =
        when{
            user.name.isEmpty() -> "Please enter name"
            user.username.isEmpty() -> "Please enter username"
            user.email.isEmpty() -> "Please enter email"
            else -> null
        }

}