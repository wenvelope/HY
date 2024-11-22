package com.hys.hy.widget

interface UpdateWidget {
    suspend fun updateWidget()
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class UpdateWidgetImpl(){
    companion object:UpdateWidget{
        override suspend fun updateWidget()
    }

}