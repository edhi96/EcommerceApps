package tia.sarwoedhi.ecommerce.feat.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.order.model.response.OrderEntity
import tia.sarwoedhi.ecommerce.domain.order.usecase.LatestOrderUseCase
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val latestOrderUseCase: LatestOrderUseCase,
) : ViewModel() {
    private val _order: MutableStateFlow<UiState<OrderEntity>> =
        MutableStateFlow(UiState.Loading)

    val order: StateFlow<UiState<OrderEntity>>
        get() = _order.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    fun getLatestOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            _order.value = UiState.Loading
            when (val result = latestOrderUseCase.invoke()) {
                is DomainWrapper.Error -> _order.value =
                    (UiState.Error(result.statusResponse.orEmpty()))

                is DomainWrapper.Success -> {
                    _order.value = UiState.Success(result.data)
                }
            }
        }
    }
}