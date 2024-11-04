package tia.sarwoedhi.ecommerce.core.local.product

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tia.sarwoedhi.ecommerce.core.local.product.source.ProductLocalDataSource
import tia.sarwoedhi.ecommerce.core.local.product.source.ProductLocalDataSourceImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ProductSourceModule {

    @Binds
    @Singleton
    abstract fun bindProductLocalSource(impl: ProductLocalDataSourceImpl): ProductLocalDataSource

}