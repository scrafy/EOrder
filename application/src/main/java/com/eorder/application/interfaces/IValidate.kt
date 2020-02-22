package interfaces

import models.ValidationError

interface IValidate<T>{

    fun validate(model: T) :  List<ValidationError>
    fun isModelValid(model: T) :  Boolean
}