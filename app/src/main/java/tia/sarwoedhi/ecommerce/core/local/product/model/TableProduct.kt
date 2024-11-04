package tia.sarwoedhi.ecommerce.core.local.product.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_product")
data class TableProduct(
    @ColumnInfo(ID) @PrimaryKey val id: String,
    @ColumnInfo(DESCRIPTION)
    val description: String,
    @ColumnInfo(CATEGORY)
    val category: String,
    @ColumnInfo(IMAGE)
    val image: String?,
    @ColumnInfo(TITLE)
    val title: String,
    @ColumnInfo(PRICE)
    val price: Double,
) {
    companion object {
        const val ID = "id"
        const val CATEGORY = "CATEGORY"
        const val PRICE = "PRICE"
        const val DESCRIPTION = "DESCRIPTION"
        const val IMAGE = "IMAGE"
        const val TITLE = "TITLE"
    }
}