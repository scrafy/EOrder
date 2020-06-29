package com.pedidoe.domain.models

import com.pedidoe.domain.attributes.FieldEqualToOtherValidation
import com.pedidoe.domain.attributes.NullOrEmptyStringValidation
import com.pedidoe.domain.attributes.RegexFormatValidation

class Account(

    @NullOrEmptyStringValidation("username_empty", "Username",1)
    val Username:String,
    @NullOrEmptyStringValidation("password_empty", "Password",1)
    @RegexFormatValidation("password_format", "Password","^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]+.*)(?=.*[A-Z]*.*)[0-9a-zA-Z\\W]{6,}\$",2)
    val Password:String,
    @NullOrEmptyStringValidation("password_confirmation_not_null","ConfirmPassword", 1)
    @FieldEqualToOtherValidation("password_confirmation_match","ConfirmPassword", "Password",2)
    val ConfirmPassword:String,
    @NullOrEmptyStringValidation("phone_not_null", "Phone",1)
    @RegexFormatValidation("phone_9_digits", "Phone","^[0-9]{9,}\$",1)
    val Phone:String,
    @NullOrEmptyStringValidation("email_not_null","Email", 1)
    @RegexFormatValidation("email_format","Email", "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$",2)
    val Email:String,
    @NullOrEmptyStringValidation("center_code_not_null", "CentreCode",1)
    val CentreCode:String
)