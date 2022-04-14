package com.example.composesfo.data.remote

import com.example.composesfo.data.remote.dto.CartDto
import com.example.composesfo.data.remote.dto.FoodDto
import com.example.composesfo.data.remote.dto.OrderDto
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.domain.model.Cart
import retrofit2.http.*

interface SFOApi {

    @PUT("/Users/{userId}.json")
    suspend fun createUser(@Path("userId") userId: String, @Body userDto: UserDto)

    @GET("/Users/.json")
    suspend fun getUsers(): Map<String, UserDto>

    @GET("/Foods/.json")
    suspend fun getFoods(): Map<String, FoodDto>

    @GET("/Foods/{foodId}.json")
    suspend fun getFoodById(@Path("foodId") foodId: String): FoodDto

    @PUT("/Cart List/User View/{userId}/Foods/{foodId}.json")
    suspend fun createCart(@Path("userId") userId: String, @Path("foodId") foodId: String, @Body cartDto: CartDto)

    @GET("/Cart List/User View/{userId}/Foods/.json")
    suspend fun getCartList(@Path("userId") userId: String): Map<String, CartDto>

    @PUT("/Orders/{userId}/{orderId}.json")
    suspend fun createOrder(@Path("userId") userId: String, @Path("orderId") orderId: String, @Body orderDto: OrderDto)

    @PUT("/Cart List/Admin View/{userId}/{orderId}/{foodId}.json")
    suspend fun createCartOrder(@Path("userId") userId: String, @Path("orderId") orderId: String, @Path("foodId") foodId: String, @Body cartDto: CartDto)

    @DELETE("/Cart List/User View/{userId}.json")
    suspend fun deleteCartList(@Path("userId") userId: String)

}