package com.hys.hy.setting.screens


import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.toolbars.NavigationBottomBar
import com.hys.hy.designsystem.component.toolbars.SettingsTabIndex
import com.hys.hy.designsystem.theme.HYTheme
import hy.features.setting.generated.resources.Res
import hy.features.setting.generated.resources.naixv
import org.jetbrains.compose.resources.painterResource


@Preview
@Composable
fun AccountScreenPreview() {
    HYTheme {
        SettingScreen()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "我的",
                        modifier = Modifier.padding(start = 4.dp),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            )
        },
        bottomBar = {
            NavigationBottomBar(
                currentTabIndex = SettingsTabIndex,
                onSettingTabClick = {

                },
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            UserBanner()

        }

    }
}

@Composable
fun UserBanner() {
    Row {
        Image(
            painter = painterResource(Res.drawable.naixv),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(start = 24.dp).size(64.dp).clip(CircleShape)
        )
        Column {
            Text(
                "友利奈绪",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 24.dp),
                textAlign = TextAlign.Start
            )
            Text(
                "wo ai chi pao fu",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 24.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }

    }
}
