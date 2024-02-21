package ui.stations.details.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import ui.stations.components.RatingBar
import ui.theme.PrimaryGray

@Composable
fun ProfileRatingItem(
    name: String,
    rating: Double,
    profilePainter: Painter
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = PrimaryGray
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = name)
                RatingBar(rating = rating)
            }
            Image(
                modifier = Modifier
                    .size(47.dp),
                painter = profilePainter,
                contentDescription = stringResource(id = "user_photo")
            )
        }
    }
}

//@Preview
//@Composable
//fun ProfileRatingItemPreview() {
//    HamSafarTheme {
//        ProfileRatingItem(
//            name = "Зулайхо Юсупова",
//            rating = 4.2,
//            getImagePainterOrPlaceHolder(
//                photo = "",
//                placeholderResId = R.drawable.ic_user
//            )
//        )
//    }
//}