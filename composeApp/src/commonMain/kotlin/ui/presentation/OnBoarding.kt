@file:OptIn(ExperimentalAnimationApi::class)

package ui.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import tj.ham_safar.app.android.on_boarding.presentation.components.OnBoardingProgressView
import ui.theme.Primary
import ui.presentation.components.OnBoardingButton
import ui.presentation.components.SkipButton

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OnBoarding(
    onOpenNextScreen: () -> Unit,
) {
    var stageState by rememberSaveable {
        mutableStateOf(1)
    }

    AnimatedContent(
        targetState = stageState,
        transitionSpec = {
            slideInHorizontally(
                animationSpec = tween(350),
                initialOffsetX = { fullWidth -> fullWidth }
            ) with slideOutHorizontally(
                animationSpec = tween(350),
                targetOffsetX = { fullWidth -> -fullWidth }
            )
        },
    ) { targetState ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Primary),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1F))
                    SkipButton(onClick = onOpenNextScreen)
                    Spacer(modifier = Modifier.size(26.dp))
                }
            }
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(getOnBoardingTitleResByStage(targetState)),
                    color = Color.White,
                    style = MaterialTheme.typography.h1
                )
            }
            item {
                Image(
                    painter = painterResource(
                        getIllustrationVectorResByStage(
                            targetState
                        )
                    ),
                    contentDescription = stringResource(
                        id = getIllustrationDescriptionResByStage(
                            targetState
                        )
                    )
                )
            }
            item {
                OnBoardingProgressView(stage = targetState)
            }
            item {
                OnBoardingButton(
                    onClick = {
                        if (targetState < MAX_STAGE) stageState++
                        else onOpenNextScreen()
                    },
                    labelRes = "next",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp)
                )
            }
        }
    }
}

private const val MAX_STAGE = 3

fun getIllustrationDescriptionResByStage(stage: Int): String = when (stage) {
    1 -> "waiting_illustration"
    2 -> "search_illustration"
    else -> "security_illustration"
}

fun getIllustrationVectorResByStage(stage: Int): String = when (stage) {
    1 -> "illustration_waiting.xml"
    2 -> "illustration_search.xml"
    else -> "illustration_security.xml"
}

private fun getOnBoardingTitleResByStage(stage: Int): String = when (stage) {
    1 -> "onboarding_1_text"
    2 -> "onboarding_2_text"
    else  -> "onboarding_3_text"
}

//@Preview
//@Composable
//private fun OnBoardingPreview() {
//    HamSafarTheme {
//        OnBoarding(
//            onOpenNextScreen = {},
//        )
//    }
//}