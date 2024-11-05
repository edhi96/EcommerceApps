package tia.sarwoedhi.ecommerce.domain.order.model.response

import tia.sarwoedhi.ecommerce.domain.cart.model.response.CartEntity

data class OrderEntity(
    var products: String? = "",
    var productOrder : List<CartEntity> = listOf(),
    var totalAmount: Double = 0.0,
    var orderTime: Long = 0
)