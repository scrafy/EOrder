package com.eorder.infrastructure.repositories


import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.Login
import com.eorder.domain.models.RecoverPassword
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.repositories.BaseRepository


class UserRepository(private val httpClient: IHttpClient) : BaseRepository(),
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
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1ODUxMzc4MzgsImV4cCI6MTYxNjY3MzkwMywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.x0o23cAQ0n_KisTxCbx7hlN6lzlPhtfqzEhJpzKuKE8",
                    null
                )
            )
        checkServerErrorInResponse(response)
        return response
    }

}