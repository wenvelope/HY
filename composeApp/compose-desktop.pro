-dontwarn kotlinx.datetime.**

# OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# Conscrypt
-keep class org.conscrypt.** { *; }
-dontwarn org.conscrypt.**

# BouncyCastle
-keep class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**

# Android classes
-keep class android.security.** { *; }
-keep class android.os.Build { *; }
-keep class android.net.ssl.** { *; }
-keep class android.net.http.X509TrustManagerExtensions { *; }
-keep class android.util.Log { *; }
-dontwarn android.**

# Kotlin serialization
-keep class kotlinx.serialization.** { *; }
-keep @kotlinx.serialization.Serializable class * { *; }

# 保留 Koin 的所有类和成员
-keep class org.koin.** { *; }

# 保留 Room 相关类
-keep class androidx.room.** { *; }
-keep class com.hys.hy.database.HyDatabase { *; }
-keep class com.hys.hy.database.dao.TaskDao { *; }


# 保留你的 ViewModel 类
-keep class com.hys.hy.login.viewmodel.SignInScreenViewModel { *; }
-keep class com.hys.hy.home.viewmodel.HomeScreenViewModel { *; }

# 保留通过构造函数注入的成员
-keepclassmembers class com.hys.hy.login.viewmodel.SignInScreenViewModel {
    public <init>(...);
}

-keep class androidx.compose.runtime.** { *; }
-keep class androidx.collection.** { *; }
-keep class androidx.lifecycle.** { *; }
#-keep class androidx.compose.ui.text.platform.ReflectionUtil { *; }

# We're excluding Material 2 from the project as we're using Material 3
-dontwarn androidx.compose.material.**

# Kotlinx coroutines rules seems to be outdated with the latest version of Kotlin and Proguard
-keep class kotlinx.coroutines.** { *; }
#----------------------------------------------------------------------------
