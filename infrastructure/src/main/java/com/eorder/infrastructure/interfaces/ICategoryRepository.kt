package com.eorder.infrastructure.interfaces

import com.eorder.domain.models.Category
import com.eorder.domain.models.ServerResponse

interface ICategoryRepository {

    fun getCategories(catalogId: Int): ServerResponse<List<Category>>

}
