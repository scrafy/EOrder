package com.eorder.domain.attributes

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class FieldEqualToOtherValidation(val message: String, val fieldName: String, val executionOrder: Int)