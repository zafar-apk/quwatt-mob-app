package ui.root

import hamsafar_root.HamsafarRootViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val RootModule = module {

    factoryOf(::HamsafarRootViewModel)
}