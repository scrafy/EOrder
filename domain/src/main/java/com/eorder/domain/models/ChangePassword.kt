package com.eorder.domain.models

import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation
import com.eorder.domain.attributes.RegexFormatValidation

class ChangePassword
{
    @NullOrEmptyStringValidation("oldpassword_empty", "oldPassword",1)
    @RegexFormatValidation("oldpassword_format", "oldPassword","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]*.*)(?=.*\\W+.*)[0-9a-zA-Z\\W]{6,}\$",2)
    var oldPassword: String? = null

    @NullOrEmptyStringValidation("new_password_empty", "newPassword",1)
    @RegexFormatValidation("new_password_format", "newPassword","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]*.*)(?=.*\\W+.*)[0-9a-zA-Z\\W]{6,}\$",2)
    var newPassword: String? = null

    @NullOrEmptyStringValidation("password_confirmation_not_null", "confirmPassword",1)
    @FieldEqualToOtherValidation("password_confirmation_match", "confirmPassword","newPassword", 2)
    var confirmPassword: String? = null

    constructor(oldPassword:String, newPassword:String, confirmPassword:String){

        this.oldPassword = oldPassword
        this.newPassword = newPassword
        this.confirmPassword = confirmPassword

    }
}
