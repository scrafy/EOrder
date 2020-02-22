package models.infrastructure

class ServerData<T>(
    var data: T?,
    var paginationData: Pagination?,
    var validationErrors: List<ValidationError>?
)

