package com.eorder.application.models

import attributes.validations.NullOrEmptyStringValidation

class LoginRequest {

    @NullOrEmptyStringValidation("#The username field can not be empty or null#")
    var username: String = ""

    @NullOrEmptyStringValidation("#The password field can not be empty or null#")
    var password: String = ""

    constructor(username:String, password:String){

        this.username = username
        this.password = password
    }
}