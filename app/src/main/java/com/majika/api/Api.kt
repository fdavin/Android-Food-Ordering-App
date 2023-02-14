package com.majika.api

import com.majika.api.branch.BranchResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("/v1/branch")
    fun getBranch(): Call<BranchResponse>
}