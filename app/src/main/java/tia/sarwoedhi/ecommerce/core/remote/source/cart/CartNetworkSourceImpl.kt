package tia.sarwoedhi.ecommerce.core.remote.source.cart

import tia.sarwoedhi.ecommerce.core.base.BaseDataSourceImpl
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.remote.model.cart.request.CartRequestDto
import tia.sarwoedhi.ecommerce.core.remote.model.cart.response.CartResponseDto
import javax.inject.Inject

class CartNetworkSourceImpl @Inject constructor(
    private val api: CartApi
) : BaseDataSourceImpl(), CartNetworkSource {

    override suspend fun addToCart(request: CartRequestDto): DataResult<CartResponseDto> {
        return executeWithResponse {
            api.addToCart(request)
        }
    }

    override suspend fun updateMyCart(request: CartRequestDto): DataResult<CartResponseDto> {
        return executeWithResponse {
            api.updateCart(request)
        }
    }

    override suspend fun deleteCart(requestId: Int): DataResult<CartResponseDto> {
        return executeWithResponse {
            api.deleteCart(requestId)
        }
    }
}