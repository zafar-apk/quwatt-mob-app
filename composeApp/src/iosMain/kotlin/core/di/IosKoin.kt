package core.di

import core.data.local.IosDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataStoreModule: Module = module {

    single { IosDataStore().getDataStore() }
}