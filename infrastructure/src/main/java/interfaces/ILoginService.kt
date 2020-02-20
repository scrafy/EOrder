package interfaces

import models.infrastructure.LoginRequest
import models.infrastructure.ServerResponse
import models.entities.Establishment


interface ILoginService {

    fun loguin(loguinRequestInfra: LoginRequest) : ServerResponse<Establishment>
}