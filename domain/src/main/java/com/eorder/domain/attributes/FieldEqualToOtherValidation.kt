package com.eorder.domain.attributes

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class FieldEqualToOtherValidation(val message: String, val name: String, val compareWith:String,val order: Int)