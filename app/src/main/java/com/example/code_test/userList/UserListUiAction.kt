package com.example.code_test.userList

import com.example.code_test.model.User

interface UserListUiAction {
    fun onUserClick(user: User)
    fun onFetchUserList()
    fun onFetchUserListError()
    fun onFetchErrorRetry()
}