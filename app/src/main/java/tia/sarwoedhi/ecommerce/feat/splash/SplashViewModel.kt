package tia.sarwoedhi.ecommerce.feat.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.domain.DomainWrapper
import tia.sarwoedhi.ecommerce.domain.auth.usecase.IsLoggedInUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<Boolean>> =
        MutableStateFlow(UiState.Init)
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    fun getLoginStatus() {
        viewModelScope.launch {
            _state.emit(UiState.Loading)
            delay(1000L)
            when (val result = isLoggedInUseCase.invoke()) {
                is DomainWrapper.Error -> {
                    _state.emit(UiState.Error(result.statusResponse.orEmpty()))
                }

                is DomainWrapper.Success -> {
                    _state.emit(UiState.Success(result.data))
                }
            }
        }
    }

}