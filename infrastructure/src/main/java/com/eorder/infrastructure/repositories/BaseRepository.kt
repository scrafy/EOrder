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
                else
                    throw BadRequestException(
                        ErrorCode.findByValue(response.StatusCode) ?: ErrorCode.UNKNOWN_ERROR_CODE,
                        response.ServerError?.ErrorMessage ?: "Bad request"
                    )
            }
            404 -> {
                throw ResourceNotFoundException(
                    ErrorCode.findByValue(response.StatusCode) ?: ErrorCode.UNKNOWN_ERROR_CODE,
                    response.ServerError?.ErrorMessage ?: "Resource not found"
                )
            }
            401 -> {
                throw UnauthorizedException(
                    ErrorCode.findByValue(response.StatusCode) ?: ErrorCode.UNKNOWN_ERROR_CODE,
                    response.ServerError?.ErrorMessage ?: "Unauthorized"
                )
            }
            500 -> throw InternalServerErrorException(
                ErrorCode.findByValue(response.StatusCode) ?: ErrorCode.UNKNOWN_ERROR_CODE,
                response.ServerError?.ErrorMessage ?: "Internal server error"
            )

        }
    }
}