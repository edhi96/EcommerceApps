package tia.sarwoedhi.ecommerce.core.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.local.product.model.response.TableProduct

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAllProduct(user: List<TableProduct>)

    @Query("SELECT * FROM tbl_product")
    fun selectAll(): Flow<List<TableProduct>>

    @Query("SELECT * FROM tbl_product WHERE id=:id")
    fun detailProduct(id: Int): TableProduct

}