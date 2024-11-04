package tia.sarwoedhi.ecommerce.core.remote.model.cart.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartRequestDto(
    @SerialName("date") val date: String = "",
    @SerialName("products") val products: List<CartProductDto> = listOf(),
    @SerialName("userId") val userId: Int = 0
)