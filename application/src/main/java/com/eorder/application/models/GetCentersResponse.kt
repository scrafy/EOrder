package com.eorder.application.models

import com.eorder.infrastructure.models.Center
import com.eorder.infrastructure.models.ServerResponse

class GetCentersResponse(val data: ServerResponse<List<Center>>)

