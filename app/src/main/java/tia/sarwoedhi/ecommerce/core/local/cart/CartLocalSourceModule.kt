package tia.sarwoedhi.ecommerce.core.local.cart

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tia.sarwoedhi.ecommerce.core.local.cart.source.CartLocalDataSource
import tia.sarwoedhi.ecommerce.core.local.cart.source.CartLocalDataSourceImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CartLocalSourceModule {

    @Binds
    @Singleton
    abstract fun bindCartLocalSource(impl: CartLocalDataSourceImpl): CartLocalDataSource
}