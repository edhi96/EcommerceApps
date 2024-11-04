package tia.sarwoedhi.ecommerce.domain.product.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.product.repository.ProductRepository
import javax.inject.Inject


class GetCategoryUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(): DomainWrapper<List<String>> =
        repository.getListCategory()
}