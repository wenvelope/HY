package com.hys.hy.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hys.hy.designsystem.component.animation.SwipeToShowActionBox


@Preview(showBackground = true)
@Composable
fun SearchBar() {
    SwipeToShowActionBox(
        modifier = Modifier.fillMaxWidth(),
        startAction = listOf(

        ),
        endAction = listOf(

        )
    ) {
        ListItem(
            headlineContent = {
                Text("Search")
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeToShowActionBox(
            modifier = Modifier.fillMaxWidth(),
            startAction = listOf({
                Text("Start Action 1")
            }, {
                Text("Start Action 2")
            }),
            endAction = listOf(

            )
        ) {
            ListItem(
                headlineContent = {
                    Text("Search")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


}