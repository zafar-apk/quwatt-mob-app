package hamsafar_root

sealed class HamsafarRootEvent {
    data class OnNewFcmToken(val token: String) : HamsafarRootEvent()
    object CheckIfTheFirstLaunch : HamsafarRootEvent()
    object SetFirstLaunchFalse : HamsafarRootEvent()
}