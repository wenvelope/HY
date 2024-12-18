package com.hys.hy.designsystem.component.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import com.hys.hy.user.usecase.GetUserAvatarUseCase
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class UserAvatarImageState : KoinComponent {
    private val getUserAvatarUseCase: GetUserAvatarUseCase by inject()
    var imageUrl: String
    var imageRequestHeader: NetworkHeaders

    init {
        val avatarParam = runBlocking {
            getUserAvatarUseCase.execute(Unit)
        }
        imageUrl = avatarParam.urlString
        imageRequestHeader =
            NetworkHeaders.Builder()
                .add(avatarParam.tokenName, avatarParam.tokenValue.toString())
                .build()

    }
}

@Composable
fun rememberUserAvatarImageState(): UserAvatarImageState {
    return rememberSaveable{ UserAvatarImageState() }
}

@Composable
fun UserAvatarImage(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    state: UserAvatarImageState = rememberUserAvatarImageState()
) {
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current).data(
                state.imageUrl
            ).httpHeaders(
                state.imageRequestHeader
            ).build(),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
}