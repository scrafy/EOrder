package com.eorder.application.usecases

import com.eorder.application.interfaces.ICreateAccountUseCase
import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ModelValidationException
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.interfaces.IValidationModelService
import com.eorder.domain.models.Account
import com.eorder.domain.models.ServerResponse

class CreateAccountUseCase(
    private val validationModelService: IValidationModelService,
    private val userRepository: IUserRepository

) : ICreateAccountUseCase {

    override fun createAccount(account: Account): ServerResponse<Any> {

        val errors = validationModelService.validate(account)

        if (errors.isNotEmpty()) {

            throw ModelValidationException(
                ErrorCode.VALIDATION_ERROR,
                "Exists validation errors",
                errors
            )
        }

        return userRepository.createAccount(account)
    }
}