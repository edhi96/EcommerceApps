package tia.sarwoedhi.ecommerce.domain

sealed class DomainWrapper<out T> {
    data class Success<T>(
        val data: T
    ) : DomainWrapper<T>()

    data class Error(
        val statusResponse: String? = null,
    ) : DomainWrapper<Nothing>()
}