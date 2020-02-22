package interfaces

import models.LoginRequest
import models.LoginResponse
import models.entities.Establishment


interface ILoginUseCase {

    fun login(loginRequest: LoginRequest) : LoginResponse<Establishment>
}