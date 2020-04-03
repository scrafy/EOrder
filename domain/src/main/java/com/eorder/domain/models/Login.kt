package com.eorder.domain.models

import com.eorder.domain.attributes.NullOrEmptyStringValidation

class Login{

    @NullOrEmptyStringValidation("The username field can not be empty or null", 1)
    var username: String? = null

    @NullOrEmptyStringValidation("The password field can not be empty or null", 1)
    var password: String? = null

    constructor(username:String, password:String){

        this.username = username
        this.password = password
    }
}