package usecases

import interfaces.ILoginUseCase
import models.InfraLoginRequest
import models.LoginRequest
import models.LoginResponse
import models.ValidationError
import models.entities.Establishment
import services.LoginService
import services.ValidationService


class LoguinUseCase : ILoginUseCase {


    private var loginService: LoginService
    private var validationService: ValidationService<LoginRequest>

    constructor(){
        this.loginService = LoginService()
        this.validationService = ValidationService()
    }

    override fun login(loginRequest: LoginRequest): LoginResponse<Establishment> {
        var validationErrors: List<ValidationError> = this.validationService.validate(loginRequest)

        if (validationErrors.isNotEmpty()){

            return LoginResponse(validationErrors, null)
        }
        return LoginResponse(validationErrors, this.loginService.loguin(InfraLoginRequest(loginRequest.username!!, loginRequest.password!!)))
    }

}