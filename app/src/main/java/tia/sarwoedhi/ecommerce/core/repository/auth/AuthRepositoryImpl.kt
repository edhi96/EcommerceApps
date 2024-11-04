package tia.sarwoedhi.ecommerce.core.repository.auth

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.dispatcher.IoDispatcher
import tia.sarwoedhi.ecommerce.core.local.data_store_local.DataStoreInterface
import tia.sarwoedhi.ecommerce.core.mapper.user.toDomain
import tia.sarwoedhi.ecommerce.core.mapper.user.toRequest
import tia.sarwoedhi.ecommerce.core.remote.source.auth.AuthNetworkSource
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.auth.model.request.LoginRequest
import tia.sarwoedhi.ecommerce.domain.auth.model.response.TokenEntity
import tia.sarwoedhi.ecommerce.domain.auth.model.response.UserEntity
import tia.sarwoedhi.ecommerce.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreInterface,
    private val authNetworkSource: AuthNetworkSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): DomainWrapper<TokenEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                val dataUsers = async {
                    when (val result = authNetworkSource.userDto()) {
                        is DataResult.Error -> emptyList()
                        is DataResult.Success -> result.data
                    }
                }.await()

                when (val result = authNetworkSource.postLogin(loginRequest.toRequest())) {
                    is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                    is DataResult.Success -> {
                            if (dataUsers?.isNotEmpty() == true && dataUsers.find { it.password.lowercase() == loginRequest.password.lowercase() } != null) {
                                val user = dataUsers.find { data -> data.password.lowercase() == loginRequest.password.lowercase() }
                                user?.let {
                                    dataStore.saveInfoUser(it)
                                }
                            }
                        dataStore.saveTokenKey(result.data?.token.orEmpty())
                        DomainWrapper.Success(result.data.toDomain())
                    }
                }
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }

    override suspend fun getUser(): DomainWrapper<UserEntity> {
        val user = dataStore.getInfoUser().firstOrNull()
        return if(user != null){
            DomainWrapper.Success(user.toDomain())
        }else{
            DomainWrapper.Error("Empty")
        }
    }

    override suspend fun getIsLoggedIn(): DomainWrapper<Boolean> {
        val token = dataStore.getTokenKey().firstOrNull() ?: ""
        return if (token.isNotEmpty()) {
            DomainWrapper.Success(true)
        } else {
            DomainWrapper.Success(false)
        }
    }

}