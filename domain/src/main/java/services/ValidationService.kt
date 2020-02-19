package services

import attributes.validations.NullOrEmptyStringValidation
import interfaces.IValidate
import models.entities.Entity
import models.ValidationError
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

class ValidationService<T: Entity> : IValidate<T>{

    override fun isModelValid(entity: T): Boolean {
        return validate(entity).isEmpty()
    }

    override fun validate(entity: T): List<ValidationError> {
        var result: MutableList<ValidationError> = arrayListOf()

        entity.javaClass.kotlin.memberProperties.filter { member -> member.visibility == KVisibility.PUBLIC }.forEach { member ->

            var value: Any? = member.get(entity)
            member.annotations.forEach lit@{ annotation ->

                if ( annotation is NullOrEmptyStringValidation ){

                    if ( (value as String).isNullOrEmpty()){
                        result.add(
                            ValidationError(
                                getMessageFromAnnotation(annotation),
                                value,
                                member.name,
                                entity::class.simpleName ?: ""
                            )
                        )
                        return@lit
                    }
                }
            }
        }
        return result
    }

    private fun getMessageFromAnnotation(annotation: Any) : String{

        var first: Int = annotation.toString().indexOf('#', 0,true)
        var last: Int = annotation.toString().indexOf('#', first + 1 , true)
        return annotation.toString().substring(first + 1, last)
    }
}