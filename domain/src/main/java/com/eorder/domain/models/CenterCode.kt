package com.eorder.domain.models

import com.eorder.domain.attributes.NullOrEmptyStringValidation

class CenterCode(

    @NullOrEmptyStringValidation("The center code can not be empty or null", "centerCode",1)
    var centerCode: String

)