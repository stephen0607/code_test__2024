package com.example.code_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.code_test.userInfo.UserInfoScreen
import com.example.code_test.userInfo.screen.UserInfoErrorScreen
import com.example.code_test.userList.UserListScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "userList") {
                    composable("userList") { UserListScreen(navController) }
                    composable("userInfo/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")
                        userId?.let {
                            UserInfoScreen(navController, it)
                        } ?: run {
                            UserInfoErrorScreen(message = "empty user")
                        }
                    }
                    composable("webview/{repoName}/{encodedUrl}") { backStackEntry ->
                        val encodedUrl = backStackEntry.arguments?.getString("encodedUrl") ?: ""
                        val repoName = backStackEntry.arguments?.getString("repoName") ?: ""
                        val decodedUrl =
                            URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
                        WebViewComponent(
                            repoName,
                            decodedUrl,
                            onBackPressed = { navController.popBackStack() })
                    }
                }
            }

        }
    }
}