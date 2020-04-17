package com.eorder.application.interfaces

import com.eorder.domain.models.ValidationError

interface IManageFormErrors {

    fun setValidationErrors(errors: List<ValidationError>?)

}