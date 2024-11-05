package tia.sarwoedhi.ecommerce.core.local.orders.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_order")
data class TableOrder(
    @ColumnInfo(ID) @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(PRODUCT_ORDER)
    val products: String,
    @ColumnInfo(TOTAL_AMOUNT)
    val totalAmount: Double,
    @ColumnInfo(ORDER_TIME)
    val orderTime: Long,
) {
    companion object {
        const val ID = "id"
        const val PRODUCT_ORDER = "PRODUCT_ORDER"
        const val ORDER_TIME = "ORDER_TIME"
        const val TOTAL_AMOUNT = "TOTAL_AMOUNT"
    }
}