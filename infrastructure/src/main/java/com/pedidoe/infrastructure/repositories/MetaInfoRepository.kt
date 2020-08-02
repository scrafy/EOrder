package com.pedidoe.infrastructure.repositories

import com.pedidoe.domain.factories.Gson
import com.pedidoe.domain.interfaces.IConfigurationManager
import com.pedidoe.domain.models.Category
import com.pedidoe.domain.models.ServerResponse
import com.pedidoe.infrastructure.interfaces.IHttpClient
import com.google.gson.reflect.TypeToken
import com.pedidoe.domain.interfaces.IMetaInfoRepository
import com.pedidoe.domain.models.ApkVersion

class MetaInfoRepository(
    private val httpClient: IHttpClient,
    private val configurationManager: IConfigurationManager
) : BaseRepository(),
    IMetaInfoRepository {


    override fun getCurrentApkVersion(): ServerResponse<ApkVersion> {

        httpClient.addAuthorizationHeader(true)
        val url =
            "${configurationManager.getProperty("endpoint_url")}Meta/apkinfo"
        val resp = httpClient.getJsonResponse(
            url,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<ApkVersion>>(
            resp, object : TypeToken<ServerResponse<ApkVersion>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }

    override fun getApkVersions(): ServerResponse<List<ApkVersion>> {

        httpClient.addAuthorizationHeader(true)
        val url =
            "${configurationManager.getProperty("endpoint_url")}Meta/apkversions"
        val resp = httpClient.getJsonResponse(
            url,
            null
        )
        var response = Gson.Create().fromJson<ServerResponse<List<ApkVersion>>>(
            resp, object : TypeToken<ServerResponse<List<ApkVersion>>>() {}.type
        )
        checkServerErrorInResponse(response)
        return response
    }
}