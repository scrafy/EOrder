package com.eorder.domain.services

import com.eorder.domain.attributes.RegexFormatValidation
import com.eorder.domain.attributes.FieldEqualToOtherValidation
import com.eorder.domain.attributes.NullOrEmptyStringValidation
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.ValidationError
import org.koin.android.ext.koin.androidApplication
import org.koin.core.KoinComponent
import java.util.regex.Pattern
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

class ValidationModelService : KoinComponent, IValidationModelService {

    private val context = this.getKoin().rootScope.androidApplication().applicationContext

    override fun isModelValid(model: Any): Boolean {
        return validate(model).isEmpty()
    }

    override fun validate(model: Any): List<ValidationError> {
        var result: MutableList<ValidationError> = mutableListOf()

        model.javaClass.kotlin.memberProperties.filter { member -> member.visibility == KVisibility.PUBLIC }
            .forEach { member ->

                val value: Any? = member.get(model)
                member.annotations.sortedBy { annotation -> annotation.javaClass.kotlin.memberProperties.find { it.name == "order" }?.get(annotation) as Int }
                    .forEach { annotation ->

                        if (annotation is NullOrEmptyStringValidation) {

                            if ((value as String).isNullOrEmpty()) {

                                result.add(
                                    ValidationError(
                                        context.resources.getString(context.resources.getIdentifier(annotation.message, "string", context.packageName)),
                                        annotation.name

                                    )
                                )

                            }

                        } else if (annotation is FieldEqualToOtherValidation) {

                            var memberToCompare =
                                model.javaClass.kotlin.memberProperties.filter { member ->

                                    member.visibility == KVisibility.PUBLIC && member.name.equals(
                                        annotation.compareWith
                                    )
                                }.first()

                            if (value != memberToCompare.get(model)) {

                                result.add(
                                    ValidationError(
                                        context.resources.getString(context.resources.getIdentifier(annotation.message, "string", context.packageName)),
                                        annotation.name
                                    )
                                )
                            }
                        } else if (annotation is RegexFormatValidation) {

                            if (!Pattern.compile(annotation.pattern).matcher(value.toString()).matches()) {

                                result.add(
                                    ValidationError(
                                        context.resources.getString(context.resources.getIdentifier(annotation.message, "string", context.packageName)),
                                        annotation.name

                                    )
                                )
                            }

                        }
                    }
            }
        return result
    }

}