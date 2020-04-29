package com.eorder.domain.models

import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation
import com.eorder.domain.attributes.RegexFormatValidation

class Account(

    @NullOrEmptyStringValidation("The username can not be empty or null", "username",1)
    val username:String,
    @NullOrEmptyStringValidation("The password can not be empty or null", "password",1)
    @RegexFormatValidation("The password must containt at least one digit, one lower case, one special character and a lenght of 6 characters as minimun", "password","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]*.*)(?=.*\\W+.*)[0-9a-zA-Z\\W]{6,}\$",2)
    val password:String,
    @NullOrEmptyStringValidation("The password confirmation can not be empty or null","confirmPassword", 1)
    @FieldEqualToOtherValidation("The password confirmation field has to match with password field","confirmPassword", "password",2)
    val confirmPassword:String,
    @NullOrEmptyStringValidation("The phone can not be empty or null", "phone",1)
    @RegexFormatValidation("The phone has to be 9 or more numeric digits", "phone","^[0-9]{9,}\$",1)
    val phone:String,
    @NullOrEmptyStringValidation("The email can not be empty or null","email", 1)
    @RegexFormatValidation("The email has not a correct format","email", "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$",2)
    val email:String,
    @NullOrEmptyStringValidation("The center code can not be empty or null", "centerCode",1)
    val centerCode:String
)