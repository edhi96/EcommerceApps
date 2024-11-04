package tia.sarwoedhi.ecommerce.domain.auth.usecase


import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.auth.model.request.LoginRequest
import tia.sarwoedhi.ecommerce.domain.auth.model.response.TokenEntity
import tia.sarwoedhi.ecommerce.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(request: LoginRequest): DomainWrapper<TokenEntity> = repository.login(request)

}