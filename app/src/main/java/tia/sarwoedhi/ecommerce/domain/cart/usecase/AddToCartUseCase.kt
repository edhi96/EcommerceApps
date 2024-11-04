package tia.sarwoedhi.ecommerce.domain.cart.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartRequestEntity
import tia.sarwoedhi.ecommerce.domain.cart.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke(request: CartRequestEntity): DomainWrapper<Unit> =
        repository.addToCart(request)
}