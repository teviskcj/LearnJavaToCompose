package com.example.composesfo.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.composesfo.presentation.ui.theme.orange
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun MultiFloatingActionButton(
    fabIcon: ImageBitmap,
    items: List<MultiFabItem>,
    toState: MultiFabState,
    showLabels: Boolean = true,
    stateChanged: (fabstate: MultiFabState) -> Unit,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    val transition: Transition<MultiFabState> = updateTransition(targetState = toState)
    val scale: Float by transition.animateFloat { state ->
        if (state == MultiFabState.EXPANDED) 56f else 0f
    }
    val alpha: Float by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        }
    ) { state ->
        if (state == MultiFabState.EXPANDED) 1f else 0f
    }
    val shadow: Dp by transition.animateDp(
        transitionSpec = {
            tween(durationMillis = 50)
        }
    ) { state ->
        if (state == MultiFabState.EXPANDED) 2.dp else 0.dp
    }
    val rotation: Float by transition.animateFloat { state ->
        if (state == MultiFabState.EXPANDED) 45f else 0f
    }
    Column(horizontalAlignment = Alignment.End) {
        items.forEach { item ->
            MiniFabItem(item, alpha, shadow, scale, showLabels, onFabItemClicked)
            Spacer(modifier = Modifier.height(20.dp))
        }
        FloatingActionButton(
            onClick = {
                stateChanged(
                    if (transition.currentState == MultiFabState.EXPANDED) {
                        MultiFabState.COLLAPSED
                    } else MultiFabState.EXPANDED
                )
            },
            backgroundColor = orange
        ) {
            Icon(
                bitmap = fabIcon,
                contentDescription = null,
                modifier = Modifier
                    .rotate(rotation)
                    .size(25.dp),
                tint = white
            )
        }
    }
}

@Composable
private fun MiniFabItem(
    item: MultiFabItem,
    alpha: Float,
    shadow: Dp,
    scale: Float,
    showLabel: Boolean,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val myColorString = "#1F000000"
    val fabColor = orange
    val shadowColor = Color(myColorString.toColorInt())
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 12.dp)
    ) {
        if (showLabel) {
            Text(
                item.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(animateFloatAsState(alpha).value)
                    .shadow(animateDpAsState(shadow).value)
                    .background(color = MaterialTheme.colors.surface)
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Canvas(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    onClick = { onFabItemClicked(item) },
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        bounded = false,
                        radius = 20.dp,
                        color = MaterialTheme.colors.onSecondary
                    )
                )
        ) {
            drawCircle(
                Color(myColorString.toColorInt()),
                center = Offset(this.center.x + 2f, this.center.y + 7f),
                radius = scale
            )
            drawCircle(color = fabColor, scale)
            drawImage(
                item.icon,
                topLeft = Offset(
                    (this.center.x) - (item.icon.width / 2),
                    (this.center.y) - (item.icon.width / 2)
                ),
                alpha = alpha,
            )
        }
    }
}

@Composable
private fun Minimal(
    item: MultiFabItem,
    alpha: Float,
    scale: Float,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val fabColor = MaterialTheme.colors.secondary
    Canvas(
        modifier = Modifier
            .size(32.dp)
            .clickable(
                onClick = { onFabItemClicked(item) },
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 20.dp,
                    color = MaterialTheme.colors.onSecondary
                )
            )
    ) {
        drawCircle(color = fabColor, scale)
        drawImage(
            item.icon,
            topLeft = Offset(
                (this.center.x) - (item.icon.width / 2),
                (this.center.y) - (item.icon.width / 2)
            ),
            alpha = alpha
        )
    }
}