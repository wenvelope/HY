package com.hys.hy.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SignUpScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            //标题
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge,
                text = "创建你的账户",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(9.dp))


            //第三方登录方式
            SinInFromThirdAccount(
                modifier = Modifier,
                onWechatClick = {},
                onPhoneClick = {}
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Text(
                text = "或者用邮箱登录",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.padding(20.dp))

            var username by remember { mutableStateOf("") }

            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                placeholder = { Text("nickname") }
            )

            Spacer(modifier = Modifier.padding(10.dp))


            with(sharedTransitionScope) {
                EmailAndPwdTextField(
                    modifier = Modifier.sharedBounds(
                        animatedVisibilityScope = animatedContentScope,
                        sharedContentState = sharedTransitionScope.rememberSharedContentState("1")
                    ).fillMaxWidth()
                )
            }


            Spacer(modifier = Modifier.padding(5.dp))

            PrivacyTextLine(
                checked = null,
                onCheckedChange = {}
            )

            Spacer(modifier = Modifier.height(5.dp))

            with(sharedTransitionScope){
                Column (
                    modifier = Modifier.sharedBounds(
                        animatedVisibilityScope = animatedContentScope,
                        sharedContentState = rememberSharedContentState("login")
                    ).padding(bottom = 10.dp)
                ){
                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 20.dp),
                    ) {
                        Text("创建账户")
                    }
                }
            }


        }


    }

}

@Composable
fun PrivacyTextLine(
    checked: Boolean?,
    onCheckedChange: (Boolean) -> Unit
) {
    var checkState by remember { mutableStateOf(false) }

    Row {
        Text(
            "我已经阅读",
            modifier = Modifier.padding(start = 20.dp).align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                "隐私协议",
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Checkbox(
            checked = checked ?: checkState,
            onCheckedChange = {
                checkState = it
                onCheckedChange.invoke(it)
            },
            modifier = Modifier.padding(end = 20.dp)
        )

    }
}
