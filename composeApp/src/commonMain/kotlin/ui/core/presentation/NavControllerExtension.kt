package ui.core.presentation

import moe.tlaster.precompose.navigation.Navigator

//
//fun <T> NavController.getResultFromCurrentBackStack(key: String): LiveData<T>? =
//    currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
//
//fun <T> Navigator.getResultFromPreviousBackStack(key: String): LiveData<T>? =
//    this?.savedStateHandle?.getLiveData<T>(key)

fun <T> Navigator.setResultToPreviousBackStack(key: String, result: T) {
//    previousBackStackEntry
//        ?.savedStateHandle
//        ?.set(key, result)
}

fun <T> Navigator.setResultToCurrentBackStack(key: String, result: T) {
//    currentBackStackEntry
//        ?.savedStateHandle
//        ?.set(key, result)
}