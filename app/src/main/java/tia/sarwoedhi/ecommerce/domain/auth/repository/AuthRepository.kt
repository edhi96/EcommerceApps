package tia.sarwoedhi.ecommerce.domain.auth.repository

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.auth.model.request.LoginRequest
import tia.sarwoedhi.ecommerce.domain.auth.model.response.TokenEntity
import tia.sarwoedhi.ecommerce.domain.auth.model.response.UserEntity

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): DomainWrapper<TokenEntity>
   suspend fun getUser(): DomainWrapper<UserEntity>
    suspend fun getIsLoggedIn(): DomainWrapper<Boolean>
}