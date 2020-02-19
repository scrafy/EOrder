package interfaces


import models.ValidationError

interface IValidationErrors {

    var validationErrors: List<ValidationError>
}