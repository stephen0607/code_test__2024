package com.example.code_test.userInfo


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.code_test.model.Repo
import com.example.code_test.model.UserInfo
import com.example.code_test.userInfo.screen.UserInfoDefaultScreen
import com.example.code_test.userInfo.screen.UserInfoErrorScreen
import com.example.code_test.userInfo.screen.UserInfoLoadingScreen
import com.example.code_test.userInfo.screen.UserInfoSuccessScreen

sealed class UserInfoScreenState(val appBarTitle: String = "GitHub User List") {
    object Default : UserInfoScreenState() {
        @Composable
        fun render(uiAction: UserInfoUiAction) {
            UserInfoDefaultScreen(uiAction)
        }
    }

    object Loading : UserInfoScreenState("Loading Screen") {
        @Composable
        fun render() {
            UserInfoLoadingScreen()
        }
    }

    data class Success(
        private val userInfo: UserInfo,
        private val repos: List<Repo>,
    ) :
        UserInfoScreenState() {
        @Composable
        fun render(
            navController: NavHostController
        ) {
            UserInfoSuccessScreen(userInfo, repos, navController)
        }
    }

    data class Error(val message: String = "") : UserInfoScreenState("Error Screen") {
        @Composable
        fun render() {
            UserInfoErrorScreen(message)
        }
    }
}