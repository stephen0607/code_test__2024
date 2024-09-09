package com.example.code_test.userList.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.code_test.ui.theme.ThemeFilledButton
import com.example.code_test.userList.UserListUiAction

@Composable
fun UserListDefaultScreen(uiAction: UserListUiAction) {
    Box {
        Column {
            ThemeFilledButton(
                "fetch user list ( Success )",
                uiAction::onFetchUserList,
            )
            Spacer(modifier = Modifier.height(10.dp))
            ThemeFilledButton(
                "fetch user list ( Error )",
                uiAction::onFetchUserListError,
            )
        }
    }
}