package tia.sarwoedhi.ecommerce.core.local.data_store_local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.UserDto
import java.io.IOException
import javax.inject.Inject

class DataStoreImpl @Inject constructor(private val context: Context) : DataStoreInterface {

    companion object {
        const val PREFERENCE_NAME = "tia.sarwoedhi.pratama.preferences"
        const val TOKEN = "token"
        const val UserEntity = "UserEntity"
    }

    override suspend fun saveTokenKey(token: String) {
        context.AppDataStore.edit { preferences ->
            preferences[stringPreferencesKey(TOKEN)] = token
        }
    }

    override suspend fun deleteTokenKey() {
        context.AppDataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(TOKEN))
        }
    }

    override suspend fun getTokenKey(): Flow<String> = flow {
        context.AppDataStore.data.catch { exception ->
            if (exception is IOException) {
                exception.localizedMessage?.let { emit(it) }
            } else {
                emit(exception.message.toString())
            }
        }.collect { value ->
            emit(value[stringPreferencesKey(TOKEN)] ?: "")
        }

    }

    override suspend fun saveInfoUser(userDto: UserDto) {
        context.AppDataStore.edit { preferences ->
            preferences[stringPreferencesKey(UserEntity)] = Json.encodeToString(userDto)
        }
    }

    override suspend fun getInfoUser(): Flow<UserDto> = flow {
        context.AppDataStore.data.collect { value ->
            val userDto = Json.decodeFromString<UserDto>(value[stringPreferencesKey(UserEntity)] ?: "")
            if(userDto.username.isNotEmpty()){
                emit(userDto)
            }else{
                emit(UserDto())
            }
        }
    }
}