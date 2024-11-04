package tia.sarwoedhi.ecommerce.core.remote.source.cart

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import tia.sarwoedhi.ecommerce.core.remote.model.cart.request.CartRequestDto
import tia.sarwoedhi.ecommerce.core.remote.model.cart.response.CartResponseDto

interface CartApi {

    @POST("/carts")
    suspend fun addToCart(
        @Body cartRequestDto: CartRequestDto
    ): Response<CartResponseDto>


    @PUT("/carts/1")
    suspend fun updateCart(
        @Body cartRequestDto: CartRequestDto
    ): Response<CartResponseDto>

    @DELETE("/carts/{id}")
    suspend fun deleteCart(
        @Path("id") id: Int
    ): Response<CartResponseDto>

}