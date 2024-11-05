package tia.sarwoedhi.ecommerce.core.local.orders.source

import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.local.orders.model.TableOrder

interface OrderLocalDataSource {

    suspend fun insertOrder(data: TableOrder) :  DataResult<Unit>

    fun selectAll(): DataResult<Flow<List<TableOrder>>>

    suspend fun selectOrderById(id: Int): DataResult<TableOrder>

    suspend fun selectLatest(): DataResult<TableOrder>
}