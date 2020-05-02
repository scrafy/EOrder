package com.eorder.domain.models

import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation
import com.eorder.domain.attributes.RegexFormatValidation

class Account(

    @NullOrEmptyStringValidation("username_empty", "username",1)
    val username:String,
    @NullOrEmptyStringValidation("password_empty", "password",1)
    @RegexFormatValidation("password_format", "password","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]*.*)(?=.*\\W+.*)[0-9a-zA-Z\\W]{6,}\$",2)
    val password:String,
    @NullOrEmptyStringValidation("password_confirmation_not_null","confirmPassword", 1)
    @FieldEqualToOtherValidation("password_confirmation_match","confirmPassword", "password",2)
    val confirmPassword:String,
    @NullOrEmptyStringValidation("phone_not_null", "phone",1)
    @RegexFormatValidation("phone_9_digits", "phone","^[0-9]{9,}\$",1)
    val phone:String,
    @NullOrEmptyStringValidation("email_not_null","email", 1)
    @RegexFormatValidation("email_format","email", "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$",2)
    val email:String,
    @NullOrEmptyStringValidation("center_code_not_null", "centerCode",1)
    val centerCode:String
)