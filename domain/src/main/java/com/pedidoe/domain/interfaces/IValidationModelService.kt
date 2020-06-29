package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.ValidationError

interface IValidationModelService{

    fun validate(model: Any) :  List<ValidationError>
    fun isModelValid(model: Any) :  Boolean
}