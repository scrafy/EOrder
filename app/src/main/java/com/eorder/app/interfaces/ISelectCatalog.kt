package com.eorder.app.interfaces

import com.eorder.domain.models.Catalog

interface ISelectCatalog {

    fun selectCatalog(catalog: Catalog)
}