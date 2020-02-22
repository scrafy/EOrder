package com.eorder.application.models

import com.eorder.application.interfaces.IValidationErrors
import models.infrastructure.ServerResponse

class LoginResponse<T>(override var validationErrors: List<ValidationError>, var serverResponse: ServerResponse<T>?) :
    IValidationErrors

