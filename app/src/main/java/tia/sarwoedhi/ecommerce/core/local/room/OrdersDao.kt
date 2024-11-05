package tia.sarwoedhi.ecommerce.core.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.local.orders.model.TableOrder

@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: TableOrder)

    @Query("SELECT * FROM tbl_order")
    fun selectAll(): Flow<List<TableOrder>>

    @Query("SELECT * FROM tbl_order WHERE id=:id")
    fun selectOrderById(id: Int): TableOrder

    @Query("SELECT * FROM tbl_order ORDER BY id DESC Limit 1")
    fun selectLatest(): TableOrder

}