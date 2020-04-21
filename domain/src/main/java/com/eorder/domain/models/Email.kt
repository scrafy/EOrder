package com.eorder.domain.models

import com.eorder.domain.attributes.RegexFormatValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation

class Email(

    @NullOrEmptyStringValidation("The email can not be empty or null","email", 1)
    @RegexFormatValidation("The email has not a correct format","email", "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$",2)
    var email:String

)