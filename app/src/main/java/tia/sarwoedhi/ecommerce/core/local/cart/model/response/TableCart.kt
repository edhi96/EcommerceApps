package tia.sarwoedhi.ecommerce.core.local.cart.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_cart")
data class TableCart(
    @ColumnInfo(ID) @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(PRODUCTID)
    val productId: Int,
    @ColumnInfo(CATEGORY)
    val category: String,
    @ColumnInfo(IMAGE)
    val image: String?,
    @ColumnInfo(TITLE)
    val title: String,
    @ColumnInfo(PRICE)
    val price: Double,
    @ColumnInfo(QUANTITY)
    val quantity: Int,
) {
    companion object {
        const val ID = "id"
        const val CATEGORY = "CATEGORY"
        const val PRICE = "PRICE"
        const val QUANTITY = "QUANTITY"
        const val PRODUCTID = "PRODUCTID"
        const val IMAGE = "IMAGE"
        const val TITLE = "TITLE"
    }
}