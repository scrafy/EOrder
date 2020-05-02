package com.eorder.domain.models

import com.eorder.domain.attributes.RegexFormatValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation

class Email(

    @NullOrEmptyStringValidation("email_not_null","email", 1)
    @RegexFormatValidation("email_format","email", "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$",2)
    var email:String

)