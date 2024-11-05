package tia.sarwoedhi.ecommerce.domain.order.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.order.model.request.DetailOrderRequest
import tia.sarwoedhi.ecommerce.domain.order.model.response.OrderEntity
import tia.sarwoedhi.ecommerce.domain.order.repository.OrderRepository
import javax.inject.Inject

class DetailOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(request: DetailOrderRequest): DomainWrapper<OrderEntity> =
        repository.getOrderDetail(request)
}