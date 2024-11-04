package tia.sarwoedhi.ecommerce.feat.auth

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
import tia.sarwoedhi.ecommerce.domain.auth.model.request.LoginRequest
import tia.sarwoedhi.ecommerce.domain.auth.model.response.TokenEntity
import tia.sarwoedhi.ecommerce.domain.auth.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginState: MutableStateFlow<UiState<TokenEntity>> =
        MutableStateFlow(UiState.Init)

    val loginState: StateFlow<UiState<TokenEntity>> = _loginState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Init
    )

    fun postLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.value = UiState.Loading
            launch {
                when (val result = loginUseCase.invoke(LoginRequest(email, password))) {
                    is DomainWrapper.Error -> {
                        _loginState.value =
                            (UiState.Error(result.statusResponse.orEmpty()))
                    }

                    is DomainWrapper.Success -> {
                        _loginState.value = (UiState.Success(result.data))
                    }
                }
            }
        }
    }

}