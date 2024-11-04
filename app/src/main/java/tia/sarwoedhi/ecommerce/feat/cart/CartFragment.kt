package tia.sarwoedhi.ecommerce.feat.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.databinding.FragmentCartBinding
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartProductEntity
import tia.sarwoedhi.ecommerce.domain.cart.model.response.CartEntity
import tia.sarwoedhi.ecommerce.feat.cart.adapter.CartAdapter
import tia.sarwoedhi.ecommerce.feat.cart.adapter.OnItemClickCallback

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels<CartViewModel>()
    private lateinit var adapterCart: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
        observeViewModel()
        viewModel.myCart()
    }

    private fun initAction() {
        binding.topAppBar.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initView() {
        binding.topAppBar.imgCart.isVisible = false
        binding.topAppBar.imgProfile.isVisible = false
        binding.topAppBar.imgBack.isVisible = true
        binding.topAppBar.lblTopBar.text = getString(R.string.cart)

        adapterCart = CartAdapter()
        adapterCart.setOnPlusItemClickCallback(object : OnItemClickCallback {
            override fun onPlusItem(data: CartEntity) {
                viewModel.updateCart(
                    CartProductEntity(
                        data.productId ?: 0,
                        quantity = (data.quantity ?: 0) + 1
                    )
                )
            }

            override fun onMinusItem(data: CartEntity) {
                viewModel.updateCart(
                    CartProductEntity(
                        data.productId ?: 0,
                        quantity = (data.quantity ?: 0) - 1
                    )
                )
            }
        })
        with(binding.rvCart) {
            this.adapter = adapterCart
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        }
    }

    private fun observeViewModel() {
        viewModel.listCart.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            binding.progressBar.isVisible = state is UiState.Loading
            if (state is UiState.Success) {
                if (state.data?.isNotEmpty() == true) {
                    adapterCart.updateData(state.data)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.cart_empty), Toast.LENGTH_LONG
                    ).show()
                }
            }

            if (state is UiState.Error) {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.updateCart.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            binding.progressBar.isVisible = state is UiState.Loading
            if (state is UiState.Success) {
                Toast.makeText(requireContext(),
                    getString(R.string.success_updated_cart), Toast.LENGTH_SHORT).show()
                viewModel.myCart()
            }

            if (state is UiState.Error) {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

}