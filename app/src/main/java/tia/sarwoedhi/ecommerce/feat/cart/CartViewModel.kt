package tia.sarwoedhi.ecommerce.feat.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.auth.usecase.GetProfileUseCase
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartProductEntity
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartRequestEntity
import tia.sarwoedhi.ecommerce.domain.cart.model.response.CartEntity
import tia.sarwoedhi.ecommerce.domain.cart.usecase.DeleteCartUseCase
import tia.sarwoedhi.ecommerce.domain.cart.usecase.GetMyCartUseCase
import tia.sarwoedhi.ecommerce.domain.cart.usecase.UpdateCartUseCase
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val deleteCartUseCase: DeleteCartUseCase,
    private val getMyCartUseCase: GetMyCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _listCart: MutableStateFlow<UiState<List<CartEntity>>> =
        MutableStateFlow(UiState.Loading)

    val listCart: StateFlow<UiState<List<CartEntity>>>
        get() = _listCart.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    private val _updateCart: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Loading)

    val updateCart: StateFlow<UiState<Unit>>
        get() = _updateCart.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    fun myCart() {
        viewModelScope.launch(Dispatchers.IO) {
            _listCart.value = UiState.Loading
            when (val result = getMyCartUseCase.invoke()) {
                is DomainWrapper.Error -> _listCart.value =
                    (UiState.Error(result.statusResponse.orEmpty()))

                is DomainWrapper.Success -> {
                    _listCart.value = UiState.Success(result.data)
                }
            }
        }
    }

    fun updateCart(product: CartProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateCart.value = UiState.Loading
            val user = async {
                when (val result = getProfileUseCase.invoke()) {
                    is DomainWrapper.Error -> null
                    is DomainWrapper.Success -> result.data
                }
            }.await()
            val request = CartRequestEntity(
                userId = user?.id ?: 0,
                date = "2024-11-05",
                products = listOf(product)
            )

            if(product.quantity > 0){
                when (val result = updateCartUseCase.invoke(request)) {
                    is DomainWrapper.Error -> _updateCart.value =
                        (UiState.Error(result.statusResponse.orEmpty()))

                    is DomainWrapper.Success -> {
                        _updateCart.value = UiState.Success(result.data)
                    }
                }
            }else{
                when (val result = deleteCartUseCase.invoke(request)) {
                    is DomainWrapper.Error -> _updateCart.value =
                        (UiState.Error(result.statusResponse.orEmpty()))

                    is DomainWrapper.Success -> {
                        _updateCart.value = UiState.Success(result.data)
                    }
                }
            }

        }
    }


}