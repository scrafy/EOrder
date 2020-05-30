package com.eorder.infrastructure.services

import com.eorder.domain.enumerations.ErrorCode
import com.eorder.domain.exceptions.InvalidJwtTokenException
import com.eorder.domain.factories.Gson
import com.eorder.domain.interfaces.IJwtTokenService
import com.eorder.infrastructure.interfaces.IHttpClient
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File



class OkHttpClient(private val client: OkHttpClient, private val tokenService: IJwtTokenService) :
    IHttpClient {

    private var addAuthorizationHeader: Boolean = false

    override fun addAuthorizationHeader(authorize: Boolean) {
        addAuthorizationHeader = authorize
    }

    override fun getJsonResponse(url: String, headers: Map<String, String>?): String? {


        val request = with(Request.Builder()) {
            addHeader("Accept", "application/json")
            if (addAuthorizationHeader)
                addHeader("Authorization", "Bearer ${tokenService.getToken()}")
            headers?.forEach { header -> addHeader(header.key, header.value) }
            get()
            url(url)
            build()
        }

        val resp = client.newCall(request).execute()
        this.refreshToken(resp.headers["Authorization"])
        return resp.body?.string()

    }

    override fun postJsonData(url: String, body: Any, headers: Map<String, String>?): String? {

        val request = with(Request.Builder()) {
            addHeader("Accept", "application/json")
            if (addAuthorizationHeader)
                addHeader("Authorization", "Bearer ${tokenService.getToken()}")
            headers?.forEach { header -> addHeader(header.key, header.value) }
            post(Gson.Create().toJson(body).toRequestBody("application/json; charset=utf-8".toMediaType()))
            url(url)
            build()
        }
        var resp = client.newCall(request).execute()
        this.refreshToken(resp.headers["Authorization"])
        return resp?.body?.string()
    }


    override fun postFormData(
        url: String,
        data: Map<String, String>,
        headers: Map<String, String>?
    ): String? {

        val form = FormBody.Builder()
        for (entry in data) form.add(entry.key, entry.value)


        val request = with(Request.Builder()) {
            if (addAuthorizationHeader)
                addHeader("Authorization", "Bearer ${tokenService.getToken()}")
            headers?.forEach { header -> addHeader(header.key, header.value) }
            post(form.build())
            url(url)
            build()
        }

        val resp = client.newCall(request).execute()
        this.refreshToken(resp.headers["Authorization"])
        return resp.body?.string()

    }

    override fun postMultipartFormData(
        url: String,
        data: Map<String, String>,
        headers: Map<String, String>?
    ): String? {


        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        for (entry in data) requestBody.addFormDataPart(entry.key, entry.value)


        val request = with(Request.Builder()) {
            url(url)
            if (addAuthorizationHeader)
                addHeader("Authorization", "Bearer ${tokenService.getToken()}")
            headers?.forEach { header -> addHeader(header.key, header.value) }
            post(requestBody.build())
            build()
        }
        val resp = client.newCall(request).execute()
        this.refreshToken(resp.headers["Authorization"])
        return resp.body?.string()
    }

    override fun postMultipartFormDataWithAttachment(
        url: String,
        data: Map<String, String>,
        filePath: String,
        format: String,
        fileName: String,
        headers: Map<String, String>?
    ): String? {

        var mediaType = (format).toMediaTypeOrNull()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        for (entry in data) requestBody.addFormDataPart(entry.key, entry.value)
        requestBody.addFormDataPart("file", fileName, File(filePath).asRequestBody(mediaType))

        val request = with(Request.Builder()) {
            url(url)
            if (addAuthorizationHeader)
                addHeader("Authorization", "Bearer ${tokenService.getToken()}")
            headers?.forEach { header -> addHeader(header.key, header.value) }
            post(requestBody.build())
            build()
        }
        val resp = client.newCall(request).execute()
        this.refreshToken(resp.headers["Authorization"])
        return resp.body?.string()
    }

    private fun refreshToken(newToken: String?) {

        if (newToken != null) {

            val list = newToken?.split(" ")
            if (list != null) {
                tokenService.refreshToken(
                    list[1]
                )
            } else {
                throw InvalidJwtTokenException(
                    ErrorCode.JWT_TOKEN_INVALID,
                    "The getToken() session is invalid"
                )
            }
        }
    }
}

