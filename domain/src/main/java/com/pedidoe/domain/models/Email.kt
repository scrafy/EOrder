package com.pedidoe.domain.models

import com.pedidoe.domain.attributes.RegexFormatValidation
import com.pedidoe.domain.attributes.NullOrEmptyStringValidation

class Email(

    @NullOrEmptyStringValidation("email_not_null","email", 1)
    @RegexFormatValidation("email_format","email", "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$",2)
    var email:String

)