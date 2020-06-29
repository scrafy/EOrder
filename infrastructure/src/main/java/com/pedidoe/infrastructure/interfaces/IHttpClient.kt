package com.pedidoe.infrastructure.interfaces

interface IHttpClient {

    fun addAuthorizationHeader(authorize:Boolean)
    fun getJsonResponse(url: String, headers: Map<String, String>?) : String?
    fun postJsonData(url: String, body: Any, headers: Map<String, String>?) : String?
    fun postFormData(url: String, data: Map<String, String>, headers: Map<String, String>?) : String?
    fun postMultipartFormDataWithAttachment(url: String, data: Map<String, String>, filePath: String, format: String, fileName: String, headers: Map<String, String>?) : String?
    fun postMultipartFormData(url: String, data: Map<String, String>, headers: Map<String, String>?) : String?
}