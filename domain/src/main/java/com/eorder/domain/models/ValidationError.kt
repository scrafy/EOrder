package com.eorder.domain.models

class ValidationError (

    var errorMessage: String,
    var fieldName: String,
    var modelName: String? = null,
    var value: String
)
