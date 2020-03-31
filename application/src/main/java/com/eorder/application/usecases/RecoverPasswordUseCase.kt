package com.eorder.application.usecases


import android.os.Build
import androidx.annotation.RequiresApi
import com.eorder.application.di.UnitOfWorkService
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.application.interfaces.IRecoverPasswordUseCase
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ValidationError
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.di.UnitOfWorkRepository


@RequiresApi(Build.VERSION_CODES.O)
class RecoverPasswordUseCase(
    private val unitOfWorkService: UnitOfWorkService,
    private val unitOfWorkRepository: UnitOfWorkRepository
) : IRecoverPasswordUseCase {


    override fun recoverPassword(recoverPassword: RecoverPassword): ServerResponse<String> {

        var validationErrors: List<ValidationError> =
            unitOfWorkService.getValidationModelService().validate(recoverPassword)

        if (validationErrors.isNotEmpty())
            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                validationErrors
            )


        return unitOfWorkRepository.getUserRepository().recoverPassword(recoverPassword)
    }
}