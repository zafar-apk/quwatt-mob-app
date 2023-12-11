package ui.trips.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tj.ham_safar.app.android.theme.Yellow
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Yellow,
) {

    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                modifier = Modifier.size(13.dp),
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = starsColor
            )
        }

        if (halfStar) {
            Icon(
                modifier = Modifier.size(13.dp),
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = starsColor
            )
        }

        repeat(unfilledStars) {
            Icon(
                modifier = Modifier.size(13.dp),
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}


//@Preview
//@Composable
//fun RatingPreview() {
//    RatingBar(rating = 2.5)
//}
//
//@Preview
//@Composable
//fun TenStarsRatingPreview() {
//    RatingBar(stars = 10, rating = 8.5)
//}
//
//@Preview
//@Composable
//fun RatingPreviewFull() {
//    RatingBar(rating = 5.0)
//}
//
//@Preview
//@Composable
//fun RatingPreviewWorst() {
//    RatingBar(rating = 1.0)
//}
//
//@Preview
//@Composable
//fun RatingPreviewDisabled() {
//    RatingBar(rating = 0.0, starsColor = Color.Gray)
//}