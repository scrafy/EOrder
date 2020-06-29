package com.pedidoe.domain.models

import com.pedidoe.domain.attributes.NullOrEmptyStringValidation

class CenterCode(

    @NullOrEmptyStringValidation("center_code_not_null", "centerCode",1)
    var centerCode: String

)