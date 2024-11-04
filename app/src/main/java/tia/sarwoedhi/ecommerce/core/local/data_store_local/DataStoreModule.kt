package tia.sarwoedhi.ecommerce.core.local.data_store_local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tia.sarwoedhi.ecommerce.core.local.data_store_local.DataStoreImpl.Companion.PREFERENCE_NAME
import javax.inject.Singleton

val Context.AppDataStore by preferencesDataStore(PREFERENCE_NAME)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.AppDataStore
    }

    @Singleton
    @Provides
    fun bindDataStoreLocal(impl: DataStoreImpl): DataStoreInterface {
        return impl
    }

}