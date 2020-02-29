package com.eorder.domain.interfaces

import com.eorder.domain.models.ValidationError

interface IValidationModelService{

    fun validate(model: Any) :  List<ValidationError>
    fun isModelValid(model: Any) :  Boolean
}