package com.eorder.infrastructure.repositories


import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.Login
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.repositories.BaseRepository


class UserRepository(var httpClient: IHttpClient) : BaseRepository(),
    IUserRepository {

    override fun recoverPassword(recoverPasswordRequest: RecoverPassword): ServerResponse<String> {

        //TODO MAKE REAL CALL TO BACKEND
        var response: ServerResponse<String> =
            ServerResponse(
                200,
                null,
                ServerData(
                    "OK",
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun login(loguinRequest: Login): ServerResponse<String> {

        //TODO MAKE REAL CALL TO BACKEND
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

        return response
    }

}