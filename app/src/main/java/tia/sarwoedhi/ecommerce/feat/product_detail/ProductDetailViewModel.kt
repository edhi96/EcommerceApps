package tia.sarwoedhi.ecommerce.feat.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.auth.usecase.GetProfileUseCase
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartProductEntity
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartRequestEntity
import tia.sarwoedhi.ecommerce.domain.cart.usecase.AddToCartUseCase
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val addToCartUseCase: AddToCartUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _cartState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Init)
    val cartState = _cartState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    fun postAddCart(product: CartProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _cartState.value = UiState.Loading
            val user = async {
                when (val result = getProfileUseCase.invoke()) {
                    is DomainWrapper.Error -> null
                    is DomainWrapper.Success -> result.data
                }
            }.await()

            val request = CartRequestEntity(userId = user?.id ?: 0, products = listOf(product))
            when (val result = addToCartUseCase.invoke(request)) {
                is DomainWrapper.Error -> {
                    _cartState.value =
                        (UiState.Error(result.statusResponse.orEmpty()))
                }

                is DomainWrapper.Success -> {
                    _cartState.value = (UiState.Success(Unit))
                }
            }
        }
    }

}