package com.example.code_test.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ThemeFilledButton(s: String = "ThemeFilledButton", onUserClick: () -> Unit = {}) {
    Row {
        Button(
            onClick = {
                onUserClick()
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
                .background(
                    MaterialTheme.colorScheme.primary, CircleShape
                ),
        ) {
            Text(
                s,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun ThemeOutlineButton(
    text: String = "ThemeOutlineButton",
    onUserClick: () -> Unit = {}
) {
    Row {
        Button(
            onClick = onUserClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = CircleShape
        ) {
            Text(
                text,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Preview
@Composable
fun ThemeTextButton(
    text: String = "ThemeOutlineButton",
    onUserClick: () -> Unit = {}
) {
    Row {
        Button(
            onClick = onUserClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
        ) {
            Text(
                text,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
