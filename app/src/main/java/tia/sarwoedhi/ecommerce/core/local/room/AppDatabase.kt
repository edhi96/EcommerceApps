package tia.sarwoedhi.ecommerce.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import tia.sarwoedhi.ecommerce.core.local.product.model.TableProduct

@Database(
    entities = [
        TableProduct::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}