package models

class ServerData<T>(
    val data: T,
    var paginationData: Pagination?,
    var validationErrors: List<InfraValidationError>?
)

