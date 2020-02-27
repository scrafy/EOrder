package com.eorder.infrastructure.services

import com.eorder.infrastructure.interfaces.IHttpClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class OkHttpClient : IHttpClient {


    override fun getJsonResponse(url: String) : String? {

        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Accept", "application/json")
            .get()
            .url(url)
            .build()

        return runBlocking(Dispatchers.IO) { async {client.newCall(request).execute() }.await().body?.string()}

    }

    override fun postJsonData(url: String, body: Any) : String? {

        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Accept", "application/json")
            .post(Gson().toJson(body).toRequestBody("application/json; charset=utf-8".toMediaType()))
            .url(url)
            .build()

        return runBlocking(Dispatchers.IO) { async{client.newCall(request).execute() }.await().body?.string()}

    }



    override fun postFormData(url: String, data: Map<String, String>) : String? {

        val form = FormBody.Builder()
        for(entry in data)  form.add(entry.key, entry.value)

        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Accept", "application/json")
            .post(form.build())
            .url(url)
            .build()

        return runBlocking(Dispatchers.IO) { async{client.newCall(request).execute() }.await().body?.string()}

    }

    override fun postMultipartFormData(url: String, data: Map<String, String>) : String? {

        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        for(entry in data)  requestBody.addFormDataPart(entry.key, entry.value)


        val request = Request.Builder()
            .url(url)
            .post(requestBody.build())
            .build()

        return runBlocking(Dispatchers.IO) { async{client.newCall(request).execute() }.await().body?.string()}
    }

    override fun postMultipartFormDataWithAttachment(url: String, data: Map<String, String>, filePath: String, format: String, fileName: String) : String? {

        var mediaType = (format).toMediaTypeOrNull()
        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        for(entry in data)  requestBody.addFormDataPart(entry.key, entry.value)
        requestBody.addFormDataPart("file",fileName, File(filePath).asRequestBody(mediaType))

        val request = Request.Builder()
            .url(url)
            .post(requestBody.build())
            .build()

        return runBlocking(Dispatchers.IO) { async{client.newCall(request).execute() }.await().body?.string()}
    }


   /* private fun internet() : Boolean{

      //  var t = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var n =  t.activeNetworkInfo.isConnected
    }*/
}

