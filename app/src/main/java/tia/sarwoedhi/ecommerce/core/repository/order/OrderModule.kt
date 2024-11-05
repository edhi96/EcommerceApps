package tia.sarwoedhi.ecommerce.core.repository.order

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tia.sarwoedhi.ecommerce.domain.order.repository.OrderRepository

@InstallIn(ViewModelComponent::class)
@Module
abstract class OrderModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsOrderRepository(impl: OrderRepositoryImpl): OrderRepository
}