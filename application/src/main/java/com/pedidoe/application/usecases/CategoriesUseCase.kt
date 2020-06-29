package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.ICategoriesUseCase
import com.pedidoe.domain.models.Category
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.infrastructure.interfaces.ICategoryRepository

class CategoriesUseCase(
    private val categoryRepository: ICategoryRepository
) : ICategoriesUseCase {

    override fun getCategories(catalogId: Int): ServerResponse<List<Category>> {

        return categoryRepository.getCategories(catalogId)
    }
}