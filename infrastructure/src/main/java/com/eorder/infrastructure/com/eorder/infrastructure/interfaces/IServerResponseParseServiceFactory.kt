package com.eorder.infrastructure.com.eorder.infrastructure.interfaces

interface IServerResponseParseServiceFactory {

    fun < T, V > createService(service: String) : IServerResponseParseService<T, V>
}