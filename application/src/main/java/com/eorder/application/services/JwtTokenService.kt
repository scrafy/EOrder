package com.eorder.application.services


import com.eorder.domain.enumerations.UserRole
import com.eorder.application.interfaces.IJwtTokenService
import org.json.JSONObject

class JwtTokenService : IJwtTokenService {

    override fun isValidToken(token: String): Boolean{

        return true
    }

    override fun getClaimFromToken(token: String, claim: String): Any{

        var json = android.util.Base64.decode(token.split(".")[1], android.util.Base64.DEFAULT).toString( Charsets.UTF_8 )
        var t = JSONObject(json)
        return t.get(claim)
    }

    override fun getRole(token:String) : UserRole {

        return this.getClaimFromToken(token, "role") as UserRole
    }
}