package tia.sarwoedhi.ecommerce.feat.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.databinding.FragmentHomeBinding
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity
import tia.sarwoedhi.ecommerce.feat.home.adapter.CategoryAdapter
import tia.sarwoedhi.ecommerce.feat.home.adapter.OnCategoryClickCallback
import tia.sarwoedhi.ecommerce.feat.home.adapter.OnItemClickCallback
import tia.sarwoedhi.ecommerce.feat.home.adapter.ProductAdapter
import tia.sarwoedhi.ecommerce.feat.profile.ProfileDialogFragment


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getData()
        observeViewModel()
    }

    private fun initView() {
        binding.topAppBar.lblTopBar.text = getString(R.string.home)
        binding.topAppBar.imgProfile.setOnClickListener {
            ProfileDialogFragment.newInstance().apply {
                show(this@HomeFragment.childFragmentManager, javaClass.simpleName)
            }
        }
        categoryAdapter = CategoryAdapter()
        categoryAdapter.setOnItemClickCallback(object : OnCategoryClickCallback {
            override fun onCategoryClicked(data: String) {
                val listProductSorted = productAdapter.getDataProduct().sortedByDescending { it.category == data }
                productAdapter.clearData()
                productAdapter.updateData(listProductSorted)
            }

        })
        with(binding.rvCategory) {
            this.adapter = categoryAdapter
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        productAdapter = ProductAdapter()
        productAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: ProductEntity) {
                navigateToProductDetail(data)
            }
        })
        with(binding.rvProduct) {
            this.adapter = productAdapter
            this.layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToProductDetail(data: ProductEntity) {
        view?.findNavController()?.currentBackStackEntry?.savedStateHandle?.set("product", data)
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_ProductDetailFragment)
    }

    private fun observeViewModel() {

        viewModel.listCategory.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            categoryResponse(state)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.listProduct.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            productResponse(state)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun categoryResponse(resource: UiState<List<String>>?) {
        when (resource) {
            is UiState.Success -> {
                showLoading(false)
                categoryAdapter.clearData()
                resource.data?.let {
                    categoryAdapter.updateData(it)
                }

            }

            is UiState.Error -> {
                showLoading(false)
                Toast.makeText(requireContext(), resource.error, Toast.LENGTH_LONG).show()
            }

            is UiState.Loading -> {
                showLoading(true)
            }

            else -> {}
        }
    }

    private fun productResponse(resource: UiState<List<ProductEntity>>?) {
        when (resource) {
            is UiState.Success -> {
                showLoading(false)
                productAdapter.clearData()
                 resource.data?.let {
                    productAdapter.updateData(it)
                }

            }

            is UiState.Error -> {
                showLoading(false)
                Toast.makeText(requireContext(), resource.error, Toast.LENGTH_LONG).show()
            }

            is UiState.Loading -> {
                showLoading(true)
            }

            else -> {}
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.alpha = if (isLoading) 1.0f else 0.0f
        binding.tvMessage.alpha = if (isLoading) 0.0f else 1.0f
    }


}