package interfaces

import models.entities.Entity
import models.ValidationError

interface IValidate<T: Entity> {

    fun validate(entity: T) :  List<ValidationError>
    fun isModelValid(entity: T) :  Boolean
}