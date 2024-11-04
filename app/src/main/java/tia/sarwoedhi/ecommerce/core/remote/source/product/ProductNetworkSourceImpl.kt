package tia.sarwoedhi.ecommerce.core.remote.source.product

import tia.sarwoedhi.ecommerce.core.base.BaseDataSourceImpl
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.remote.model.product.request.ProductDetailParam
import tia.sarwoedhi.ecommerce.core.remote.model.product.request.ProductParam
import tia.sarwoedhi.ecommerce.core.remote.model.product.response.ProductDto
import javax.inject.Inject

class ProductNetworkSourceImpl @Inject constructor(
    private val api: ProductApi
) : BaseDataSourceImpl(), ProductNetworkSource {

    override suspend fun getListCategory(): DataResult<List<String>> {
        return executeWithResponse {
            api.getListCategory()
        }
    }

    override suspend fun getListProduct()
            : DataResult<List<ProductDto>> {
        return executeWithResponse {
            api.getListProduct()
        }
    }

    override suspend fun getProductDetail(param: ProductDetailParam): DataResult<ProductDto> {
        return executeWithResponse {
            api.getProductDetail(id = param.id)
        }
    }


}