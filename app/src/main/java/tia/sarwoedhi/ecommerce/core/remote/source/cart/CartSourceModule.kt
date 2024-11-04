package tia.sarwoedhi.ecommerce.core.remote.source.cart

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object CartSourceModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): CartApi {
        return retrofit.create(CartApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkSource(impl: CartNetworkSourceImpl): CartNetworkSource {
        return impl
    }

}