package com.mfo.teambalancerapp.domain

import com.mfo.teambalancerapp.data.network.response.LoginResponse
import com.mfo.teambalancerapp.domain.model.LoginRequest

interface Repository {
    // login
    suspend fun authenticationUser(loginRequest: LoginRequest): LoginResponse?
}