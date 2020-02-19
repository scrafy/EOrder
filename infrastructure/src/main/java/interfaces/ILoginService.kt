package interfaces

import models.InfraLoginRequest
import models.ServerResponse
import models.entities.Establishment


interface ILoginService {

    fun loguin(loguinRequestInfra: InfraLoginRequest) : ServerResponse<Establishment>
}