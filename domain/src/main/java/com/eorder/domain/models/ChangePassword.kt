package com.eorder.domain.models

import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation
import com.eorder.domain.attributes.RegexFormatValidation

class ChangePassword
{
    @NullOrEmptyStringValidation("The old pasword field can not be empty or null", "oldPassword",1)
    @RegexFormatValidation("The password must containt at least one digit, one lower case, one special character and a lenght of 6 characters as minimun", "oldPassword","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]*.*)(?=.*\\W+.*)[0-9a-zA-Z\\W]{6,}\$",2)
    var oldPassword: String? = null

    @NullOrEmptyStringValidation("The new password field can not be empty or null", "newPassword",1)
    @RegexFormatValidation("The password must containt at least one digit, one lower case, one special character and a lenght of 6 characters as minimun", "newPassword","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]*.*)(?=.*\\W+.*)[0-9a-zA-Z\\W]{6,}\$",2)
    var newPassword: String? = null

    @NullOrEmptyStringValidation("The confirm password field can not be empty or null", "confirmPassword",1)
    @FieldEqualToOtherValidation("The confirm password has to match with the new password", "confirmPassword","newPassword", 2)
    var confirmPassword: String? = null

    constructor(oldPassword:String, newPassword:String, confirmPassword:String){

        this.oldPassword = oldPassword
        this.newPassword = newPassword
        this.confirmPassword = confirmPassword

    }
}
