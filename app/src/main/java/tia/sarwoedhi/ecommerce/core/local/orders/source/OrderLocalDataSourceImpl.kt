package tia.sarwoedhi.ecommerce.core.local.orders.source

import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.local.orders.model.TableOrder
import tia.sarwoedhi.ecommerce.core.local.room.OrdersDao
import javax.inject.Inject

class OrderLocalDataSourceImpl
@Inject constructor(private var dao: OrdersDao) : OrderLocalDataSource {

    override suspend fun insertOrder(data: TableOrder): DataResult<Unit> {
        return try {
            dao.insert(data)
            return DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }

    override fun selectAll(): DataResult<Flow<List<TableOrder>>> {
        return try {
            val result = dao.selectAll()
            return DataResult.Success(result)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }


    override suspend fun selectOrderById(id: Int): DataResult<TableOrder> {
        return try {
            val result = dao.selectOrderById(id)
            return DataResult.Success(result)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }

    override suspend fun selectLatest(): DataResult<TableOrder> {
        return try {
            val result = dao.selectLatest()
            return DataResult.Success(result)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }

}