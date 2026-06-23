package com.droid.letsbuy.pages

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droid.letsbuy.GlobalNavigation
import com.droid.letsbuy.R
import com.droid.letsbuy.application.prefs
import com.droid.letsbuy.components.ProfileShimmer
import com.droid.letsbuy.viewmodel.ProfileUiState
import com.droid.letsbuy.viewmodel.ProfileViewModel
import com.droid.letsbuy.viewmodel.ThemeViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val switchState by themeViewModel.isDarkThemeEnabled.collectAsState()
    val profileUiState by profileViewModel.profileUiState.collectAsState()
    var addressInput by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        profileViewModel.loadUser()
    }

    LaunchedEffect(profileUiState.user.address) {
        addressInput = profileUiState.user.address
    }



    when {
        profileUiState.isLoading -> ProfileShimmer()
        else -> ProfileContent(
            modifier = modifier,
            profileViewModel = profileViewModel,
            addressInput = addressInput,
            switchState = switchState,
            themeViewModel = themeViewModel,
            profileUiState = profileUiState,
            context = context
        )
    }


}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    profileUiState: ProfileUiState,
    profileViewModel: ProfileViewModel,
    addressInput: String,
    switchState: Boolean,
    themeViewModel: ThemeViewModel,
    context: Context
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // — Avatar + Name —
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(
                        0.5.dp,
                        MaterialTheme.colorScheme.outlineVariant,
                        CircleShape
                    )
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.unicorn),
                    contentDescription = "Profile picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                profileUiState.user.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                profileUiState.user.email,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(28.dp))

        // — Section: Account —
        SectionLabel("Account")
        Spacer(Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                0.5.dp,
                MaterialTheme.colorScheme.outlineVariant
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(Modifier.padding(horizontal = 16.dp)) {

                // Address field
                FieldRow(
                    icon = Icons.Default.LocationOn,
                    label = "Delivery address"
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BasicTextField(
                            value = addressInput,
                            onValueChange = {
                                profileViewModel.updateAddress(it, context)
                            },
                            modifier = Modifier.weight(1f),
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                profileViewModel.updateAddress(
                                    addressInput,
                                    context
                                )
                            })
                        )
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                profileViewModel.updateAddress(
                                    addressInput,
                                    context
                                )
                            },
                            modifier = Modifier
                                .size(28.dp)
                                .border(
                                    0.5.dp,
                                    MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(6.dp)
                                )
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Save address",
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                // Email
                FieldRow(icon = Icons.Default.Mail, label = "Email") {
                    Text(profileUiState.user.email, fontSize = 14.sp)
                }

                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                // Cart
                FieldRow(
                    icon = Icons.Default.ShoppingCart,
                    label = "Items in cart"
                ) {
                    Text(
                        "${profileUiState.user.cartItems.values.sum()} items",
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // — Section: Preferences —
        SectionLabel("Preferences")
        Spacer(Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                0.5.dp,
                MaterialTheme.colorScheme.outlineVariant
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(Modifier.padding(horizontal = 16.dp)) {

                // My Orders
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { GlobalNavigation.navController.navigate("orders") }
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            Icons.Default.Receipt,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text("My orders", fontSize = 14.sp)
                    }
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                // Dark mode toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            if (switchState) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text("Dark mode", fontSize = 14.sp)
                    }
                    Switch(
                        checked = switchState,
                        onCheckedChange = {
                            themeViewModel.setTheme(it)
                            prefs.themeDark = it
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // — Sign out —
        OutlinedButton(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                GlobalNavigation.navController.popBackStack()
                GlobalNavigation.navController.navigate("auth")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.Logout,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text("Sign out", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }

}

// Helpers

@Composable
private fun SectionLabel(text: String) {
    Text(
        text.uppercase(),
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.08.em,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun FieldRow(
    icon: ImageVector,
    label: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .border(
                    0.5.dp,
                    MaterialTheme.colorScheme.outlineVariant,
                    RoundedCornerShape(6.dp)
                )
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(6.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                label,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(2.dp))
            content()
        }
    }
}



