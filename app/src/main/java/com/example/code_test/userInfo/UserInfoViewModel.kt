package com.example.code_test.userInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.code_test.api.GitHubRepository
import com.example.code_test.model.Repo
import com.example.code_test.model.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

internal class UserInfoViewModel(
    private val githubRepository: GitHubRepository
) : ViewModel(), KoinComponent {
    private val _userInfoFlow: MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    private val _reposFlow: MutableStateFlow<List<Repo>> = MutableStateFlow(emptyList())
    private val _loadingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _errorFlow: MutableStateFlow<String> = MutableStateFlow("")

    val uiAction = object : UserInfoUiAction {
        override fun onUserClick() {
        }
    }
    val uiState: StateFlow<UserInfoScreenState> = combine(
        _userInfoFlow, _reposFlow, _loadingFlow, _errorFlow
    ) { userInfo, repos, loading, error ->
        when {
            error.isNotBlank() -> {
                UserInfoScreenState.Error(error)
            }

            loading -> {
                UserInfoScreenState.Loading
            }

            userInfo != null -> {
                UserInfoScreenState.Success(userInfo, repos)
            }

            else -> {
                UserInfoScreenState.Default
            }
        }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), UserInfoScreenState.Default
    )

    fun initVm(userId: String) {
        getUserInfo(userId)
        getRepos(userId)
    }

    private fun getUserInfo(userId: String) {
        viewModelScope.launch {
            githubRepository.getUserInfo(userId).fold(onSuccess = {
                _userInfoFlow.tryEmit(it)
            }, onFailure = {
                _errorFlow.tryEmit(it.toString())
            })
        }
    }

    private fun getRepos(userId: String) {
        viewModelScope.launch {
            githubRepository.getUserRepos(userId).fold(onSuccess = {
                _reposFlow.tryEmit(
                    //filter the forked repo
                    it.filter { repo -> !repo.fork })
            }, onFailure = {
                _errorFlow.tryEmit(it.toString())
            })
        }
    }
}