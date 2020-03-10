package com.eorder.application.models

import com.eorder.infrastructure.models.Catalog
import com.eorder.infrastructure.models.ServerResponse

class GetCatalogsByCentreResponse(val data:ServerResponse<List<Catalog>>) {
}