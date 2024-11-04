package tia.sarwoedhi.ecommerce.core.remote.source.auth

import tia.sarwoedhi.ecommerce.core.base.BaseDataSourceImpl
import tia.sarwoedhi.ecommerce.core.base.DataResult
import tia.sarwoedhi.ecommerce.core.remote.model.auth.request.LoginRequestDto
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.TokenDto
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.UserDto
import javax.inject.Inject

class AuthNetworkSourceImpl @Inject constructor(private val api: AuthApi) : BaseDataSourceImpl(),
    AuthNetworkSource {
    override suspend fun postLogin(request: LoginRequestDto): DataResult<TokenDto> {
        return executeWithResponse {
            api.postLoginUser(request)
        }
    }

    override suspend fun userDto(): DataResult<List<UserDto>> {
        return executeWithResponse {
            api.getAllUser()
        }
    }
}