package services

import attributes.validations.NullOrEmptyStringValidation
import interfaces.IValidate
import models.InfraValidationError
import models.ValidationError
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

class ValidationService<T: Any> : IValidate<T> {

    override fun isModelValid(model: T): Boolean {
        return validate(model).isEmpty()
    }

    override fun validate(model: T): List<ValidationError> {
        var result: MutableList<ValidationError> = arrayListOf()

        model.javaClass.kotlin.memberProperties.filter { member -> member.visibility == KVisibility.PUBLIC }.forEach { member ->

            var value: Any? = member.get(model)
            member.annotations.forEach lit@{ annotation ->

                if ( annotation is NullOrEmptyStringValidation){

                    if ( (value as String).isNullOrEmpty()){
                        result.add(
                            ValidationError(
                                getMessageFromAnnotation(annotation),
                                value,
                                member.name,
                                model::class.simpleName ?: ""
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