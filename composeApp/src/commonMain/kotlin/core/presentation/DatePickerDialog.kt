package core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.domain.util.getCurrentDateTime
import core.domain.util.stringResource
import core.domain.util.toYYYYmmDD
import dev.icerock.moko.resources.StringResource
import epicarchitect.calendar.compose.basis.config.rememberMutableBasisEpicCalendarConfig
import epicarchitect.calendar.compose.datepicker.EpicDatePicker
import epicarchitect.calendar.compose.datepicker.config.rememberEpicDatePickerConfig
import epicarchitect.calendar.compose.datepicker.state.rememberEpicDatePickerState
import epicarchitect.calendar.compose.pager.config.rememberEpicCalendarPagerConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Month
import tj.ham_safar.app.android.theme.BackgroundGray
import tj.ham_safar.app.android.theme.Blue
import tj.yakroh.yakrohapp.SharedRes

private const val SHAKE_ANIMATION_DURATION = 250

@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val hapticFeedback = LocalHapticFeedback.current

    val basisConfig = rememberMutableBasisEpicCalendarConfig()
    val state = rememberEpicDatePickerState(
        config = rememberEpicDatePickerConfig(
            pagerConfig = rememberEpicCalendarPagerConfig(
                basisConfig = basisConfig
            ),
            selectionContentColor = MaterialTheme.colors.onPrimary,
            selectionContainerColor = MaterialTheme.colors.primary
        )
    )

    val currentDate = remember {
        getCurrentDateTime().date
    }
    val selectedDate = state.selectedDates.firstOrNull()
    val isSelectedDateValid = selectedDate == null || selectedDate >= currentDate

    var shakeEffect by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition()
    val offsetX: Float by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = getShakeAnimationSpec()
    )

    LaunchedEffect(shakeEffect) {
        if (shakeEffect) {
            coroutineScope.launch {
                delay(SHAKE_ANIMATION_DURATION.toLong())
                shakeEffect = false
            }
        }
    }

    LaunchedEffect(isSelectedDateValid) {
        if (!isSelectedDateValid) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            selectedDate?.let(state::toggleDateSelection)
            shakeEffect = true
        }
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.background(
                color = BackgroundGray,
                RoundedCornerShape(16.dp)
            )
                .padding(16.dp)
                .offset {
                    IntOffset(x = if (shakeEffect) offsetX.toInt() else 0, y = 0)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = getDisplayMonthName(state.pagerState.currentMonth.month),
                    style = MaterialTheme.typography.h3
                )

                Spacer(Modifier.weight(1F))

                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            state.pagerState.scrollMonths(-1)
                        }
                    }
                ) {
                    Text("<", style = MaterialTheme.typography.subtitle2)
                }

                TextButton(onClick = {
                    coroutineScope.launch {
                        state.pagerState.scrollMonths(1)
                    }
                }) {
                    Text(">", style = MaterialTheme.typography.subtitle2)
                }
            }

            Spacer(modifier = Modifier.size(14.dp))

            EpicDatePicker(
                state = state
            )

            AnimatedVisibility(visible = selectedDate != null && isSelectedDateValid) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = selectedDate?.toYYYYmmDD().orEmpty(),
                        style = MaterialTheme.typography.subtitle2,
                    )

                    Spacer(Modifier.weight(1F))

                    TextButton(
                        onClick = {
                            onDateSelected(selectedDate?.toYYYYmmDD().orEmpty())
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = stringResource(SharedRes.strings.select),
                            style = MaterialTheme.typography.subtitle2,
                            color = Blue
                        )
                    }
                }
            }
        }
    }
}

private fun getShakeAnimationSpec() = infiniteRepeatable(
    animation = keyframes {
        durationMillis = SHAKE_ANIMATION_DURATION
        0f at 0 with LinearEasing
        -10f at 50 with LinearEasing
        10f at 100 with LinearEasing
        -10f at 150 with LinearEasing
        10f at 200 with LinearEasing
        0f at 250 with LinearEasing
    },
    repeatMode = RepeatMode.Restart
)

@Composable
private fun getDisplayMonthName(month: Month): String {
    return stringResource(getDisplayMonthRes(month))
}

private fun getDisplayMonthRes(month: Month): StringResource {
    return when (month) {
        Month.JANUARY -> SharedRes.strings.january
        Month.FEBRUARY -> SharedRes.strings.february
        Month.MARCH -> SharedRes.strings.march
        Month.APRIL -> SharedRes.strings.april
        Month.MAY -> SharedRes.strings.may
        Month.JUNE -> SharedRes.strings.june
        Month.JULY -> SharedRes.strings.july
        Month.AUGUST -> SharedRes.strings.august
        Month.SEPTEMBER -> SharedRes.strings.september
        Month.OCTOBER -> SharedRes.strings.october
        Month.NOVEMBER -> SharedRes.strings.november
        Month.DECEMBER -> SharedRes.strings.december
        else -> throw IllegalArgumentException("Passed month is not correct")
    }
}