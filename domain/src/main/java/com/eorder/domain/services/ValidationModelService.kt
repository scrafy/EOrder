package com.eorder.domain.services

import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.ValidationError
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

class ValidationModelService : IValidationModelService {

    override fun isModelValid(model: Any): Boolean {
        return validate(model).isEmpty()
    }

    override fun validate(model: Any): List<ValidationError> {
        var result: MutableList<ValidationError> = mutableListOf()

        model.javaClass.kotlin.memberProperties.filter { member -> member.visibility == KVisibility.PUBLIC }
            .forEach { member ->

                val value: Any? = member.get(model)
                member.annotations.sortedBy { annotation -> (getValuesFromAnnotation(annotation)["executionOrder"])?.toInt() }
                    .forEach { annotation ->

                        if (annotation is NullOrEmptyStringValidation) {

                            if ((value as String).isNullOrEmpty()) {

                                result.add(
                                    ValidationError(
                                        getValuesFromAnnotation(annotation)["message"] ?: "",
                                        member.name,
                                        model::class.simpleName ?: "",
                                        value.toString()
                                    )
                                )

                            }

                        } else if (annotation is FieldEqualToOtherValidation) {

                            var anotationValues = getValuesFromAnnotation(annotation)
                            var memberToCompare =
                                model.javaClass.kotlin.memberProperties.filter { member ->

                                    member.visibility == KVisibility.PUBLIC && member.name.equals(
                                        anotationValues["fieldName"]
                                    )
                                }.first()

                            if (value != memberToCompare.get(model)) {

                                result.add(
                                    ValidationError(
                                        anotationValues["message"] ?: "",
                                        member.name,
                                        model::class.simpleName ?: "",
                                        value.toString()
                                    )
                                )
                            }
                        }
                    }
            }
        return result
    }

    private fun getValuesFromAnnotation(annotation: Annotation): Map<String, String> {

        val result: MutableMap<String, String> = mutableMapOf()
        annotation.toString().split("(")[1].substring(
            0,
            annotation.toString().split("(")[1].length - 1
        ).split(",").forEach { it ->

            result[it.split("=")[0].trim()] = it.split("=")[1].trim()
        }

        return result
    }
}