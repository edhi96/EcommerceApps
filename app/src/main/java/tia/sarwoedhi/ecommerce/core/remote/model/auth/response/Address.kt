package tia.sarwoedhi.ecommerce.core.remote.model.auth.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName("city") val city: String = "",
    @SerialName("geolocation") val geolocation: Geolocation = Geolocation(),
    @SerialName("number") val number: Int = 0,
    @SerialName("street") val street: String = "",
    @SerialName("zipcode") val zipcode: String = ""
)