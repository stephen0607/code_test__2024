package com.example.code_test

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import com.example.code_test.ui.theme.AppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SetJavaScriptEnabled")
@Composable
fun WebViewComponent(repoName: String, url: String, onBackPressed: () -> Unit) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    Scaffold(topBar = {
        AppBar(appBarTitle = repoName, onClickF = {
            // Handle back navigation
            if (webView?.canGoBack() == true) {
                webView?.goBack()
            } else {
                onBackPressed()
            }
        })
    }, content = {
        AndroidView(factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        }, update = { webView ->
            webView.loadUrl(url)
        })
    })
}