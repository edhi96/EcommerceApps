package tia.sarwoedhi.ecommerce.domain.order.model.request

data class OrderRequest(
    var product: String? = "",
    var totalAmount: Double = 0.0,
    var orderTime: Long = 0
)