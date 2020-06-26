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


    override fun createAccount(account: Account): ServerResponse<UserProfile> {

        httpClient.addAuthorizationHeader(false)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}Users/register",
            account,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<UserProfile>>(
            resp, object : TypeToken<ServerResponse<UserProfile>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

    override fun checkUserEmail(email: Email): ServerResponse<Boolean> {

        httpClient.addAuthorizationHeader(false)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}Users/finduserbyemail",
            email,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<Boolean>>(
            resp, object : TypeToken<ServerResponse<Boolean>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response

    }

    override fun getFavoriteProducts( searchProduct: SearchProduct ): ServerResponse<List<Product>> {

        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}ProductDetails/subsetproducts",
            searchProduct,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<Product>>>(
            resp, object : TypeToken<ServerResponse<List<Product>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

    override fun changePassword(recoverPasswordRequest: ChangePassword): ServerResponse<Any> {

        httpClient.addAuthorizationHeader(true)
        var resp = httpClient.postJsonData(
            "${configurationManager.getProperty("endpoint_url")}Users/changepassword",
            recoverPasswordRequest,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<Any>>(
            resp, object : TypeToken<ServerResponse<Any>>() {}.type
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