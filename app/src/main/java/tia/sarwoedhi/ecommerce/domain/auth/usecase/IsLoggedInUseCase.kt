package tia.sarwoedhi.ecommerce.domain.auth.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.auth.repository.AuthRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): DomainWrapper<Boolean> = repository.getIsLoggedIn()
}