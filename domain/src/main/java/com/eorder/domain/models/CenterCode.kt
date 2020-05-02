package com.eorder.domain.models

import com.eorder.domain.attributes.NullOrEmptyStringValidation

class CenterCode(

    @NullOrEmptyStringValidation("center_code_not_null", "centerCode",1)
    var centerCode: String

)