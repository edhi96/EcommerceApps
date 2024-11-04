package tia.sarwoedhi.ecommerce.feat.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.databinding.FragmentProfileDialogBinding

@AndroidEntryPoint
class ProfileDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfileUser()
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.state.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            if (state is UiState.Success && state.data != null) {
                val data = state.data
                binding.txtName.text =  requireContext().getString(R.string.name, data.name)
                binding.txtCity.text =  requireContext().getString(R.string.city, data.city)
                binding.txtEmail.text =  requireContext().getString(R.string.email, data.email)
                binding.txtPhone.text =  requireContext().getString(R.string.phone, data.phone)
                binding.txtUserName.text =  requireContext().getString(R.string.userName, data.username)
                binding.txtStreet.text =  requireContext().getString(R.string.address, data.street)
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): ProfileDialogFragment = ProfileDialogFragment()
    }

}