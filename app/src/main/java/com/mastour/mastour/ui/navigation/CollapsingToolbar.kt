package com.mastour.mastour.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mastour.mastour.R

@Composable
fun ExpandedToolbar (
    title: String,
    picture: Int,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primaryVariant)
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(picture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary, blendMode = BlendMode.Softlight)
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = title,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 8f)
                ),
            )
            IconButton(
                onClick = onBackClicked,
                modifier = modifier
                    .align(Alignment.TopStart)
                    .padding(2.dp)

            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = modifier.shadow(
                        elevation = 4.dp
                    )
                )
            }
        }
}

@Composable
fun CollapsedTopBar(
    title: String,
    modifier: Modifier = Modifier,
    isCollapsed: Boolean
) {
    val color: Color by animateColorAsState(
        if (isCollapsed) MaterialTheme.colors.background else Color.Transparent
    )

    Box(
        modifier = modifier
            .background(color)
            .fillMaxWidth()
            .height(56.dp)
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AnimatedVisibility(visible = isCollapsed) {
            Text(text = title, style = MaterialTheme.typography.h6)
        }
    }
}

@Preview
@Composable
private fun CollapsedTopBarPreview() {
    Column {
        CollapsedTopBar(title = "Makassar", isCollapsed = true)
        Spacer(Modifier.height(16.dp))
        CollapsedTopBar(title = "Makassar", isCollapsed = false)
    }
}

@Preview
@Composable
private fun ExpandedTopBarPreview() {
    ExpandedToolbar(title = "Makassar", picture = R.drawable.makassar, onBackClicked = {})
}