package com.eorder.domain.models

class ValidationError (

    var fieldName: String,
    var modelName: String,
    var errorMessage: String,
    var value: String
)
