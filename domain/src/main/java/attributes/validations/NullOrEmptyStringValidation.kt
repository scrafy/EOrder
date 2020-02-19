package attributes.validations

import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
annotation class NullOrEmptyStringValidation(val message: String)