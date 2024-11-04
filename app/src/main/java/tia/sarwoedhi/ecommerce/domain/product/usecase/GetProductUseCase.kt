package tia.sarwoedhi.ecommerce.domain.product.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity
import tia.sarwoedhi.ecommerce.domain.product.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(): DomainWrapper<List<ProductEntity>> = repository.getListProduct()
}