package com.eorder.application.usecases


import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.di.UnitOfWorkService
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.application.interfaces.ILoginUseCase
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.Login
import com.eorder.domain.models.ValidationError
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository
import com.eorder.infrastructure.repositories.UserRepository
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
class LoginUseCase(
    private val unitOfWorkService: UnitOfWorkService,
    private val unitOfWorkRepository: UnitOfWorkRepository
) : ILoginUseCase {


    override fun login(login: Login): ServerResponse<String> {

        var validationErrors: List<ValidationError> =
            unitOfWorkService.getValidationModelService().validate(login)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )

        var response = this.unitOfWorkRepository.getUserRepository().login(login)
        unitOfWorkService.getJwtTokenService().addToken(response.serverData?.data!!)
        return response
    }

}