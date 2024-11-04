package tia.sarwoedhi.ecommerce.core.remote.model.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    @SerialName("token") val token: String = "",
)