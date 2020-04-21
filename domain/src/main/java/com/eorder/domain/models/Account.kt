package com.eorder.domain.models

import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation
import com.eorder.domain.attributes.RegexFormatValidation

class Account(

    @NullOrEmptyStringValidation("The username can not be empty or null", "username",1)
    val username:String,
    @NullOrEmptyStringValidation("The password can not be empty or null", "password",1)
    val password:String,
    @NullOrEmptyStringValidation("The password confirmation can not be empty or null","confirmPassword", 1)
    @FieldEqualToOtherValidation("The password confirmation field has to match with password field","confirmPassword", "password",2)
    val confirmPassword:String,
    @NullOrEmptyStringValidation("The phone can not be empty or null", "phone",1)
    @RegexFormatValidation("The phone has not the correct format", "phone","^[+]{1}[0-9]{2}+-[0-9]{3}-[0-9]{3}-[0-9]{3}\$",1)
    val phone:String,
    val email:String,
    val centerCode:String
)