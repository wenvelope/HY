package com.hys.hy.setting.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.icons.Bilibili
import com.hys.hy.designsystem.component.icons.Github
import com.hys.hy.designsystem.component.icons.QQ
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import hy.features.setting.generated.resources.Res
import hy.features.setting.generated.resources.app_logo
import hy.features.setting.generated.resources.app_text
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "关于",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    NavigationBackButton(
                        onBackButtonClick = onBackClick
                    )
                },
                modifier = Modifier.shadow(elevation = 1.dp)
            )
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(scrollState)
        ) {

            Image(
                modifier = Modifier.padding(top = 20.dp).size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(Res.drawable.app_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Image(
                modifier = Modifier.width(36.dp).height(28.dp).offset { IntOffset(0, -40) }
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(Res.drawable.app_text),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Version：1.0.0",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            FunctionList()

            Spacer(modifier = Modifier.height(28.dp))

            DeveloperInfo()

            Spacer(modifier = Modifier.height(28.dp))

            ContactUs()

            Spacer(modifier = Modifier.height(28.dp))

            AllRightsReserved()

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
fun AllRightsReserved() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "© 2024 HY App. All rights reserved.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

}

@Composable
fun ContactUs() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        ElevatedButton(
            modifier = Modifier.size(40.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {}
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.QQ,
                    contentDescription = "联系qq",
                    modifier = Modifier.size(25.dp).align(Alignment.Center),
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        ElevatedButton(
            modifier = Modifier.size(40.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {}
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.Github,
                    contentDescription = "联系qq",
                    modifier = Modifier.size(25.dp).align(Alignment.Center),
                )
            }

        }

        Spacer(modifier = Modifier.width(20.dp))

        ElevatedButton(
            modifier = Modifier.size(40.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {}
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.Bilibili,
                    contentDescription = "联系qq",
                    modifier = Modifier.size(25.dp).align(Alignment.Center),
                )
            }

        }
    }
}

@Composable
fun DeveloperInfo() {
    Card(
        modifier = Modifier.padding(horizontal = 14.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {

        Text(
            text = "开发者",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 21.dp).padding(top = 21.dp)
                .padding(bottom = 14.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            headlineContent = {
                Text(
                    text = "小黄头号粉丝应援团",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "小黄头号粉丝应援团",
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            headlineContent = {
                Text(
                    text = "whr1930@outlook.com",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "联系方式",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }
}

@Composable
fun FunctionList() {
    Card(
        modifier = Modifier.padding(horizontal = 14.dp).padding(top = 28.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {

        Text(
            text = "功能介绍",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 21.dp).padding(top = 21.dp)
                .padding(bottom = 14.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            headlineContent = {
                Text(
                    text = "任务管理",
                    style = MaterialTheme.typography.titleSmall
                )
            },
            supportingContent = {
                Text(
                    text = "轻松创建、编辑和删除待办事项，让任务管理更高效",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            headlineContent = {
                Text(
                    text = "日程规划",
                    style = MaterialTheme.typography.titleSmall
                )
            },
            supportingContent = {
                Text(
                    text = "灵活安排日程，提醒您重要的事情，让生活更有序",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            headlineContent = {
                Text(
                    text = "贴心提醒",
                    style = MaterialTheme.typography.titleSmall
                )
            },
            supportingContent = {
                Text(
                    text = "定时提醒，让您不错过任何重要的事情",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            headlineContent = {
                Text(
                    text = "数据同步",
                    style = MaterialTheme.typography.titleSmall
                )
            },
            supportingContent = {
                Text(
                    text = "多设备数据实时同步，随时随地管理您的任务",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )


    }
}

