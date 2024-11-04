package tia.sarwoedhi.ecommerce.core.local.product.source

import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.local.product.model.response.TableProduct
import tia.sarwoedhi.ecommerce.core.local.room.ProductDao
import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(private var dao: ProductDao) :
    ProductLocalDataSource {

    override fun getListProduct(): Flow<List<TableProduct>> = dao.selectAll()

    override suspend fun getDetailProduct(id: Int): TableProduct = dao.detailProduct(id)

    override suspend fun insertAllProduct(data: List<TableProduct>) = dao.insertAllProduct(data)

}