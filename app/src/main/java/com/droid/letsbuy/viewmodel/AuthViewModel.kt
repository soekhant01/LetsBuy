package com.droid.letsbuy.viewmodel

import androidx.lifecycle.ViewModel
import com.droid.letsbuy.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val fireStore = Firebase.firestore

    fun signup(
        email: String,
        name: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { verifyTask ->
                            if (verifyTask.isSuccessful) {
                                val userId = it.result.user?.uid
                                val userModel = UserModel(name, email, userId!!)
                                fireStore.collection("users").document(userId)
                                    .set(userModel)
                                    .addOnCompleteListener { dbTask ->
                                        if (dbTask.isSuccessful) {
                                            onResult(
                                                true,
                                                "Verification email sent. Please check your email."
                                            )

                                        } else {
                                            user.delete()
                                            onResult(
                                                false, "Something Went Wrong"
                                            )

                                        }

                                    }

                            } else {
                                user.delete()
                                onResult(
                                    false, "Failed to send verification email"
                                )
                            }
                        }

                } else {
                    onResult(false, it.exception?.localizedMessage)
                }
            }
    }


    fun login(
        email: String, password: String, onResult: (Boolean, String?) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                user?.reload()?.addOnCompleteListener {
                    if (user.isEmailVerified) onResult(true, null)
                    else {
                        auth.signOut()
                        onResult(false, "Please verify your email first")
                    }


                }

            } else {
                onResult(false, it.exception?.localizedMessage)

            }
        }
    }

}