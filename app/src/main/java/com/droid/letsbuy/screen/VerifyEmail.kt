package com.droid.letsbuy.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.letsbuy.utils.AppUtil
import com.google.firebase.auth.FirebaseAuth

@Composable
fun VerifyEmailScreen(
    navController: NavController,
) {

    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Verify Your Email",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "We have sent a verification email to:",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user?.email ?: "",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                user?.reload()?.addOnCompleteListener {
                    if (user.isEmailVerified) {
                        AppUtil.showToast(context, "Email verified!")

                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    } else {
                        AppUtil.showToast(context, "Still not verified")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("I Have Verified")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                user?.sendEmailVerification()?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        AppUtil.showToast(
                            context,
                            "Verification email sent again"
                        )
                    } else {
                        AppUtil.showToast(context, "Failed to resend email")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Resend Email")
        }
    }
}