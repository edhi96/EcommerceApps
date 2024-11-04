package tia.sarwoedhi.ecommerce.core.remote.source.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tia.sarwoedhi.ecommerce.core.remote.model.auth.request.LoginRequestDto
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.TokenDto
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.UserDto

interface AuthApi {

    @POST("/auth/login")
    suspend fun postLoginUser(@Body request: LoginRequestDto): Response<TokenDto>

    @GET("/users")
    suspend fun getAllUser() : Response<List<UserDto>>
}