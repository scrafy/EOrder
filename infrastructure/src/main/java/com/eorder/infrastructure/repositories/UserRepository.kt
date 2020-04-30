package com.eorder.infrastructure.repositories

import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.*
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.services.ProductsService


class UserRepository(private val httpClient: IHttpClient) : BaseRepository(),
    IUserRepository {


    override fun createAccount(account: Account): ServerResponse<Any> {

        val response = ServerResponse<Any>(
            200,
            null,
            null
        )
        checkServerErrorInResponse(response)

        return response
    }

    override fun checkUserEmail(email: Email): ServerResponse<Boolean> {

        val response = ServerResponse(
            200,
            null,
            ServerData(
                false,
                null
            )
        )
        checkServerErrorInResponse(response)

        return response

    }

    override fun getFavoriteProducts(favorites: List<Int>): ServerResponse<List<Product>> {


        var filtered = ProductsService.getProducts().filter { p -> p.id in favorites }
        var response: ServerResponse<List<Product>> =
            ServerResponse(
                200,
                null,
                ServerData(
                    filtered,
                    null
                )
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun changePassword(recoverPasswordRequest: ChangePassword): ServerResponse<Any> {

        //TODO MAKE REAL CALL TO BACKEND
        var response: ServerResponse<Any> =
            ServerResponse(
                200,
                null,
                null
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun recoverPassword(email: Email): ServerResponse<Any> {

        //TODO MAKE REAL CALL TO BACKEND
        var response: ServerResponse<Any> =
            ServerResponse(
                200,
                null,
               null
            )
        checkServerErrorInResponse(response)

        return response
    }

    override fun login(loguinRequest: Login): ServerResponse<String> {

        //TODO MAKE REAL CALL TO BACKEND

        var response: ServerResponse<String>? = null

        if (loguinRequest.username == "jose")

            response = ServerResponse(
                200,
                null,
                ServerData(
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1ODgxOTg2MTAsImV4cCI6MTYxOTczNDYxMCwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsInVzZXJuYW1lIjoiSm9zZSIsImVtYWlsIjoic2NyYWZ5MjZAZ21haWwuY29tIiwicGhvbmUiOiI2MjcyMDYzNjkiLCJ1c2VySWQiOiIxMjMiLCJ1c2VySGFzQ2VudGVycyI6InRydWUifQ.KLikj5aohZhvGTTOZlMNAfNnaXoKM3bnSKH9k4rSsJA",
                    null
                )
            )
        else if (loguinRequest.username == "fran")

            response = ServerResponse(
                200,
                null,
                ServerData(
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1ODgxOTg2MTAsImV4cCI6MTYxOTczNDc4MywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsInVzZXJuYW1lIjoiZnJhbiIsImVtYWlsIjoic2NyYWZ5MjZAZ21haWwuY29tIiwicGhvbmUiOiI2MjcyMDYzNjkiLCJ1c2VySWQiOiIxMjQiLCJ1c2VySGFzQ2VudGVycyI6InRydWUifQ.yZ6CBqBMnjyVRFmUMCqIMWfoy2aBqcmFfINBr93ak8A",
                    null
                )
            )
        else
            response = ServerResponse(
                500,
                ServerError("The users does not exists", 500, null, null)

            )

        checkServerErrorInResponse(response!!)
        return response
    }

}