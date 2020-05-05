package com.eorder.application.usecases

import com.eorder.application.interfaces.ICategoriesUseCase
import com.eorder.domain.models.Category
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.ICategoryRepository

class CategoriesUseCase(
    private val categoryRepository: ICategoryRepository
) : ICategoriesUseCase {

    override fun getCategories(catalogId: Int): ServerResponse<List<Category>> {

        return categoryRepository.getCategories(catalogId)
    }
}