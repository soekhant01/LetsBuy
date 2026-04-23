package com.droid.letsbuy.model

data class UserModel(
    val name: String,
    val email: String,
    val uid: String
    // don't store password, password is handled by authentication
)
