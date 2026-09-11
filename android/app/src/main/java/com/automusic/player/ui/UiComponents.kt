package com.automusic.player.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.automusic.player.ui.theme.Brand
import com.automusic.player.ui.theme.BrandSoft
import com.automusic.player.ui.theme.Ink
import com.automusic.player.ui.theme.Ink2
import com.automusic.player.ui.theme.Ink3
import com.automusic.player.ui.theme.Line
import com.automusic.player.ui.theme.StateSuccess
import com.automusic.player.ui.theme.Surface1
import com.automusic.player.ui.theme.Surface2

@Composable
internal fun PageHeader(
    title: String,
    subtitle: String,
    icon: ImageVector,
    badge: String? = null,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Surface1,
        border = BorderStroke(0.dp, Line),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(color = BrandSoft, shape = RoundedCornerShape(8.dp)) {
                    Box(Modifier.size(42.dp), contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = Brand, modifier = Modifier.size(23.dp))
                    }
                }
                Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                    Text(title, color = Ink, style = MaterialTheme.typography.titleLarge)
                    Text(
                        subtitle,
                        color = Ink2,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                badge?.let {
                    Surface(color = Surface2, shape = RoundedCornerShape(6.dp), border = BorderStroke(1.dp, Line)) {
                        Text(
                            it,
                            color = Brand,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                        )
                    }
                }
            }
            Box(Modifier.fillMaxWidth().height(1.dp).background(Line))
        }
    }
}

@Composable
internal fun WorkflowRail(
    steps: List<String>,
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        steps.forEachIndexed { index, label ->
            val complete = index < currentStep
            val current = index == currentStep
            Surface(
                modifier = Modifier.weight(1f),
                color = if (current) BrandSoft else Surface1,
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, if (current) Brand.copy(alpha = 0.55f) else Line),
            ) {
                Row(
                    Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Surface(
                        color = when {
                            complete -> StateSuccess
                            current -> Brand
                            else -> Surface2
                        },
                        shape = CircleShape,
                    ) {
                        Box(Modifier.size(21.dp), contentAlignment = Alignment.Center) {
                            if (complete) {
                                Icon(Icons.Outlined.Check, null, tint = Surface1, modifier = Modifier.size(13.dp))
                            } else {
                                Text(
                                    "${index + 1}",
                                    color = if (current) Surface1 else Ink3,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                    Spacer(Modifier.size(6.dp))
                    Text(
                        label,
                        color = if (current || complete) Ink else Ink3,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
internal fun SectionHeading(
    number: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(number, color = Brand, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        Column(Modifier.weight(1f).padding(start = 10.dp)) {
            Text(title, color = Ink, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, color = Ink3, style = MaterialTheme.typography.bodySmall)
        }
    }
}
