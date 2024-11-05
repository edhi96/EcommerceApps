package tia.sarwoedhi.ecommerce.domain.cart.repository

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartRequestEntity
import tia.sarwoedhi.ecommerce.domain.cart.model.response.CartEntity

interface CartRepository {

    suspend fun getMyCart(): DomainWrapper<List<CartEntity>>

    suspend fun addToCart(request: CartRequestEntity): DomainWrapper<Unit>

    suspend fun updateMyCart(request: CartRequestEntity): DomainWrapper<Unit>

    suspend fun deleteCart(request: CartRequestEntity): DomainWrapper<Unit>

    suspend fun getCartByProductId(productId : Int): DomainWrapper<CartEntity>

    suspend fun deleteAllCart(): DomainWrapper<Unit>
}