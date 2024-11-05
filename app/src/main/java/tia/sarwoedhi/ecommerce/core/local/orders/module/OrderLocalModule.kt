package tia.sarwoedhi.ecommerce.core.local.orders.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tia.sarwoedhi.ecommerce.core.local.orders.source.OrderLocalDataSource
import tia.sarwoedhi.ecommerce.core.local.orders.source.OrderLocalDataSourceImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class OrderLocalModule {

    @Binds
    @Singleton
    abstract fun bindOrderLocalSource(impl: OrderLocalDataSourceImpl): OrderLocalDataSource
}