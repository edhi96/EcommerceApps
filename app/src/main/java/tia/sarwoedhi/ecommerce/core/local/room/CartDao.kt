package tia.sarwoedhi.ecommerce.core.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tia.sarwoedhi.ecommerce.core.local.cart.model.response.TableCart

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCart(data: TableCart)

    @Query("SELECT * FROM tbl_cart")
    fun selectAll(): Flow<List<TableCart>>

    @Query("SELECT * FROM tbl_cart WHERE productId=:productId ")
    suspend fun selectByProductId(productId:Int): TableCart

    @Query("UPDATE tbl_cart SET quantity=:qty WHERE productId=:productId")
    suspend fun updateCart(qty: Int, productId:Int)

    @Query("DELETE FROM tbl_cart WHERE productId=:productId")
    suspend fun deleteCart(productId:Int)

    @Query("DELETE FROM tbl_cart")
    suspend fun deleteAll()

}