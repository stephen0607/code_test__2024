package com.example.code_test.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.code_test.api.GitHubRepository
import com.example.code_test.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

internal class UserListViewModel(
    private val githubRepository: GitHubRepository, private val navController: NavController
) : ViewModel(), KoinComponent {
    private val _userListFlow: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    private val _loadingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _errorFlow: MutableStateFlow<String> = MutableStateFlow("")
    val uiState: StateFlow<UserListScreenState> =
        combine(_userListFlow, _loadingFlow, _errorFlow) { userList, loading, error ->
            when {
                error.isNotBlank() -> {
                    UserListScreenState.Error(error)
                }

                loading -> {
                    UserListScreenState.Loading
                }

                userList.isNotEmpty() -> {
                    UserListScreenState.Success(userList)
                }

                else -> {
                    UserListScreenState.Default
                }
            }
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), UserListScreenState.Default
        )

    init {
        // may call userList here if needed
    }


    private fun getUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingFlow.tryEmit(true)

            // simulate network loading
            delay(100)

            val result = githubRepository.getUserList()
            _loadingFlow.tryEmit(false)
            result.fold(
                onSuccess = { userList ->
                    _userListFlow.tryEmit(userList)
                },
                onFailure = {
                    _errorFlow.tryEmit(it.message.toString())
                },
            )
        }
    }

    private fun getUserListError() {
        _errorFlow.tryEmit("Network error or other error")
    }

    val uiAction = object : UserListUiAction {
        override fun onUserClick(user: User) {
            navController.navigate("userInfo/${user.login}")
        }

        override fun onFetchUserList() {
            getUserList()
        }

        override fun onFetchUserListError() {
            getUserListError()
        }

        override fun onFetchErrorRetry() {
            // maybe add event tracking
            _errorFlow.tryEmit("")
            getUserList()
        }
    }
}