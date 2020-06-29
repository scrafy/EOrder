package com.pedidoe.domain.models

import com.pedidoe.domain.attributes.NullOrEmptyStringValidation

class Login {

    @NullOrEmptyStringValidation("username_empty", "Username", 1)
    var username: String? = null

    @NullOrEmptyStringValidation("password_empty", "Password", 1)
    var password: String? = null

    constructor(username: String, password: String) {

        this.username = username
        this.password = password
    }
}