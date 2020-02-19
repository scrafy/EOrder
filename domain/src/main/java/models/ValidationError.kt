package models

class ValidationError {

    var fieldName: String = ""
    var modelName: String = ""
    var errorMessage: String = ""
    var value: String? = null

    constructor( errorMessage: String, value: String?, fieldName: String, modelName: String ){

        this.errorMessage = errorMessage
        this.value = value
        this.fieldName = fieldName
        this.modelName = modelName
    }
}