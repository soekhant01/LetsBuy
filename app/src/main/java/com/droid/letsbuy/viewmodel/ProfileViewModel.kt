package com.droid.letsbuy.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.droid.letsbuy.model.UserModel
import com.droid.letsbuy.utils.AppUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class ProfileUiState(
    val isLoading: Boolean = true,
    val user: UserModel = UserModel()
)

class ProfileViewModel : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()


    fun loadUser() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        Firebase.firestore.collection("users").document(uid)
            .addSnapshotListener { snapshot, _ ->
                val user = snapshot?.toObject(UserModel::class.java)
                if (user != null) {
                    _profileUiState.value = ProfileUiState(
                        isLoading = false,
                        user = user
                    )
                }
            }
    }

    fun updateAddress(address: String, context: Context) {

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        if (address.isBlank()) {
            AppUtil.showToast(context, "Address can't be empty")
            return
        }

        Firebase.firestore.collection("users")
            .document(uid)
            .update("address", address)
            .addOnSuccessListener {
                AppUtil.showToast(context, "Address Updated Successfully")
            }
            .addOnFailureListener { e ->
                AppUtil.showToast(context, "Failed: ${e.message}")
            }
    }

    fun deleteAccount(
        onReAuthRequired: () -> Unit,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,

        ) {
        val user =
            FirebaseAuth.getInstance().currentUser
                ?: return onError("No User Logged In")

        user.delete().addOnSuccessListener {
            Firebase.firestore.collection("users").document(user.uid).delete()
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Failed to delete user data")
                }

        }
            .addOnFailureListener { e ->
                if (e is FirebaseAuthRecentLoginRequiredException) {
                    onReAuthRequired()
                } else {
                    onError(e.message ?: "Failed to delete account")
                }
            }


    }

    fun reAuthAndDeleteAccount(
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        val user =
            FirebaseAuth.getInstance().currentUser
                ?: return onError("No User Logged In")
        val userCredential =
            EmailAuthProvider.getCredential(user.email!!, password)

        user.reauthenticate(userCredential).addOnSuccessListener {
            Firebase.firestore.collection("users").document(user.uid).delete()
                .addOnSuccessListener {
                    user.delete()
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e ->
                            onError(e.message ?: "Failed to delete account")
                        }
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Failed to delete user data")
                }
        }
            .addOnFailureListener {
                onError("Wrong password. Please try again.")
            }
    }
}
