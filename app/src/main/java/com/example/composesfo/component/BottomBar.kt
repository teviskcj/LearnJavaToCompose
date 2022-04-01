package com.example.composesfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesfo.R
import com.example.composesfo.ui.theme.AllButton
import com.example.composesfo.ui.theme.frameColor

@Preview(showBackground = true)
@Composable
fun BottomBar() {
    BottomAppBar(
        backgroundColor = frameColor,
        elevation = 0.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(AllButton)
                    .size(44.dp, 44.dp)


            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_book),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp, 20.dp),
                    tint = frameColor
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_shopping_cart),
            contentDescription = "",
            modifier = Modifier
                .weight(1f)
                .size(20.dp, 20.dp),
            tint = AllButton
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_wallet),
            contentDescription = "",
            modifier = Modifier
                .weight(1f)
                .size(20.dp, 20.dp),
            tint = AllButton
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "",
            modifier = Modifier
                .weight(1f)
                .size(20.dp, 20.dp),
            tint = AllButton
        )
    }
}