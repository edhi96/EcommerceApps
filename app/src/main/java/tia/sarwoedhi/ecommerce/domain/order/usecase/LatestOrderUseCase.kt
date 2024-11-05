package tia.sarwoedhi.ecommerce.domain.order.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.order.model.response.OrderEntity
import tia.sarwoedhi.ecommerce.domain.order.repository.OrderRepository
import javax.inject.Inject

class LatestOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(): DomainWrapper<OrderEntity> =
        repository.getLatestOrder()
}