package tia.sarwoedhi.ecommerce.core.repository.product

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.dispatcher.IoDispatcher
import tia.sarwoedhi.ecommerce.core.local.product.source.ProductLocalDataSource
import tia.sarwoedhi.ecommerce.core.mapper.product.toDomain
import tia.sarwoedhi.ecommerce.core.mapper.product.toDomainProduct
import tia.sarwoedhi.ecommerce.core.mapper.product.toParam
import tia.sarwoedhi.ecommerce.core.mapper.product.toTableProduct
import tia.sarwoedhi.ecommerce.core.remote.source.product.ProductNetworkSource
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.product.model.request.ProductDetailReq
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity
import tia.sarwoedhi.ecommerce.domain.product.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkSource: ProductNetworkSource,
    private val localDataSource: ProductLocalDataSource
) :
    ProductRepository {
    override suspend fun getListCategory(): DomainWrapper<List<String>> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (val result = networkSource.getListCategory()) {
                    is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                    is DataResult.Success -> {
                        DomainWrapper.Success(result.data ?: emptyList())
                    }
                }
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }

    override suspend fun getListProduct(): DomainWrapper<List<ProductEntity>> =
        withContext(ioDispatcher) {

            return@withContext try {
                val localSourceProduct = localDataSource.getListProduct().first().toDomainProduct()
                if (localSourceProduct?.isEmpty() == true) {
                    when (val result = networkSource.getListProduct()) {
                        is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                        is DataResult.Success -> {
                            localDataSource.insertAllProduct(
                                result.data?.toTableProduct() ?: emptyList()
                            )
                            DomainWrapper.Success(result.data?.toDomain() ?: emptyList())
                        }
                    }
                } else {
                    DomainWrapper.Success(localSourceProduct ?: emptyList())
                }

            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }


    override suspend fun getProductDetail(param: ProductDetailReq): DomainWrapper<ProductEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (val result = networkSource.getProductDetail(param.toParam())) {
                    is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                    is DataResult.Success -> {
                        DomainWrapper.Success(result.data.toDomain())
                    }
                }
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }
}

