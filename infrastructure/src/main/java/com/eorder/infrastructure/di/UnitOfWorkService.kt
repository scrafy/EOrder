package com.eorder.infrastructure.di

import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.infrastructure.interfaces.IHttpClient
import com.eorder.infrastructure.services.OkHttpClient


class UnitOfWorkService(
    private val jwtTokenService: IJwtTokenService

) {

    private var httpClient: IHttpClient ? = null

    fun getHttpClient(): IHttpClient {

        if (httpClient == null)

            httpClient = OkHttpClient(okhttp3.OkHttpClient(), jwtTokenService)

        return httpClient as IHttpClient
    }
}