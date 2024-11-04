package tia.sarwoedhi.ecommerce.core.remote.source.cart

import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.remote.model.cart.request.CartRequestDto
import tia.sarwoedhi.ecommerce.core.remote.model.cart.response.CartResponseDto

interface CartNetworkSource {

    suspend fun addToCart(request: CartRequestDto): DataResult<CartResponseDto>

    suspend fun updateMyCart(request: CartRequestDto): DataResult<CartResponseDto>

    suspend fun deleteCart(requestId : Int): DataResult<CartResponseDto>

}
