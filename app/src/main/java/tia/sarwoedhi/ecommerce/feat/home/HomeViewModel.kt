package tia.sarwoedhi.ecommerce.feat.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity
import tia.sarwoedhi.ecommerce.domain.product.usecase.GetCategoryUseCase
import tia.sarwoedhi.ecommerce.domain.product.usecase.GetProductUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCase: GetCategoryUseCase,
    private val productUseCase: GetProductUseCase
) :
    ViewModel() {
    private val _listCategory: MutableStateFlow<UiState<List<String>>> =
        MutableStateFlow(UiState.Loading)
    val listCategory: StateFlow<UiState<List<String>>>
        get() = _listCategory.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    private val _lstProduct: MutableStateFlow<UiState<List<ProductEntity>>> =
        MutableStateFlow(UiState.Loading)

    val listProduct: StateFlow<UiState<List<ProductEntity>>>
        get() = _lstProduct.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )


    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {

            _listCategory.emit(UiState.Loading)
            val dataCategory = launch {
                when (val result = categoryUseCase.invoke()) {
                    is DomainWrapper.Error -> _listCategory.emit(UiState.Error(result.statusResponse.orEmpty()))
                    is DomainWrapper.Success -> _listCategory.emit(UiState.Success(result.data))
                }
            }

            val dataProduct = launch {
                when (val result = productUseCase.invoke()) {
                    is DomainWrapper.Error -> _lstProduct.emit(UiState.Error(result.statusResponse.orEmpty()))
                    is DomainWrapper.Success -> _lstProduct.emit(UiState.Success(result.data))
                }
            }

            joinAll(dataProduct, dataCategory)
        }
    }

}