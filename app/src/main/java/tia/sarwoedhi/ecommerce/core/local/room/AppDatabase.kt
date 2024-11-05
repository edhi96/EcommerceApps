package tia.sarwoedhi.ecommerce.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import tia.sarwoedhi.ecommerce.core.local.cart.model.response.TableCart
import tia.sarwoedhi.ecommerce.core.local.orders.model.TableOrder
import tia.sarwoedhi.ecommerce.core.local.product.model.response.TableProduct

@Database(
    entities = [
        TableProduct::class,
        TableCart::class,
        TableOrder::class,
    ],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrdersDao
}