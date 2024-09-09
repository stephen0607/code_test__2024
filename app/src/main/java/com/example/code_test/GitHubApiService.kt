package com.example.code_test

import com.example.code_test.model.Repo
import com.example.code_test.model.User
import com.example.code_test.model.UserInfo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET(PATH_USER_LIST)
    suspend fun getUserList(
        @Header("Authorization") token: String,
        @Header("Accept") accept: String = ACCEPT,
        @Query("per_page") perPage: Int
    ): List<User>

    @GET("$PATH_USER_LIST/{username}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Header("Accept") accept: String = ACCEPT,
        @Path("username") username: String
    ): UserInfo


    @GET("$PATH_USER_LIST/{username}/repos")
    suspend fun getUserRepos(
        @Header("Authorization") token: String,
        @Header("Accept") accept: String = ACCEPT,
        @Path("username") username: String
    ): List<Repo>

    companion object {
        private const val PATH_USER_LIST = "users"
        private const val ACCEPT = "application/vnd.github+json"
        const val TOKEN =
            "Bearer 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"
    }
}