package tia.sarwoedhi.ecommerce.core.remote.model.cart.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    @SerialName("productId") val productId: Int = 0,
    @SerialName("quantity") val quantity: Int = 0
)