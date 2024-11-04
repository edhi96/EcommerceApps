package tia.sarwoedhi.ecommerce.domain.cart.model.response

data class CartEntity(
    val productId: Int? = 0,
    val category: String? = "",
    val image: String? = "",
    val title: String? = "",
    val price: Double? = 0.0,
    val quantity: Int? = 0,
)
