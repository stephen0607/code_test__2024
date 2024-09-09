package com.example.code_test.api

import com.example.code_test.model.Repo
import com.example.code_test.model.User
import com.example.code_test.model.UserInfo

internal interface GitHubRepository {
    suspend fun getUserList(): Result<List<User>>
    suspend fun getUserInfo(userName: String): Result<UserInfo>
    suspend fun getUserRepos(userName: String): Result<List<Repo>>
}