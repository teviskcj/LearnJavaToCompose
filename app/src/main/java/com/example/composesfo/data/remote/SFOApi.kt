package com.example.composesfo.data.remote

import com.example.composesfo.data.remote.dto.*
import retrofit2.http.*

interface SFOApi {

    @PUT("/Users/{userId}.json")
    suspend fun createUser(@Path("userId") userId: String, @Body userDto: UserDto)

    @GET("/Users/.json")
    suspend fun getUsers(): Map<String, UserDto>

    @GET("/Users/{userId}.json")
    suspend fun getUserById(@Path("userId") userId: String): UserDto

    @PUT("/Users/{userId}/Security Questions.json")
    suspend fun createQuestion(@Path("userId") userId: String, @Body questionDto: QuestionDto)

    @GET("/Users/{userId}/Security Questions.json")
    suspend fun getQuestionById(@Path("userId") userId: String): QuestionDto

    @PUT("/Food_Categories/{categoryId}.json")
    suspend fun createCategory(@Path("categoryId") categoryId: String, @Body foodCategoryDto: FoodCategoryDto)

    @GET("/Food_Categories/.json")
    suspend fun getCategory(): Map<String, FoodCategoryDto>

    /*@GET("/Food_Categories/.json")
    suspend fun getCategory(): Call<Map<String, FoodCategoryDto>>*/

    @GET("/Foods/.json")
    suspend fun getFoods(): Map<String, FoodDto>

    @GET("/Foods/{foodId}.json")
    suspend fun getFoodById(@Path("foodId") foodId: String): FoodDto

    @PUT("/Foods/{foodId}.json")
    suspend fun createFood(@Path("foodId") foodId: String, @Body foodDto: FoodDto)

    @PUT("/Cart List/User View/{userId}/Foods/{foodId}.json")
    suspend fun createCart(@Path("userId") userId: String, @Path("foodId") foodId: String, @Body cartDto: CartDto)

    @PUT("/Cart List/Admin View/{userId}/{orderId}/{foodId}.json")
    suspend fun createCartOrder(@Path("userId") userId: String, @Path("orderId") orderId: String, @Path("foodId") foodId: String, @Body cartDto: CartDto)

    @GET("/Cart List/User View/{userId}/Foods/{foodId}.json")
    suspend fun getFoodFromCart(@Path("userId") userId: String, @Path("foodId") foodId: String): CartDto

    @GET("/Cart List/User View/{userId}/Foods/.json")
    suspend fun getCartList(@Path("userId") userId: String): Map<String, CartDto>

    @GET("/Cart List/Admin View/{userId}/{orderId}/.json")
    suspend fun getCartListFromOrder(@Path("userId") userId: String, @Path("orderId") orderId: String): Map<String, CartDto>

    @PUT("/Orders/{userId}/{orderId}.json")
    suspend fun createOrder(@Path("userId") userId: String, @Path("orderId") orderId: String, @Body orderDto: OrderDto)

    @GET("/Orders/{userId}/.json")
    suspend fun getOrders(@Path("userId") userId: String): Map<String, OrderDto>

    @GET("/Orders/{userId}/{orderId}.json")
    suspend fun getOrderById(@Path("userId") userId: String, @Path("orderId") orderId: String): OrderDto

    @DELETE("/Cart List/User View/{userId}.json")
    suspend fun deleteCartList(@Path("userId") userId: String)

    @DELETE("/Cart List/User View/{userId}/Foods/{foodId}.json")
    suspend fun deleteCartItem(@Path("userId") userId: String, @Path("foodId") foodId: String)

    @GET("/E-Wallet/{userId}.json")
    suspend fun getWalletById(@Path("userId") userId: String): WalletDto

    @PUT("/E-Wallet/{userId}.json")
    suspend fun createWallet(@Path("userId") userId: String, @Body walletDto: WalletDto)

}