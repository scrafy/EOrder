package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.Category
import com.pedidoe.domain.models.ServerResponse

interface ICategoriesUseCase {

    fun getCategories(catalogId: Int, centreId: Int): ServerResponse<List<Category>>
}
