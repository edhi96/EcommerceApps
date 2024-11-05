package tia.sarwoedhi.ecommerce.domain.order.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.order.model.request.OrderRequest
import tia.sarwoedhi.ecommerce.domain.order.repository.OrderRepository
import javax.inject.Inject

class InsertOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(request: OrderRequest): DomainWrapper<Unit> =
        repository.insertOrder(request)
}