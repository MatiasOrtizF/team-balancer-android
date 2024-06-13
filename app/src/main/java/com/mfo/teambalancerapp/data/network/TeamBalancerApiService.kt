package com.mfo.teambalancerapp.data.network

import com.mfo.teambalancerapp.data.network.response.LoginResponse
import com.mfo.teambalancerapp.domain.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface TeamBalancerApiService {

    @POST("login")
    suspend fun authenticationUser(@Body loginRequest: LoginRequest): LoginResponse
}