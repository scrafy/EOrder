package com.pedidoe.infrastructure.interfaces

import com.pedidoe.domain.models.Category
import com.pedidoe.domain.models.ServerResponse

interface ICategoryRepository {

    fun getCategories(catalogId: Int, centreId: Int): ServerResponse<List<Category>>

}
