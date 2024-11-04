package tia.sarwoedhi.ecommerce.domain.product.usecase

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.product.model.request.ProductDetailReq
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity
import tia.sarwoedhi.ecommerce.domain.product.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(param: ProductDetailReq): DomainWrapper<ProductEntity> =
        repository.getProductDetail(param)
}