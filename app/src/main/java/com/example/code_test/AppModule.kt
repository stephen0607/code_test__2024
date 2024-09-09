package com.example.code_test

import androidx.navigation.NavController
import com.example.code_test.api.GitHubRepository
import com.example.code_test.api.GitHubRepositoryImpl
import com.example.code_test.userInfo.UserInfoViewModel
import com.example.code_test.userList.UserListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        OkHttpClient.Builder().build()
    }
    single {
        Retrofit.Builder().baseUrl("https://api.github.com/").client(get())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single { get<Retrofit>().create(GitHubApiService::class.java) }
    single<GitHubRepository> { GitHubRepositoryImpl(get()) }
    factory { (navController: NavController) -> UserListViewModel(get(), navController) }
    viewModelOf(::UserInfoViewModel)
}