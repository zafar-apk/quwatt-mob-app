package user.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserLocalDataSource(private val dataStore: DataStore<Preferences>) {

    private companion object {

        val KEY_TOKEN = stringPreferencesKey("key::SecureString")
        val KEY_NOTIFICATION_TOKEN = stringPreferencesKey("keyNotification::SecureString")
        val KEY_REGISTER_ID = stringPreferencesKey("keyRegisterId::SecureString")
        val KEY_IS_USER_EXIST = booleanPreferencesKey("userExist::SecureString")
        val KEY_IS_FIRST_LAUNCH = booleanPreferencesKey("isFirstLaunch::SecureString")
    }

    suspend fun getToken(): String? {
        return dataStore.data.map { preferences ->
            preferences[KEY_TOKEN]
        }.first()
    }

    suspend fun insertToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
        }
    }

    suspend fun getRegisterId(): String? {
        return dataStore.data.map { preferences ->
            preferences[KEY_REGISTER_ID]
        }.first()
    }

    suspend fun insertRegisterId(id: String) {
        dataStore.edit { preferences ->
            preferences[KEY_REGISTER_ID] = id
        }
    }

    suspend fun getNotificationToken(): String? {
        return dataStore.data.map { preferences ->
            preferences[KEY_NOTIFICATION_TOKEN]
        }.first()
    }

    suspend fun insertNotificationToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_NOTIFICATION_TOKEN] = token
        }
    }

    suspend fun setIsUserExist(isUserExist: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_IS_USER_EXIST] = isUserExist
        }
    }

    suspend fun isUserExist(): Boolean {
        return dataStore.data.map {
            it[KEY_IS_USER_EXIST]
        }.first() ?: false
    }

    suspend fun isAuthorized(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun setIsFirstLaunch(isFirst: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_IS_FIRST_LAUNCH] = isFirst
        }
    }

    suspend fun getIsFirstLaunch(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[KEY_IS_FIRST_LAUNCH]
        }.first() ?: true
    }
}