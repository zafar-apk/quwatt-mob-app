package presentatition

sealed class SplashScreenEvent {
    object GoToAuthZone : SplashScreenEvent()
    object GoToNonAuthZone : SplashScreenEvent()
}
