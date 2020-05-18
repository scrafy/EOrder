package com.eorder.infrastructure.repositories

import com.eorder.domain.factories.Gson
import com.eorder.domain.interfaces.ICenterRepository
import com.eorder.domain.interfaces.IConfigurationManager
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.domain.models.Center
import com.eorder.domain.models.CenterCode
import com.eorder.domain.models.ServerData
import com.eorder.domain.models.ServerResponse
import com.eorder.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken

class CenterRepository(
    private val httpClient: IHttpClient,
    private val configurationManager: IConfigurationManager,
    private val jwtTokenService: IJwtTokenService
) : BaseRepository(), ICenterRepository {


    override fun checkCenterActivationCode(code: CenterCode): ServerResponse<Boolean> {

        var response: ServerResponse<Boolean> =
            ServerResponse(
                200,
                null,
                ServerData(false, null)

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

}