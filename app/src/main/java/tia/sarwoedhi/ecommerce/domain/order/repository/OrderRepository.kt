package tia.sarwoedhi.ecommerce.domain.order.repository

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.order.model.request.DetailOrderRequest
import tia.sarwoedhi.ecommerce.domain.order.model.request.OrderRequest
import tia.sarwoedhi.ecommerce.domain.order.model.response.OrderEntity

interface OrderRepository {
    suspend fun getOrderDetail(param: DetailOrderRequest): DomainWrapper<OrderEntity>
    suspend fun insertOrder(request: OrderRequest): DomainWrapper<Unit>
    suspend fun getLatestOrder(): DomainWrapper<OrderEntity>

}