package com.eorder.domain.models

data class DataProductFragment(

    val categories: List<Category>,
    val categorySelected: Category,
    val catalogId: Int,
    var centerId:Int
)