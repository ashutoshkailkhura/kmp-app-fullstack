package ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ButtonWithIcon(
    label: String,
    icon: ImageVector? = null,
    enable: Boolean,
    onClick: () -> Unit = {}
) {

    TextButton(
        onClick = {
            onClick()
        },
        enabled = enable,
    ) {

        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }

        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))

        Text(label)
    }
}