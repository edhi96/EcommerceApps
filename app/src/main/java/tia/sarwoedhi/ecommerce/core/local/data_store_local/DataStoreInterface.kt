package tia.sarwoedhi.ecommerce.core.local.data_store_local

import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.UserDto

interface DataStoreInterface {
    suspend fun saveTokenKey(token: String)
    suspend fun deleteTokenKey()
    suspend fun getTokenKey(): Flow<String>
    suspend fun saveInfoUser(userDto: UserDto)
    suspend fun getInfoUser(): Flow<UserDto>
}