package tia.sarwoedhi.ecommerce.domain.auth.model.response

data class UserEntity(
    val email: String = "",
    val username: String = "",
    val name: String = "",
    val phone: String = "",
    val city : String = "",
    val street : String = ""
)