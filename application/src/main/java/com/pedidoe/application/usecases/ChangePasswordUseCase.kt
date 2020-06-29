package com.pedidoe.application.usecases


import android.os.Build
import androidx.annotation.RequiresApi
import com.pedidoe.application.interfaces.IChangePasswordUseCase
import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.exceptions.ModelValidationException
import com.pedidoe.domain.interfaces.IUserRepository
import com.pedidoe.domain.interfaces.IValidationModelService
import com.pedidoe.domain.models.ChangePassword
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.ValidationError


@RequiresApi(Build.VERSION_CODES.O)
class ChangePasswordUseCase(
    private val validationModelService: IValidationModelService,
    private val userRepository: IUserRepository
) : IChangePasswordUseCase {


    override fun changePassword(changePassword: ChangePassword): ServerResponse<Any> {


        var validationErrors: List<ValidationError> =
            validationModelService.validate(changePassword)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )


        return userRepository.changePassword(changePassword)
    }
}