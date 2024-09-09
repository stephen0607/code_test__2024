package com.example.code_test.userList

import androidx.compose.runtime.Composable


import com.example.code_test.model.User
import com.example.code_test.userList.screen.UserListDefaultScreen
import com.example.code_test.userList.screen.UserListErrorScreen
import com.example.code_test.userList.screen.UserListErrorScreenUiState
import com.example.code_test.userList.screen.UserListLoadingScreen
import com.example.code_test.userList.screen.UserListSuccessScreen

sealed class UserListScreenState(val appBarTitle: String = "") {
    object Default : UserListScreenState("Welcome Screen") {
        @Composable
        fun render(uiAction: UserListUiAction) {
            UserListDefaultScreen(uiAction)
        }
    }

    object Loading : UserListScreenState("Loading Screen") {
        @Composable
        fun render() {
            UserListLoadingScreen()
        }
    }

    data class Success(
        val userList: List<User> = emptyList(),
    ) : UserListScreenState("User List") {
        @Composable
        fun render(uiAction: UserListUiAction) {
            UserListSuccessScreen(userList, uiAction)
        }
    }

    data class Error(val message: String = "") : UserListScreenState("Error Screen") {
        @Composable
        fun render(uiAction: UserListUiAction) {
            UserListErrorScreen(
                UserListErrorScreenUiState(
                    message, uiAction::onFetchErrorRetry
 w               )
            )
        }
    }
}