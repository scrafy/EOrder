package com.eorder.domain.models

class ValidationError (

    var errorMessage: String,
    var fieldName: String,
    var modelName: String,
    var value: String
)
