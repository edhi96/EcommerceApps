package tia.sarwoedhi.ecommerce.core.remote.model.product.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("category") val category: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("id") val id: Int = 0,
    @SerialName("image") val image: String = "",
    @SerialName("price") val price: Double = 0.0,
    @SerialName("rating") val rating: Rating = Rating(),
    @SerialName("title") val title: String = ""
)