package com.hys.hy.login.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hys.hy.login.viewmodel.SignInScreenViewModel
import hy.features.login.generated.resources.Res
import hy.features.login.generated.resources.phone
import hy.features.login.generated.resources.wechat
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SinInFromThirdAccount(
    modifier: Modifier,
    onWechatClick: () -> Unit,
    onPhoneClick: () -> Unit
) {

    Column(modifier = Modifier) {
        // 微信登陆按钮
        Button(
            onClick = onWechatClick,
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 20.dp),
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(Res.drawable.wechat),
                    contentDescription = "使用微信登陆",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)
                )

                Text(
                    text = "使用微信登陆",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.padding(9.dp))

        //手机登陆按钮
        Button(
            onClick = onPhoneClick,
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(Res.drawable.phone),
                    contentDescription = "使用手机登陆",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)
                )

                Text(
                    text = "使用手机登陆",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SignInScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit = {},
    onPhoneClick: () -> Unit,
    onWechatClick: () -> Unit,
    onEmailLogInClick: () -> Unit,
    onForgetPwdClick: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: SignInScreenViewModel = koinViewModel()
) {

    val state by viewModel.container.uiStateFlow.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                with(sharedTransitionScope){
                    // 标题
                    Column(
                        modifier = Modifier.sharedBounds(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = rememberSharedContentState("login_title")
                        )
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.headlineLarge,
                            text = "欢迎回来!",
                            textAlign = TextAlign.Center
                        )
                    }
                }

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

                with(sharedTransitionScope) {
                    EmailAndPwdTextField(
                        modifier = Modifier.sharedBounds(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = sharedTransitionScope.rememberSharedContentState("1")
                        ).fillMaxWidth()
                    )
                }


                Spacer(modifier = Modifier.padding(16.dp))

                with(sharedTransitionScope) {
                    Column(
                        modifier = Modifier.sharedBounds(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = rememberSharedContentState("login")
                        )
                    ) {
                        Button(
                            onClick = {

                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                                .padding(horizontal = 20.dp),
                        ) {
                            Text("登录")
                        }
                    }
                }

                with(sharedTransitionScope) {
                    Column(
                        modifier = Modifier.sharedBounds(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = rememberSharedContentState("login_text")
                        )
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = {

                            },
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        ) {
                            Text(
                                "忘记密码？",
                                textDecoration = TextDecoration.Underline
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "还没有账户？"
                            )
                            TextButton(
                                onClick = onSignUpClick,
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                Text(
                                    "注册",
                                    textDecoration = TextDecoration.Underline,
                                )
                            }
                        }
                    }
                }


            }
        }
    )
}

@Composable
fun EmailAndPwdTextField(modifier: Modifier) {
    Column(modifier = modifier) {
        var email by remember { mutableStateOf("") }

        var password by remember { mutableStateOf("") }


        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            placeholder = { Text("Email Address") }
        )


        Spacer(modifier = Modifier.padding(9.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            placeholder = { Text("Password") }
        )
    }

}