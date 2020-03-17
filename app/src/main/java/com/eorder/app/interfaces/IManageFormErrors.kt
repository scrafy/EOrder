package com.eorder.app.interfaces

import com.eorder.domain.models.ValidationError

interface IManageFormErrors {

    fun setValidationErrors(errors: List<ValidationError>?)
    fun clearEditTextAndFocus()
}