package com.eorder.domain.models

import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation

class RecoverPassword
{
    @NullOrEmptyStringValidation("The old pasword field can not be empty or null", 1)
    var oldPassword: String? = null

    @NullOrEmptyStringValidation("The new password field can not be empty or null", 1)
    var newPassword: String? = null

    @NullOrEmptyStringValidation("The confirm password field can not be empty or null", 1)
    @FieldEqualToOtherValidation("The confirm password has to match with the new password", "newPassword", 2)
    var confirmPassword: String? = null

    constructor(oldPassword:String, newPassword:String, confirmPassword:String){

        this.oldPassword = oldPassword
        this.newPassword = newPassword
        this.confirmPassword = confirmPassword

    }
}
