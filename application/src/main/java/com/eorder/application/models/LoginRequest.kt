package com.eorder.application.models

import com.eorder.domain.attributes.NullOrEmptyStringValidation

class LoginRequest {

    @NullOrEmptyStringValidation("The username field can not be empty or null", 1)
    var username: String = ""

    @NullOrEmptyStringValidation("The password field can not be empty or null", 1)
    var password: String = ""

    constructor(username:String, password:String){

        this.username = username
        this.password = password
    }
}