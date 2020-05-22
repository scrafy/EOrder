package com.eorder.infrastructure.repositories

import com.eorder.domain.factories.Gson
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.IUserRepository
import com.eorder.domain.models.*
import com.eorder.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken


class UserRepository(
    private val httpClient: IHttpClient,
    private val configurationManager: IConfigurationManager
) : BaseRepository(),
    IUserRepository {


    override fun createAccount(account: Account): ServerResponse<Any> {

        httpClient.addAuthorizationHeader(false)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}Users/register",
            account,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<Any>>(
            resp, object : TypeToken<ServerResponse<String>>() {}.type
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


        var resp =  ServerResponse<List<Product>>(200, null, ServerData(listOf<Product>(),null))
        return resp
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

        httpClient.addAuthorizationHeader(false)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}Users/forgotPassword",
            email,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<Any>>(
            resp, object : TypeToken<ServerResponse<Any>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

    override fun login(loguinRequest: Login): ServerResponse<String> {

        httpClient.addAuthorizationHeader(false)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}Users/authenticate",
            loguinRequest,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<String>>(
            resp, object : TypeToken<ServerResponse<String>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

}