package com.hys.hy.today

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.theme.HYTheme

@Preview
@Composable
fun TabPreview(){
    HYTheme {
        Surface {
            DayScrollableSelector()
        }
    }
}



@Composable
fun DayScrollableSelector() {
    val items = remember {
        listOf(
            "323",
            "323", "323", "323", "323", "323", "323", "323", "323", "323", "323"
        )
    }

    var selectIndex by remember{
        mutableIntStateOf(0)
    }
    ScrollableTabRow(
        selectedTabIndex = 0,
        containerColor = MaterialTheme.colorScheme.background,
        tabs = {
            items.forEachIndexed{ index,item->
                Tab(
                    selected = selectIndex == index,
                    onClick = {
                        selectIndex = index
                    },
                    modifier = Modifier
                        .width(70.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(43.dp)),
                    text = {
                        Text(item)
                    }
                )
            }
        },
        divider = {},
        indicator = {
                tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(
                        tabPositions[selectIndex]
                    ),
                width = 70.dp,
                height = 120.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(43.dp)

            )
        }
    )
}