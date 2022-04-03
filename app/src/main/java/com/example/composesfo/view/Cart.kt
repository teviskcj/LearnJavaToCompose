package com.example.composesfo.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.composesfo.ui.theme.AllButton

@Preview(showBackground = true)
@Composable
fun CartScreen() {
    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            constraintSet = cartScreenConstraintSet()
        ) {
            Text(
                text = "Total Price = 0.00 MYR",
                fontSize = 30.sp,
                modifier = Modifier.layoutId("totalText")
            )

            Button(
                onClick = {  },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("orderButton")
            ) {
                Text(
                    text = "Place Order",
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
fun cartItemCard(foodName: String = "Food Name", quantity: Int =0, price: Int =0) {
        Card(backgroundColor = AllButton, 
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .clickable {  }
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = foodName)
                    Text(text = quantity.toString())
                }
                Text(text = price.toString())
            }

            
        }
}

private fun cartScreenConstraintSet(): ConstraintSet {
    return ConstraintSet() {
        val totalText =  createRefFor("totalText")
        val orderButton = createRefFor("orderButton")

        constrain(totalText) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(parent)
        }

        constrain(orderButton) {
            bottom.linkTo(parent.bottom,20.dp)
            start.linkTo(parent.start, 30.dp)
            end.linkTo(parent.end, 30.dp)
            centerHorizontallyTo(parent)
        }
    }
}
