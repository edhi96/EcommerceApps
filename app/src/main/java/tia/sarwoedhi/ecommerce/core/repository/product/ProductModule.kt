package tia.sarwoedhi.ecommerce.core.repository.product

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tia.sarwoedhi.ecommerce.domain.product.repository.ProductRepository

@InstallIn(ViewModelComponent::class)
@Module
abstract class ProductModule {

    @Binds
    @ViewModelScoped
    abstract fun provideProductRepository(impl: ProductRepositoryImpl): ProductRepository

}