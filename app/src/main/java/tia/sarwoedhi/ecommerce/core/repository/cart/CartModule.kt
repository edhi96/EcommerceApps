package tia.sarwoedhi.ecommerce.core.repository.cart

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tia.sarwoedhi.ecommerce.domain.cart.repository.CartRepository

@InstallIn(ViewModelComponent::class)
@Module
abstract class CartModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsCartRepository(impl: CartRepositoryImpl): CartRepository
}