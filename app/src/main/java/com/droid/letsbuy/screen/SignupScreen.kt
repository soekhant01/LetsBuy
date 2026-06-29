package com.droid.letsbuy.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.droid.letsbuy.R
import com.droid.letsbuy.utils.AppUtil
import com.droid.letsbuy.viewmodel.AuthViewModel

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello There!",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp, fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
            )
        )
        Spacer(Modifier.height(4.dp))


        Text(
            text = "Create An Account!",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp,
            )
        )



        Image(
            painter = painterResource(R.drawable.signup),
            contentDescription = "signup",
        )


        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Name")

            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))


        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")

            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))



        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")

            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(20.dp))


        Button(
            onClick = {
                if (name.isBlank() || password.isBlank() || password.isBlank()) {
                    AppUtil.showToast(context, "Please fill in all fields")
                    return@Button
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches() || email.any { it.isUpperCase() }
                ) {
                    AppUtil.showToast(
                        context,
                        "Please enter a valid email address"
                    )
                    return@Button
                }
                if (password.length < 6) {
                    AppUtil.showToast(
                        context,
                        "Password must be at least 6 characters"
                    )
                    return@Button
                }
                isLoading = true
                authViewModel.signup(
                    email,
                    name,
                    password
                ) { success, errorMessage ->
                    if (success) {
                        isLoading = false

                        navController.navigate("verify_email") {
                            popUpTo("auth") {
                                inclusive = true
                            }
                        }
                    } else {
                        isLoading = false

                        AppUtil.showToast(
                            context,
                            errorMessage ?: "Something Went Wrong"
                        )
                    }

                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp, color = Color.White
                )
            } else {
                Text(
                    text = "Signup",
                    fontSize = 22.sp
                )
            }
        }
    }

}