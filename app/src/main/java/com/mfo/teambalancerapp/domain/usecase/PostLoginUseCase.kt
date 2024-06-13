package com.mfo.teambalancerapp.domain.usecase

import com.mfo.teambalancerapp.data.network.response.LoginResponse
import com.mfo.teambalancerapp.domain.Repository
import com.mfo.teambalancerapp.domain.model.LoginRequest
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(private val repository: Repository){
    suspend operator fun invoke(loginRequest: LoginRequest): LoginResponse? = repository.authenticationUser(loginRequest)
}