package tia.sarwoedhi.ecommerce.core.mapper.order

import kotlinx.serialization.json.Json
import tia.sarwoedhi.ecommerce.core.local.orders.model.TableOrder
import tia.sarwoedhi.ecommerce.domain.order.model.request.OrderRequest
import tia.sarwoedhi.ecommerce.domain.order.model.response.OrderEntity

fun OrderRequest?.toTableOrder() = TableOrder(
    products = this?.product.orEmpty(),

    orderTime = this?.orderTime ?: 0L,
    totalAmount = this?.totalAmount ?: 0.0
)

fun TableOrder?.toDomain() = OrderEntity(
    products = this?.products,
    productOrder = if(this?.products?.isNotEmpty() == true) Json.decodeFromString(this.products) else listOf(),
    orderTime = this?.orderTime ?: 0L,
    totalAmount = this?.totalAmount ?: 0.0
)