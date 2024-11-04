package tia.sarwoedhi.ecommerce.core.repository.auth

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tia.sarwoedhi.ecommerce.domain.auth.repository.AuthRepository

@InstallIn(ViewModelComponent::class)
@Module
abstract class AuthModule {

    @Binds
    @ViewModelScoped
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository


}