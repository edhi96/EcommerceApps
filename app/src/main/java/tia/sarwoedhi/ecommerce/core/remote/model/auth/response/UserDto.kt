package tia.sarwoedhi.ecommerce.core.remote.model.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("address") val address: Address = Address(),
    @SerialName("email") val email: String = "",
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: Name = Name(),
    @SerialName("password") val password: String = "",
    @SerialName("phone") val phone: String = "",
    @SerialName("username") val username: String = "",
    @SerialName("__v") val v: Int = 0
)