package com.eorder.domain.attributes

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class NullOrEmptyStringValidation(val message: String, val name:String, val order: Int)