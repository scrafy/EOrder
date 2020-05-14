package com.eorder.application.usecases


import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.Login
import com.eorder.domain.models.ValidationError
import com.eorder.domain.models.ServerResponse


@RequiresApi(Build.VERSION_CODES.O)
class LoginUseCase(
    private val jwtTokenService: IJwtTokenService,
    private val userRepository: IUserRepository,
    private val validationModelService: IValidationModelService
) : ILoginUseCase {


    override fun login(login: Login): ServerResponse<String> {

        var validationErrors: List<ValidationError> =
            validationModelService.validate(login)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )

        var response = userRepository.login(login)
        jwtTokenService.addToken(response.ServerData?.Data!!)
        return response
    }

}