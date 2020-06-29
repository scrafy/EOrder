package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.ICreateAccountUseCase
import com.pedidoe.domain.enumerations.ErrorCode
import com.pedidoe.domain.exceptions.ModelValidationException
import com.pedidoe.domain.interfaces.IUserRepository
import com.pedidoe.domain.interfaces.IValidationModelService
import com.pedidoe.domain.models.Account
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.domain.models.UserProfile

class CreateAccountUseCase(
    private val validationModelService: IValidationModelService,
    private val userRepository: IUserRepository

) : ICreateAccountUseCase {

    override fun createAccount(account: Account): ServerResponse<UserProfile> {

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