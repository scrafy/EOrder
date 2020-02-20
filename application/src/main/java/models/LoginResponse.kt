package models

import interfaces.IValidationErrors
import models.infrastructure.ServerResponse

class LoginResponse<T>(override var validationErrors: List<ValidationError>, var serverResponse: ServerResponse<T>?) : IValidationErrors

