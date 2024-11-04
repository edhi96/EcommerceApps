package tia.sarwoedhi.ecommerce.feat.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.databinding.FragmentSplashBinding

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLoginStatus()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {

        viewModel.state.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            if ((state is UiState.Success && state.data == false) || state is UiState.Error) {
                navigateToLoginScreen()
            }

            if (state is UiState.Success && state.data == true) {
                navigateToHomeScreen()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun navigateToHomeScreen() {
        view?.findNavController()?.navigate(R.id.action_splashFragment_toHomeFragment)
    }

    private fun navigateToLoginScreen() {
        view?.findNavController()?.navigate(R.id.action_splashFragment_toLoginFragment)
    }

}