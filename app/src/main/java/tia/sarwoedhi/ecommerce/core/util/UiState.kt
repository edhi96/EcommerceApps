package tia.sarwoedhi.ecommerce.core.util

sealed class UiState<out R> private constructor() {
    data class Success<out T>(val data: T?) : UiState<T>()
    data class Error(val error: String) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data object Init : UiState<Nothing>()
}