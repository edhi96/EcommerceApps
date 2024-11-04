package tia.sarwoedhi.ecommerce.core.remote.source.auth

import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.remote.model.auth.request.LoginRequestDto
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.TokenDto
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.UserDto

interface AuthNetworkSource {
    suspend fun postLogin(request: LoginRequestDto): DataResult<TokenDto>
    suspend fun userDto(): DataResult<List<UserDto>>
}