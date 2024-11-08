package tia.sarwoedhi.ecommerce.core.local.product.source

import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.local.product.model.response.TableProduct

interface ProductLocalDataSource {

    fun getListProduct(): Flow<List<TableProduct>>

    suspend fun getDetailProduct(id: Int): TableProduct

    suspend fun insertAllProduct(data: List<TableProduct>)
}