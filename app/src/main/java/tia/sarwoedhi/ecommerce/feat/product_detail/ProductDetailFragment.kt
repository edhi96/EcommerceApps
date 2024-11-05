package tia.sarwoedhi.ecommerce.feat.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.databinding.FragmentProductDetailBinding
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartProductEntity
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity
import tia.sarwoedhi.ecommerce.feat.profile.ProfileDialogFragment

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private var product: ProductEntity? = null
    private val viewModel: ProductDetailViewModel by viewModels()
    private var qty: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
        loadData()
        observerViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.topAppBar.imgBack.isVisible = true
        binding.topAppBar.lblTopBar.text = getString(R.string.product_detail)
    }

    private fun initAction() {
        binding.topAppBar.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.topAppBar.imgProfile.setOnClickListener {
            ProfileDialogFragment.newInstance().apply {
                show(this@ProductDetailFragment.childFragmentManager, javaClass.simpleName)
            }
        }
        binding.topAppBar.imgCart.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_productDetailFragment_to_cartFragment)
        }
        binding.btnAddCart.setOnClickListener {
            if (product != null) {
                qty += 1
                viewModel.postAddCart(CartProductEntity(product?.id ?: 0, qty))
            }
        }

        binding.btnPlusCart.setOnClickListener {
            qty += 1
            viewModel.updateCart(CartProductEntity(product?.id ?: 0, qty))
        }

        binding.btnMinusCart.setOnClickListener {
            qty -= 1
            viewModel.updateCart(CartProductEntity(product?.id ?: 0, qty))
        }

    }

    private fun loadData() {

        product =
            findNavController().previousBackStackEntry?.savedStateHandle?.get<ProductEntity>("product")
        val data = product
        if (data != null) {
            binding.txtTitle.text = data.title
            binding.txtCategoryName.text = data.category
            binding.txtOverview.text = data.description
            binding.txtPrice.text =
                requireContext().getString(R.string.price, data.price.toString())

            binding.labelImgNotAvailable.isVisible = data.image.isBlank()
            val image = data.image.ifBlank { R.drawable.shape_img_not_available }
            Glide.with(binding.root.context)
                .load(image)
                .skipMemoryCache(true)
                .placeholder(R.drawable.shape_img_not_available)
                .into(binding.imgProduct)
            viewModel.myCart(data.id)
        }

    }

    private fun observerViewModel() {
        viewModel.cartState.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            if (state is UiState.Success && state.data != null) {
                viewModel.myCart(product?.id ?: 0)
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.myCartState.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            binding.btnAddCart.isVisible =
                !(state is UiState.Success && (state.data?.quantity ?: 0) > 0)
            binding.layoutCart.isVisible =
                state is UiState.Success && (state.data?.quantity ?: 0) > 0
            if (state is UiState.Success && (state.data?.quantity ?: 0) > 0) {
                qty = state.data?.quantity ?: 0
                binding.txtQuantity.text = qty.toString()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.updateCart.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->

            if (state is UiState.Success && state.data != null) {
                binding.layoutCart.isVisible = state.data > 0
                binding.btnAddCart.isVisible = state.data < 1
                val newQty = state.data
                binding.txtQuantity.text = (newQty).toString()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


}