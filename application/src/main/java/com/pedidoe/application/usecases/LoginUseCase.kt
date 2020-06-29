package com.pedidoe.application.usecases


import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.exceptions.ModelValidationException
import com.pedidoe.domain.interfaces.IJwtTokenService
import com.pedidoe.application.interfaces.ILoginUseCase
import com.pedidoe.domain.interfaces.IUserRepository
import com.pedidoe.domain.interfaces.IValidationModelService
import com.pedidoe.domain.models.Login
import com.pedidoe.domain.models.ValidationError
import com.pedidoe.domain.models.ServerResponse


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