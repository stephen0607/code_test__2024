package com.example.code_test.userInfo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.code_test.model.Repo
import com.example.code_test.model.UserInfo
import com.example.code_test.model.getDummyRepositoryInfo
import com.example.code_test.model.getDummyUserInfo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun CmDivider() {
    HorizontalDivider(
        color = Color.Gray, modifier = Modifier.height(0.5.dp)
    )
}

@Composable
fun IconText(
    text: String, icon: ImageVector, tint: Color? = null,
    textColor: Color? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = tint ?: MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor ?: MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun UserInfoSuccessScreen(
    userInfo: UserInfo = getDummyUserInfo(),
    repos: List<Repo> = emptyList(),
    navController: NavHostController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Top
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(0.7f),
            ),
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = userInfo.avatarUrl,
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    RepoFollowersRow(repos, userInfo)
                }
                Column {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        userInfo.name ?: userInfo.login,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    userInfo.email?.let {
                        Spacer(Modifier.height(8.dp))
                        IconText(
                            text = it, icon = Icons.Filled.Email,
                            textColor = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    userInfo.location?.let {
                        Spacer(Modifier.height(4.dp))
                        IconText(
                            text = it, icon = Icons.Filled.LocationOn,
                            textColor = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    userInfo.bio?.let {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            it, color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }

            }
        }
        if (repos.isNotEmpty()) {
            RepoListColumn(repos, navController)
        }
    }
}

@Composable
fun RepoListColumn(repos: List<Repo>, navController: NavHostController?) {
    Box(modifier = Modifier.height(16.dp))
    Text("Repo Lists", fontSize = 20.sp)
    Box(modifier = Modifier.height(8.dp))
    LazyColumn {
        items(repos) { repo ->
            if (navController != null) {
                RepoRow(repo, navController)
                CmDivider()
            }
        }
    }
}

@Composable
fun RepoFollowersRowText(s: String) {
    Text(
        s, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun RepoFollowersRowDivider() {
    VerticalDivider(
        color = MaterialTheme.colorScheme.onPrimary.copy(0.7f), modifier = Modifier
            .height(50.dp)
            .width(0.5.dp)
    )
}

@Composable
fun RepoFollowersRow(repos: List<Repo>, userInfo: UserInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RepoFollowersRowText("${repos.size}\nRepos")
        RepoFollowersRowDivider()
        RepoFollowersRowText("${userInfo.followers.toString()}\nFollowers")
        RepoFollowersRowDivider()
        RepoFollowersRowText("${userInfo.following.toString()}\nFollowing")
    }
}

@Composable
fun RepoRow(repo: Repo = getDummyRepositoryInfo(), navController: NavHostController) {
    Column(modifier = Modifier
        .padding(vertical = 8.dp)
        .clickable {
            val encodedUrl = URLEncoder.encode(repo.htmlUrl, StandardCharsets.UTF_8.toString())
            navController.navigate("webview/${repo.name}/$encodedUrl")
        }) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                repo.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            IconText(
                text = repo.stargazersCount.toString(),
                icon = Icons.Filled.Star,
                tint = Color.Yellow
            )
        }
        Text(
            "${repo.description}", color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            "Language: ${repo.language ?: "N/A"}", color = MaterialTheme.colorScheme.onBackground
        )
    }
}