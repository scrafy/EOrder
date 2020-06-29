package com.pedidoe.app.interfaces

import com.pedidoe.domain.models.Catalog

interface ISelectCatalog {

    fun selectCatalog(catalog: Catalog)
}