package com.droid.letsbuy.model

data class UserModel(
    val name: String = "",
    val email: String = "",
    val uid: String = "",
    val cartItems: Map<String, Long> = emptyMap(),
    val address: String = ""
    // don't store password, password is handled by authentication
)
