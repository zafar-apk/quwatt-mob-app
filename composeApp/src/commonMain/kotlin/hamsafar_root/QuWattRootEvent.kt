package hamsafar_root

sealed class QuWattRootEvent {
    data class OnNewFcmToken(val token: String) : QuWattRootEvent()
    object CheckIfTheFirstLaunch : QuWattRootEvent()
    object SetFirstLaunchFalse : QuWattRootEvent()
}