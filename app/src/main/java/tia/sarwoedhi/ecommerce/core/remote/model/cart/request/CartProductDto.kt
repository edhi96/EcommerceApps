package tia.sarwoedhi.ecommerce.core.remote.model.cart.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartProductDto(
    @SerialName("productId") val productId: Int = 0,
    @SerialName("quantity") val quantity: Int = 0
)