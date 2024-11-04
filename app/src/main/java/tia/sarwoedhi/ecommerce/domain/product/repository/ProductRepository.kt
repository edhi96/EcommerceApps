package tia.sarwoedhi.ecommerce.domain.product.repository

import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.product.model.request.ProductDetailReq
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity

interface ProductRepository {
    suspend fun getListCategory(): DomainWrapper<List<String>>

    suspend fun getListProduct(): DomainWrapper<List<ProductEntity>>

    suspend fun getProductDetail(param: ProductDetailReq): DomainWrapper<ProductEntity>

}