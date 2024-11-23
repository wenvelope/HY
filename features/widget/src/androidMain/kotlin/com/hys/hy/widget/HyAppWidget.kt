package com.hys.hy.widget

import android.content.ComponentName
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.hys.hy.task.entities.Task
import com.hys.hy.task.usecase.ChangeTaskIsDoneUseCase
import com.hys.hy.task.usecase.GetCurrentDayTasksUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.mp.KoinPlatform
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration


class HyAppWidget : GlanceAppWidget(), KoinComponent {

    companion object {
        private val SMALL_BOX = DpSize(90.dp, 90.dp)
        private val BIG_BOX = DpSize(180.dp, 180.dp)
        private val ROW = DpSize(180.dp, 48.dp)
        private val LARGE_ROW = DpSize(300.dp, 48.dp)
        private val COLUMN = DpSize(48.dp, 180.dp)
        private val LARGE_COLUMN = DpSize(48.dp, 300.dp)


        suspend fun updateWidget(context: Context = KoinPlatform.getKoin().get<Context>()) {
            val glanceAppWidgetManager = GlanceAppWidgetManager(context)
            val glanceIds = glanceAppWidgetManager.getGlanceIds(HyAppWidget::class.java)
            glanceIds.forEach { glanceId ->
                HyAppWidget().update(context, glanceId)
            }
        }
    }


    override val sizeMode = SizeMode.Responsive(
        setOf(SMALL_BOX, BIG_BOX, ROW, LARGE_ROW, COLUMN, LARGE_COLUMN)
    )


    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val getUseCase: GetCurrentDayTasksUseCase by inject()
        val changeUseCase: ChangeTaskIsDoneUseCase by inject()

        val tasksFlow = getUseCase.executeFlow(GetCurrentDayTasksUseCase.Param("test")).map {
            it.filter { task ->
                task.taskSelectTime != null
            }
        }
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "HyAppWidgetWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequest.Builder(
                HyWidgetWorker::class.java,
                5.seconds.toJavaDuration()
            ).setInitialDelay(15.minutes.toJavaDuration()).build()
        )


        provideContent {
            val state by tasksFlow.collectAsState(initial = emptyList())
            val scope = rememberCoroutineScope()
            MyContent(state) {
                scope.launch {
                    changeUseCase.execute(ChangeTaskIsDoneUseCase.Param(it.taskId!!, !it.isDone))
                    updateWidget()
                }
            }
        }
    }

}


@Composable
private fun MyContent(tasks: List<Task> = emptyList(), onTaskClick: (Task) -> Unit = {}) {
    GlanceTheme {
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .padding(10.dp)
                .cornerRadius(
                    14.dp
                )
                .background(GlanceTheme.colors.background)
                .clickable(
                    actionStartActivity(
                        componentName = ComponentName(
                            "com.hys.hy",
                            "com.hys.hy.MainActivity"
                        )
                    )
                ),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "今日任务📅",
                modifier = GlanceModifier.fillMaxWidth(),
                style = TextStyle(
                    color = GlanceTheme.colors.onBackground,
                )
            )
            Spacer(modifier = GlanceModifier.height(8.dp))
            LazyColumn(
                modifier = GlanceModifier.fillMaxSize()
            ) {
                items(
                    count = tasks.size,
                    itemContent = { index ->
                        Column {
                            Row(
                                modifier = GlanceModifier
                                    .fillMaxWidth()
                                    .cornerRadius(14.dp)
                                    .height(32.dp)
                                    .background(tasks[index].color)
                                    .padding(horizontal = 5.dp)
                                    .clickable(
                                        actionStartActivity(
                                            componentName = ComponentName(
                                                "com.hys.hy",
                                                "com.hys.hy.MainActivity"
                                            )
                                        )
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val task = tasks[index]
                                val timeText = if (task.taskSelectTime != null) "${
                                    task.taskSelectTime?.hour.toString().padStart(2, '0')
                                }:${
                                    task.taskSelectTime?.minute.toString().padStart(2, '0')
                                }" else "未定时间"

                                Text(
                                    timeText,
                                    modifier = GlanceModifier.padding(start = 3.dp, end = 10.dp),
                                    maxLines = 1,
                                    style = TextStyle(
                                        color = ColorProvider(Color.White),
                                    )
                                )
                                Text(
                                    text = tasks[index].taskTitle,
                                    maxLines = 1,
                                    style = TextStyle(
                                        color = ColorProvider(Color.White),
                                    )
                                )
                                Spacer(modifier = GlanceModifier.defaultWeight())
                            }
                            Spacer(modifier = GlanceModifier.height(6.dp))
                        }

                    }
                )
            }
        }
    }

}