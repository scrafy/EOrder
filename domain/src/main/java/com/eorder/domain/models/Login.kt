package com.eorder.domain.models

import com.eorder.domain.attributes.NullOrEmptyStringValidation

class Login {

    @NullOrEmptyStringValidation("username_empty", "username", 1)
    var username: String? = null

    @NullOrEmptyStringValidation("password_empty", "password", 1)
    var password: String? = null

    constructor(username: String, password: String) {

        this.username = username
        this.password = password
    }
}