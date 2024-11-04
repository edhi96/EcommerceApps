package tia.sarwoedhi.ecommerce.core.remote.source.product

import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.remote.model.product.request.ProductDetailParam
import tia.sarwoedhi.ecommerce.core.remote.model.product.request.ProductParam
import tia.sarwoedhi.ecommerce.core.remote.model.product.response.ProductDto

interface ProductNetworkSource {
    suspend fun getListCategory(): DataResult<List<String>>

    suspend fun getListProduct(): DataResult<List<ProductDto>>

    suspend fun getProductDetail(param: ProductDetailParam): DataResult<ProductDto>
}
