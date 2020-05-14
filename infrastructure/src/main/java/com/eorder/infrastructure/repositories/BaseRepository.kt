package com.eorder.infrastructure.repositories

import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.*
import com.eorder.domain.models.ServerResponse
import com.eorder.domain.models.ValidationError

abstract class BaseRepository {

    protected fun <T> checkServerErrorInResponse(response: ServerResponse<T>) {

        when (response.StatusCode) {

            400 -> {
                if (response.ServerError?.ValidationErrors != null)
                    throw ModelValidationException(
                        ErrorCode.findByValue(response.StatusCode) ?: ErrorCode.UNKNOWN_ERROR_CODE,
                        response.ServerError?.ErrorMessage ?: "",
                        response.ServerError?.ValidationErrors as List<ValidationError>
                    )
            }
            404 -> {
                throw ResourceNotFoundException(
                    ErrorCode.findByValue(response.StatusCode) ?: ErrorCode.UNKNOWN_ERROR_CODE,
                    response.ServerError?.ErrorMessage ?: ""
                )
            }
            401 -> {
                throw UnauthorizedException(
                    ErrorCode.findByValue(response.StatusCode) ?: ErrorCode.UNKNOWN_ERROR_CODE,
                    response.ServerError?.ErrorMessage ?: ""
                )
            }
            500 -> throw InternalServerErrorException(
                ErrorCode.findByValue(response.StatusCode) ?: ErrorCode.UNKNOWN_ERROR_CODE,
                response.ServerError?.ErrorMessage ?: ""
            )

        }
    }
}