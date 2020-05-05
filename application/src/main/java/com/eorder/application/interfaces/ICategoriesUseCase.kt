package com.eorder.application.interfaces

import com.eorder.domain.models.Category
import com.eorder.domain.models.ServerResponse

interface ICategoriesUseCase {

    fun getCategories(catalogId: Int): ServerResponse<List<Category>>
}
