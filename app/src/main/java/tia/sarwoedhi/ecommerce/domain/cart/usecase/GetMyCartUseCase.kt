package tia.sarwoedhi.ecommerce.domain.cart.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.cart.model.response.CartEntity
import tia.sarwoedhi.ecommerce.domain.cart.repository.CartRepository
import javax.inject.Inject

class GetMyCartUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke(): DomainWrapper<List<CartEntity>> = repository.getMyCart()
}