package com.droid.letsbuy.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droid.letsbuy.AppUtil
import com.droid.letsbuy.GlobalNavigation
import com.droid.letsbuy.R
import com.droid.letsbuy.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {

    val userModel = remember {
        mutableStateOf(UserModel())
    }

    var addressInput by remember {
        mutableStateOf(userModel.value.address)
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result
                        addressInput = userModel.value.address
                    }
                }
            }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        ) {
        Text(
            "Your Profile", style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        )
        Spacer(Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.unicorn),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))


        Text(
            userModel.value.name, style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Text("Address", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)



        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = addressInput,
                onValueChange = { addressInput = it },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    saveAddress(addressInput, context)
                })
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilledIconButton(onClick = {
                saveAddress(addressInput, context)
            }) {
                Icon(Icons.Default.Check, contentDescription = "Correct")
            }

        }


        Spacer(Modifier.height(16.dp))

        Text("Email", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        Text(userModel.value.email)

        Spacer(Modifier.height(16.dp))

        Text(
            "Number Of Items In Cart: ${userModel.value.cartItems.values.sum()}",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "View My Orders",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    GlobalNavigation.navController.navigate("orders")
                }
                .padding(16.dp)
        )

        Button(onClick = {
            FirebaseAuth.getInstance().signOut()
            val navController = GlobalNavigation.navController
            navController.popBackStack()
            navController.navigate("auth")
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Sign Out", fontSize = 18.sp)
        }


    }

}

fun saveAddress(addressInput: String, context: android.content.Context) {
    if (addressInput.isNotEmpty()) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .update("address", addressInput)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    AppUtil.showToast(
                        context,
                        "Address Updated Successfully"
                    )
                }
            }

    } else {
        AppUtil.showToast(context, "Address can't be empty")

    }
}

