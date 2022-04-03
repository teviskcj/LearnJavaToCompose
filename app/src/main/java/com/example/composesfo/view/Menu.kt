package com.example.composesfo.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.composesfo.ui.theme.AllButton

@Preview(showBackground = true)
@Composable
fun MenuScreen() {
    val foodTypeList = foodType()

    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
            constraintSet = menuScreenConstraintSet()
        ) {
            Text(
                text = "Food Menu",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.layoutId("menuText")
            )

            LazyRow(
                modifier = Modifier.layoutId("typeList"),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = foodTypeList) { item ->
                    Text(text = item, modifier = Modifier
                        .clickable {}
                        .padding(start = 10.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
                        .border(1.dp, AllButton)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.layoutId("foodList")
            ) {

            }
        }
    }
}

private fun foodType(): List<String> {
    return listOf("Popular", "Food", "Drink")
}

private fun menuScreenConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val menuText = createRefFor("menuText")
        val typeList = createRefFor("typeList")
        val foodList = createRefFor("foodList")

        constrain(menuText) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(parent)
        }

        constrain(typeList) {
            top.linkTo(menuText.bottom, 20.dp)
            width = Dimension.fillToConstraints
        }

        constrain(foodList) {
            top.linkTo(foodList.bottom)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
        }
    }
}