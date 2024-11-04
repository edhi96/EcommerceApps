package tia.sarwoedhi.ecommerce.domain.cart.model.request

data class CartRequestEntity(
    val date: String = "",
    val products: List<CartProductEntity> = listOf(),
    val userId: Int = 0
)

data class CartProductEntity(
    val productId: Int = 0,
    val quantity: Int = 0
)