package com.mfo.teambalancerapp.data

import android.util.Log
import com.mfo.teambalancerapp.data.network.TeamBalancerApiService
import com.mfo.teambalancerapp.data.network.response.LoginResponse
import com.mfo.teambalancerapp.domain.Repository
import com.mfo.teambalancerapp.domain.model.LoginRequest
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val apiService: TeamBalancerApiService): Repository {

    // login
    override suspend fun authenticationUser(loginRequest: LoginRequest): LoginResponse? {
        runCatching { apiService.authenticationUser(loginRequest)}
            .onSuccess { return it.toDomain() }
            .onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> throwable.response()?.errorBody()?.string()
                    else -> null
                } ?: "An error occurred: ${throwable.message}"
                Log.i("mfo", "Error occurred: $errorMessage")
                throw Exception(errorMessage)
            }
        return null
    }
}