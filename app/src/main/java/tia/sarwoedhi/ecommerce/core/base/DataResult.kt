package tia.sarwoedhi.ecommerce.core.base

sealed interface DataResult<out R> {
    data class Success<out T>(val data: T?) : DataResult<T>
    data class Error(
        val httpCode: Int? = null,
        val exception: Exception? = null
    ) : DataResult<Nothing>
}
