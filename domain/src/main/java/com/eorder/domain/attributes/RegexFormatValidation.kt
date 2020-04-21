package com.eorder.domain.attributes

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class RegexFormatValidation(
    val message: String,
    val name: String,
    val pattern:String,
    val order: Int
)