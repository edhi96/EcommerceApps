package tia.sarwoedhi.ecommerce.core.repository.cart

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.dispatcher.IoDispatcher
import tia.sarwoedhi.ecommerce.core.local.cart.model.response.TableCart
import tia.sarwoedhi.ecommerce.core.local.cart.source.CartLocalDataSource
import tia.sarwoedhi.ecommerce.core.local.product.source.ProductLocalDataSource
import tia.sarwoedhi.ecommerce.core.mapper.cart.toCartDomain
import tia.sarwoedhi.ecommerce.core.mapper.cart.toDomain
import tia.sarwoedhi.ecommerce.core.mapper.cart.toDomainProduct
import tia.sarwoedhi.ecommerce.core.mapper.cart.toRequest
import tia.sarwoedhi.ecommerce.core.remote.source.cart.CartNetworkSource
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartRequestEntity
import tia.sarwoedhi.ecommerce.domain.cart.model.response.CartEntity
import tia.sarwoedhi.ecommerce.domain.cart.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject
constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkSource: CartNetworkSource,
    private val localDataSource: CartLocalDataSource,
    private val productDataSource: ProductLocalDataSource
) : CartRepository {
    override suspend fun getMyCart(): DomainWrapper<List<CartEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val dataCart: List<TableCart> = async(ioDispatcher) {
                    when (val resultCart = localDataSource.selectAll()) {
                        is DataResult.Error -> emptyList()
                        is DataResult.Success -> resultCart.data?.first() ?: emptyList()
                    }
                }.await()
                DomainWrapper.Success(dataCart.toDomain() ?: emptyList())
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }

    override suspend fun addToCart(request: CartRequestEntity): DomainWrapper<Unit> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (val result = networkSource.addToCart(request.toRequest())) {
                    is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                    is DataResult.Success -> {
                        if (request.products.isNotEmpty()) {
                            request.products.forEach { productData ->
                                val product =
                                    productDataSource.getDetailProduct(productData.productId)
                                localDataSource.insertCart(product.toDomainProduct(productData.quantity))
                            }
                        }
                        DomainWrapper.Success(Unit)
                    }
                }
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }

    override suspend fun updateMyCart(request: CartRequestEntity): DomainWrapper<Unit> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (val result = networkSource.updateMyCart(request.toRequest())) {
                    is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                    is DataResult.Success -> {
                        if (request.products.isNotEmpty()) {
                            localDataSource.updateCart(
                                request.products.first().quantity,
                                request.products.first().productId
                            )
                        }
                        DomainWrapper.Success(Unit)
                    }
                }
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }

    override suspend fun deleteCart(request: CartRequestEntity): DomainWrapper<Unit> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (val result = networkSource.deleteCart(request.userId)) {
                    is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                    is DataResult.Success -> {
                        if (request.products.isNotEmpty()) {
                            localDataSource.deleteCart(
                                request.products.first().productId
                            )
                        }
                        DomainWrapper.Success(Unit)
                    }
                }
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }

    override suspend fun getCartByProductId(productId : Int) : DomainWrapper<CartEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                val dataCart: TableCart? = async(ioDispatcher) {
                    when (val resultCart = localDataSource.selectByProductId(productId)) {
                        is DataResult.Error -> null
                        is DataResult.Success -> resultCart.data
                    }
                }.await()
                DomainWrapper.Success(dataCart.toCartDomain())
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }
}