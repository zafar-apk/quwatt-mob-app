package tj.ham_safar.app.android.core.presentation

import androidx.compose.ui.Modifier

fun Modifier.withIf(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier =
    if (condition) modifier(this) else this

