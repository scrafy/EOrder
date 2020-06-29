package com.pedidoe.infrastructure.di

import com.pedidoe.domain.interfaces.IJwtTokenService
import com.pedidoe.infrastructure.interfaces.IHttpClient
import com.pedidoe.infrastructure.services.OkHttpClient


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