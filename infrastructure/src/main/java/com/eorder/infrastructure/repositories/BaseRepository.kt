package com.eorder.infrastructure.repositories

import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.ServerErrorException
import com.eorder.domain.exceptions.ServerErrorUnhadledException
import com.eorder.domain.exceptions.ServerErrorValidationException
import com.eorder.domain.models.ServerResponse

abstract class BaseRepository {

    protected fun<T> checkServerErrorInResponse(response: ServerResponse<T>){

        when( response.statusCode ){

            400 -> {
                if (response.serverError?.validationErrors != null)
                    throw ServerErrorValidationException(
                        response.serverError?.errorCode,
                        ErrorCode.SERVER_VALIDATION_ERROR,
                        response.serverError?.errorMessage ?: "",
                        response.serverError?.validationErrors
                    )

                throw ServerErrorException(
                    response.serverError?.errorCode,
                    ErrorCode.SERVER_ERROR,
                    response.serverError?.errorMessage ?: ""
                )
            }
            500 -> throw ServerErrorUnhadledException(
                response.serverError?.errorCode,
                ErrorCode.SERVER_ERROR,
                response.serverError?.errorMessage ?: "",
                response.serverError?.stackTrace
            )

        }
    }
}