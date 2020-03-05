package com.eorder.infrastructure.services

import com.eorder.domain.models.ValidationError
import com.eorder.infrastructure.interfaces.ILoginService
import com.eorder.infrastructure.models.LoginRequest
import com.eorder.infrastructure.models.ServerData
import com.eorder.infrastructure.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.models.ServerError
import java.lang.Exception


class LoginService(var httpClient: IHttpClient) : BaseService(),
    ILoginService {

    override fun login(loguinRequest: LoginRequest): ServerResponse<String> {


        var response: ServerResponse<String> =
            ServerResponse(
                200,
                null,
                ServerData(
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiY2VudGVyIiwibmFtZSI6Ikpvc8OpIEx1aXMiLCJhZG1pbiI6ZmFsc2UsImlkdXNlciI6IjdiM2MyMmM2LWZjMGQtNDI1ZC1iMWJmLTc4YjQ2OWZjNjdlYiIsImp0aSI6IjdiM2MyMmM2LWZjMGQtNDI1ZC1iMWJmLTc4YjQ2OWZjNjdlYiIsImlhdCI6MTU4Mjc0MTU4NCwiZXhwIjoxNTgyNzQ1NDcwfQ.xJA0bwsgn9LCHmTqSjwypUPf37epkM3Y-RkrWSiv8VE",
                    null
                )
            )
        checkServerErrorInResponse(response)
        throw Exception("caca de la buena")
        return response
    }

}