package com.eorder.infrastructure.com.eorder.infrastructure.interfaces

interface IOkHttpClient {

    fun  getJsonResponse(url: String) : String?
    fun  postJsonData(url: String, body: Any) : String?
    fun postFormData(url: String, data: Map<String, String>) : String?
    fun postMultipartFormDataWithAttachment(url: String, data: Map<String, String>, filePath: String, format: String, fileName: String) : String?
    fun postMultipartFormData(url: String, data: Map<String, String>) : String?
}