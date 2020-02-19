package models

import attributes.validations.NullOrEmptyStringValidation

class LoginRequest {

    @NullOrEmptyStringValidation("#The username field can not be empty or null#")
    var username: String? = null

    @NullOrEmptyStringValidation("#The password field can not be empty or null#")
    var password: String? = null

    constructor(username:String, password:String){

        this.username = username
        this.password = password
    }
}