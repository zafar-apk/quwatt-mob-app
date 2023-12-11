package tj.ham_safar.app.android.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .clickable { onClick() }
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
                clip = true
            )
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp),
        imageVector = icon,
        contentDescription = contentDescription,
    )
}