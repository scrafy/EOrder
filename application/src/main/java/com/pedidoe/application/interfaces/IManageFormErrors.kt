package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.ValidationError

interface IManageFormErrors {

    fun setValidationErrors(errors: List<ValidationError>?)

}