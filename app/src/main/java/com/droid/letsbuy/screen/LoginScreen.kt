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
import com.droid.letsbuy.AppUtil
import com.droid.letsbuy.R
import com.droid.letsbuy.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier, authViewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
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
            text = "Welcome Back!",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp, fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
            )
        )
        Spacer(Modifier.height(10.dp))


        Text(
            text = "Please Login!",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp,
            )
        )
        Spacer(Modifier.height(20.dp))


        Image(
            painter = painterResource(R.drawable.login),
            contentDescription = "signup"
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
                isLoading = true
                authViewModel.login(email, password) { success, errorMessage ->
                    if (success) {
                        isLoading = false
                        AppUtil.showToast(context, "Login Successfully!")
                        navController.navigate("home") {
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
                    text = "Login",
                    fontSize = 22.sp
                )
            }
        }
    }
}