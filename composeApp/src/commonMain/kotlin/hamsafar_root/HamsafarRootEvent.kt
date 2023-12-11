package hamsafar_root

sealed class HamsafarRootEvent {
    object CheckIfTheFirstLaunch : HamsafarRootEvent()
    object SetFirstLaunchFalse : HamsafarRootEvent()
}