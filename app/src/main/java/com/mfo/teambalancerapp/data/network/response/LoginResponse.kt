package com.mfo.teambalancerapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.mfo.teambalancerapp.domain.model.User

data class LoginResponse (@SerializedName ("token") val token: String, @SerializedName ("user") val user: User) {
    fun toDomain(): LoginResponse {
        return LoginResponse(
            token = token,
            user = user
        )
    }
}