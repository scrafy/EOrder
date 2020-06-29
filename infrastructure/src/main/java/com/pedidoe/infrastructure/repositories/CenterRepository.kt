package com.pedidoe.infrastructure.repositories

import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.interfaces.ICenterRepository
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.interfaces.IJwtTokenService
import com.pedidoe.domain.models.*
import com.pedidoe.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken

class CenterRepository(
    private val httpClient: IHttpClient,
    private val configurationManager: IConfigurationManager,
    private val jwtTokenService: IJwtTokenService
) : BaseRepository(), ICenterRepository {


    override fun checkCenterActivationCode(code: CenterCode): ServerResponse<Boolean> {

        val url = "${configurationManager.getProperty("endpoint_url")}Centres/${code.centerCode}/ActivationCode"
        val resp = httpClient.getJsonResponse (
            url,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<Boolean>>(
            resp, object : TypeToken<ServerResponse<Boolean>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }


    override fun getUserCenters(): ServerResponse<List<Center>> {

        httpClient.addAuthorizationHeader(true)
        val url = "${configurationManager.getProperty("endpoint_url")}Users/${jwtTokenService.getClaimFromToken("userId")}/centres"
        val resp = httpClient.getJsonResponse (
            url,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<Center>>>(
            resp, object : TypeToken<ServerResponse<List<Center>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

    override fun associateAccountToCentreCode(data: AccountCentreCode): ServerResponse<UserProfile> {

        val url = "${configurationManager.getProperty("endpoint_url")}Centres/associatecentre"
        val resp = httpClient.postJsonData (
            url,
            data,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<UserProfile>>(
            resp, object : TypeToken<ServerResponse<UserProfile>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

}