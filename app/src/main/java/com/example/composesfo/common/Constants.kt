package com.example.composesfo.common

import android.net.Uri

object Constants {
    //const val BASE_URL = "https://mobilefoodordering-74426-default-rtdb.firebaseio.com/"
    const val BASE_URL = "https://learnjavatocompose-default-rtdb.firebaseio.com/"
    const val URL_FOODS = "Foods/.json"
    const val URL_FOOD = "Foods/{foodId}.json"
    const val URL_FOOD_CATEGORIES = "Food_Categories/.json"
    const val URL_FOOD_CATEGORY = "Food_Categories/{categoryId}.json"


    const val PARAM_USER_ID = "userId"
    const val PARAM_FOOD_ID = "foodId"
    const val PARAM_ORDER_ID = "orderId"
    const val PARAM_CATEGORY_ID = "categoryId"
    val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")
}