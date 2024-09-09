@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.code_test.userList.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.code_test.model.User
import com.example.code_test.userList.UserListUiAction

@Composable
fun UserListSuccessScreen(
    userList: List<User>, uiAction: UserListUiAction
) {
    val isAscending = remember { mutableStateOf(true) }
    val filterText = remember { mutableStateOf("") }

    // Filter and sort the user list
    val filteredUserList =
        userList.filter { it.login.contains(filterText.value, ignoreCase = true) }
            .sortedWith(if (isAscending.value) compareBy { it.login } else compareByDescending { it.login })

    Column(modifier = Modifier.fillMaxSize()) {
        FilterAndSortBar(filterText.value,
            isAscending.value,
            { s -> filterText.value = s },
            { isAscending.value = !isAscending.value })
        UserList(filteredUserList, uiAction)
    }
}

@Composable
fun FilterAndSortBar(
    filterText: String,
    isAscending: Boolean,
    onTextChange: (String) -> Unit,
    onOrderChange: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            value = filterText,
            onValueChange = { s -> onTextChange(s) },
            label = { Text("Filter user by login ID") },
            modifier = Modifier.weight(1f),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
            },
        )
        Icon(imageVector = if (isAscending) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable { onOrderChange() })
    }
}

@Composable
fun UserList(filteredUserList: List<User>, uiAction: UserListUiAction) {
    LazyColumn() {
        items(filteredUserList) { user ->
            UserRow(user = user, onUserClick = { uiAction.onUserClick(user) })
            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun UserRow(user: User, onUserClick: (User) -> Unit) {
    Row(modifier = Modifier
        .clickable { onUserClick(user) }
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = user.login, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground
        )
    }
}