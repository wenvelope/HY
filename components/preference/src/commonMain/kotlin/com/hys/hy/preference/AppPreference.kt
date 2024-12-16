package com.hys.hy.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface AppPreference {
    suspend fun getUserTokenValue(): String?
    suspend fun setUserTokenValue(value: String)
    suspend fun isOfflineModeEnabled(): Boolean
    suspend fun setOfflineModeEnabled(value: Boolean)
    suspend fun getUserId(): String
    suspend fun setUserId(value: String)
    suspend fun isFirstUse(): Boolean
    suspend fun setNotFirstUse(isFirstUse: Boolean = false)
    suspend fun clearUserTokenAndUserId()
}

internal class AppPreferenceImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreference {

    private companion object {
        private const val PREFS_TAG_KEY = "HyAppPreference"
        private const val USER_TOKEN = "user_token"
        private const val OFFLINE_MODE = "offline_mode"
        private const val IS_FIRST_USE = "is_first_use"
        private const val USER_ID = "user_id"
    }

    private val userTokenKey = stringPreferencesKey("$PREFS_TAG_KEY.$USER_TOKEN")

    private val offlineModeKey = booleanPreferencesKey("$PREFS_TAG_KEY.$OFFLINE_MODE")

    private val isFirstUseKey = booleanPreferencesKey("$PREFS_TAG_KEY.$IS_FIRST_USE")

    private val userIdKey = stringPreferencesKey("$PREFS_TAG_KEY.$USER_ID")


    override suspend fun getUserTokenValue() = dataStore.data.map {
        it[userTokenKey]
    }.first()

    override suspend fun setUserTokenValue(value: String) {
        dataStore.edit {
            it[userTokenKey] = value
        }
    }

    override suspend fun isOfflineModeEnabled(): Boolean {
        return dataStore.data.map {
            it[offlineModeKey] ?: true
        }.first()
    }

    override suspend fun setOfflineModeEnabled(value: Boolean) {
        dataStore.edit {
            it[offlineModeKey] = value
        }
    }

    override suspend fun getUserId(): String{
        return dataStore.data.map {
            it[userIdKey] ?: "test"
        }.first()
    }

    override suspend fun setUserId(value: String) {
        dataStore.edit {
            it[userIdKey] = value
        }
    }

    override suspend fun isFirstUse(): Boolean {
        return dataStore.data.map {
            it[isFirstUseKey] ?: true
        }.first()
    }

    override suspend fun setNotFirstUse(isFirstUse: Boolean) {
        dataStore.edit {
            it[isFirstUseKey] = isFirstUse
        }
    }

    override suspend fun clearUserTokenAndUserId() {
        dataStore.edit {
            it.remove(userTokenKey)
            it.remove(userIdKey)
        }
    }


}