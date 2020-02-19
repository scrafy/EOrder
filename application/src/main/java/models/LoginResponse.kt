package models

import interfaces.IValidationErrors

class LoginResponse<T>(override var validationErrors: List<ValidationError>, var serverResponse: ServerResponse<T>?) : IValidationErrors

