package models.entities

import attributes.validations.NullOrEmptyStringValidation


class User : Entity {

    @NullOrEmptyStringValidation("#The field username can not be null or empty string#")
    var username: String = ""


    @NullOrEmptyStringValidation("#The field password can not be null or empty string#")
    var password: String = ""


    constructor(username: String, password: String)
    {
        this.username = username
        this.password = password
    }
}