package tia.sarwoedhi.ecommerce.core.local.cart.source

import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.local.cart.model.response.TableCart
import tia.sarwoedhi.ecommerce.core.local.room.CartDao
import javax.inject.Inject

class CartLocalDataSourceImpl
@Inject constructor(private var dao: CartDao) : CartLocalDataSource {

    override suspend fun insertCart(data: TableCart): DataResult<Unit> {
        return try {
            dao.insertCart(data)
            return DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }

    override fun selectAll(): DataResult<Flow<List<TableCart>>> {
        return try {
            val result = dao.selectAll()
            return DataResult.Success(result)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }

    override suspend fun updateCart(qty: Int, productId: Int): DataResult<Unit> {
        return try {
            dao.updateCart(qty, productId)
            return DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }

    override suspend fun deleteCart(productId: Int): DataResult<Unit> {
        return try {
            dao.deleteCart(productId)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }

    override suspend fun selectByProductId(productId: Int): DataResult<TableCart> {
        return try {
            val result = dao.selectByProductId(productId)
            return DataResult.Success(result)
        } catch (e: Exception) {
            DataResult.Error(exception = e)
        }
    }

}