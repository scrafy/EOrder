package com.eorder.application.interfaces


import com.eorder.application.models.ValidationError

interface IValidationErrors {

    var validationErrors: List<ValidationError>
}