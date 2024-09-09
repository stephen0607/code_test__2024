package com.example.code_test.api

import com.example.code_test.GitHubApiService
import com.example.code_test.model.Repo
import com.example.code_test.model.User
import com.example.code_test.model.UserInfo

internal class GitHubRepositoryImpl(
    private val apiService: GitHubApiService
) : GitHubRepository {
    override suspend fun getUserList(): Result<List<User>> {
        return try {
            val response = apiService.getUserList(GitHubApiService.TOKEN, perPage = 30)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserInfo(userName: String): Result<UserInfo> {
        return try {
            val response = apiService.getUser(GitHubApiService.TOKEN, username = userName)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserRepos(userName: String): Result<List<Repo>> {
        return try {
            val response = apiService.getUserRepos(GitHubApiService.TOKEN, username = userName)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}