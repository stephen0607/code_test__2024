package com.example.code_test.userList

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.code_test.ui.theme.AppBar
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserListScreen(navController: NavHostController) {
    val viewModel: UserListViewModel = koinViewModel { parametersOf(navController) }
    val uiState by viewModel.uiState.collectAsState()
    val uiAction = viewModel.uiAction
    Scaffold(topBar = {
        AppBar(
            appBarTitle = uiState.appBarTitle,
        )
    }, content = {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is UserListScreenState.Default -> (uiState as UserListScreenState.Default).render(
                    uiAction
                )

                is UserListScreenState.Error -> (uiState as UserListScreenState.Error).render(
                    uiAction
                )

                is UserListScreenState.Loading -> (uiState as UserListScreenState.Loading).render()
                is UserListScreenState.Success -> ((uiState as UserListScreenState.Success).render(
                    uiAction
                ))
            }
        }
    })
}