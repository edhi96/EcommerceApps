package tia.sarwoedhi.ecommerce.core.local.cart.source

import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.local.cart.model.response.TableCart

interface CartLocalDataSource {

    suspend fun insertCart(data: TableCart) :  DataResult<Unit>

    fun selectAll(): DataResult<Flow<List<TableCart>>>

    suspend fun updateCart(qty: Int, productId: Int) : DataResult<Unit>

    suspend fun deleteCart(productId: Int) : DataResult<Unit>

    suspend fun selectByProductId(productId: Int): DataResult<TableCart>
}