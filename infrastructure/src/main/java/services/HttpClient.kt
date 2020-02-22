package services

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject


class HttpClient(var context: Context) {

/*
    fun getJsonObject(url: String) : JSONObject{

        //val queue = Volley.newRequestQueue(context)
        var future: RequestFuture<JSONObject> = RequestFuture.newFuture()
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,future,future)
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)
        return future.get()
    }*/

    fun getJsonObject(url: String) : JSONObject {

        //val queue = Volley.newRequestQueue(context)
        var future: RequestFuture<JSONObject> = RequestFuture.newFuture()
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,future,future)
        future.setRequest(jsonObjectRequest)
        GlobalScope.launch { // launch a new coroutine in background and continue
            return future.get()
        }
        return future.get()
    }

   /* private fun internet() : Boolean{

      //  var t = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var n =  t.activeNetworkInfo.isConnected
    }*/
}