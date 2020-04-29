package com.eorder.application.usecases


import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.interfaces.IChangePasswordUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.ChangePassword
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError


@RequiresApi(Build.VERSION_CODES.O)
class ChangePasswordUseCase(
    private val validationModelService: IValidationModelService,
    private val userRepository: IUserRepository
) : IChangePasswordUseCase {


    override fun changePassword(recoverPassword: ChangePassword): ServerResponse<Any> {


        var validationErrors: List<ValidationError> =
            validationModelService.validate(recoverPassword)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )


        return userRepository.changePassword(recoverPassword)
    }
}