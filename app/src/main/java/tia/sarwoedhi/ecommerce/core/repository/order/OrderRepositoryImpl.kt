package tia.sarwoedhi.ecommerce.core.repository.order

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.dispatcher.IoDispatcher
import tia.sarwoedhi.ecommerce.core.local.orders.source.OrderLocalDataSource
import tia.sarwoedhi.ecommerce.core.mapper.order.toDomain
import tia.sarwoedhi.ecommerce.core.mapper.order.toTableOrder
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.order.model.request.DetailOrderRequest
import tia.sarwoedhi.ecommerce.domain.order.model.request.OrderRequest
import tia.sarwoedhi.ecommerce.domain.order.model.response.OrderEntity
import tia.sarwoedhi.ecommerce.domain.order.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localDataSource: OrderLocalDataSource
) : OrderRepository {

    override suspend fun getOrderDetail(param: DetailOrderRequest): DomainWrapper<OrderEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (val result = localDataSource.selectOrderById(param.id)) {
                    is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                    is DataResult.Success -> {
                        DomainWrapper.Success(result.data.toDomain())
                    }
                }
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }

    override suspend fun insertOrder(request: OrderRequest): DomainWrapper<Unit> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (val result =
                    localDataSource.insertOrder(request.toTableOrder())
                ) {
                    is DataResult.Error -> return@withContext DomainWrapper.Error(result.exception?.message)
                    is DataResult.Success -> {
                        DomainWrapper.Success(Unit)
                    }
                }
            } catch (e: Exception) {
                return@withContext DomainWrapper.Error(e.localizedMessage)
            }
        }

    override suspend fun getLatestOrder(): DomainWrapper<OrderEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (val result = localDataSource.selectLatest()) {
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

