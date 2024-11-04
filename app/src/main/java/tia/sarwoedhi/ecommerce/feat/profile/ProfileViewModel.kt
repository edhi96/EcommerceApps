package tia.sarwoedhi.ecommerce.feat.profile

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
import tia.sarwoedhi.ecommerce.domain.auth.model.response.UserEntity
import tia.sarwoedhi.ecommerce.domain.auth.usecase.GetProfileUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getProfileUseCase: GetProfileUseCase) : ViewModel() {

    private val _state: MutableStateFlow<UiState<UserEntity>> =
        MutableStateFlow(UiState.Init)
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    fun getProfileUser() {
        viewModelScope.launch {
            _state.emit(UiState.Loading)
            delay(1000L)
            when (val result = getProfileUseCase.invoke()) {
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