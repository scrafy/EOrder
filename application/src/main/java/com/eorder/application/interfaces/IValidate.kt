package com.eorder.application.interfaces

import com.eorder.application.models.ValidationError

interface IValidate<T>{

    fun validate(model: T) :  List<ValidationError>
    fun isModelValid(model: T) :  Boolean
}