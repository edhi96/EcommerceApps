package tia.sarwoedhi.ecommerce.core.remote.source.product

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ProductSourceModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkSource(impl: ProductNetworkSourceImpl): ProductNetworkSource {
        return impl
    }

}