package com.example.code_test.userInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.code_test.ui.theme.AppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserInfoScreen(navController: NavHostController, userId: String) {
    val viewModel: UserInfoViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiAction = viewModel.uiAction
    viewModel.initVm(userId)
    Scaffold(topBar = {
        AppBar(
            userId
        ) { navController.navigateUp() }
    }, content = {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is UserInfoScreenState.Default -> (uiState as UserInfoScreenState.Default).render(
                    uiAction
                )

                is UserInfoScreenState.Error -> (uiState as UserInfoScreenState.Error).render()
                is UserInfoScreenState.Loading -> (uiState as UserInfoScreenState.Loading).render()
                is UserInfoScreenState.Success -> ((uiState as UserInfoScreenState.Success).render(
                    navController
                ))
            }
        }
    })
}