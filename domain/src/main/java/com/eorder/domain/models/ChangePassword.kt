package com.eorder.domain.models

import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation
import com.eorder.domain.attributes.RegexFormatValidation

class ChangePassword
{
    @NullOrEmptyStringValidation("oldpassword_empty", "OldPassword",1)
    @RegexFormatValidation("oldpassword_format", "OldPassword","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]+.*)(?=.*[A-Z]*.*)[0-9a-zA-Z\\W]{6,}\$",2)
    var oldPassword: String? = null

    @NullOrEmptyStringValidation("new_password_empty", "NewPassword",1)
    @RegexFormatValidation("new_password_format", "NewPassword","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]+.*)(?=.*[A-Z]*.*)[0-9a-zA-Z\\W]{6,}\$",2)
    var newPassword: String? = null

    @NullOrEmptyStringValidation("password_confirmation_not_null", "ConfirmPassword",1)
    @FieldEqualToOtherValidation("password_confirmation_match", "ConfirmPassword","newPassword", 2)
    var confirmPassword: String? = null

    constructor(oldPassword:String, newPassword:String, confirmPassword:String){

        this.oldPassword = oldPassword
        this.newPassword = newPassword
        this.confirmPassword = confirmPassword

    }
}
