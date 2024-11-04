package tia.sarwoedhi.ecommerce.domain.auth.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.auth.model.response.UserEntity
import tia.sarwoedhi.ecommerce.domain.auth.repository.AuthRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): DomainWrapper<UserEntity> = repository.getUser()
}