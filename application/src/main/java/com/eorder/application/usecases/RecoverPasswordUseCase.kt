package com.eorder.application.usecases


import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ValidationError
import com.eorder.domain.models.ServerResponse


@RequiresApi(Build.VERSION_CODES.O)
class RecoverPasswordUseCase(
    private val validationModelService: IValidationModelService,
    private val userRepository: IUserRepository
) : IRecoverPasswordUseCase {


    override fun recoverPassword(recoverPassword: RecoverPassword): ServerResponse<String> {


        var validationErrors: List<ValidationError> =
            validationModelService.validate(recoverPassword)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )


        return userRepository.recoverPassword(recoverPassword)
    }
}