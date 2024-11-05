package tia.sarwoedhi.ecommerce.domain.cart.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.cart.repository.CartRepository
import javax.inject.Inject

class DeleteAllCartUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke(): DomainWrapper<Unit> =
        repository.deleteAllCart()
}