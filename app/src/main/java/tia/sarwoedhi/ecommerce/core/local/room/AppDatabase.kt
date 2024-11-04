package tia.sarwoedhi.ecommerce.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import tia.sarwoedhi.ecommerce.core.local.cart.model.response.TableCart
import tia.sarwoedhi.ecommerce.core.local.product.model.response.TableProduct

@Database(
    entities = [
        TableProduct::class,
        TableCart::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}