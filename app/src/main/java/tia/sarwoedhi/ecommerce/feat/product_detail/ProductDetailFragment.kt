package tia.sarwoedhi.ecommerce.feat.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.databinding.FragmentProductDetailBinding
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity
import tia.sarwoedhi.ecommerce.feat.profile.ProfileDialogFragment

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun loadData() {

        val data =
            findNavController().previousBackStackEntry?.savedStateHandle?.get<ProductEntity>("product")
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
        }
    }


}