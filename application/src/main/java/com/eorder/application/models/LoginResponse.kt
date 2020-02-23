package com.eorder.application.models

import com.eorder.application.interfaces.IValidationErrors
import com.eorder.infrastructure.models.ServerResponse

class LoginResponse<T>(override var validationErrors: List<ValidationError>, var serverResponse: ServerResponse<T>?) :
    IValidationErrors

