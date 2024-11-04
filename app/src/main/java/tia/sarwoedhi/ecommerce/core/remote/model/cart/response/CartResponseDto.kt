package tia.sarwoedhi.ecommerce.core.remote.model.cart.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartResponseDto(
    @SerialName("date") val date: String = "",
    @SerialName("id") val id: Int = 0,
    @SerialName("products") val products: List<ProductResponseDto> = listOf(),
    @SerialName("userId") val userId: Int = 0,
)