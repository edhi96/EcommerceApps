package tia.sarwoedhi.ecommerce.core.mapper

import tia.sarwoedhi.ecommerce.core.remote.model.auth.request.LoginRequestDto
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.TokenDto
import tia.sarwoedhi.ecommerce.core.remote.model.auth.response.UserDto
import tia.sarwoedhi.ecommerce.domain.auth.model.request.LoginRequest
import tia.sarwoedhi.ecommerce.domain.auth.model.response.TokenEntity
import tia.sarwoedhi.ecommerce.domain.auth.model.response.UserEntity


fun UserDto?.toDomain(): UserEntity {
    return UserEntity(
        email = this?.email.orEmpty(),
        username = this?.username.orEmpty(),
        name = "${this?.name?.firstname.orEmpty()} ${this?.name?.lastname.orEmpty()}",
        phone = this?.phone.orEmpty(),
        city = this?.address?.city.orEmpty(),
        street = "${this?.address?.number} ${this?.address?.street.orEmpty()}"
    )
}

fun LoginRequest.toRequest(): LoginRequestDto {
    return LoginRequestDto(
        password = password,
        username = userName,
    )
}


fun TokenDto?.toDomain() = TokenEntity(
    token = this?.token.orEmpty(),
)

