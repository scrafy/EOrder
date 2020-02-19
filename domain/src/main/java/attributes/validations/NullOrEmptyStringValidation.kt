package attributes.validations

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class NullOrEmptyStringValidation(val message: String)