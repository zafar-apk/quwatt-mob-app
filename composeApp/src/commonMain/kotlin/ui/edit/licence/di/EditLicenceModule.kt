package ui.edit.licence.di

import edit.licence.domain.EditLicenceUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val EditLicenceModule = module {

    factoryOf(::EditLicenceUseCase)
}