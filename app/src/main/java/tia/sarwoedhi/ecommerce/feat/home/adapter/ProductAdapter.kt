package tia.sarwoedhi.ecommerce.feat.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.databinding.ItemProductBinding
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList: MutableList<ProductEntity> = mutableListOf()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun getDataProduct() = productList

    fun clearData(){
        productList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(product)
        }
    }

    fun updateData(list: List<ProductEntity>) {
        productList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: ProductEntity) {
            binding.txtTitle.text = data.title
            binding.txtCategoryName.text = data.category
            binding.txtOverview.text = data.description
            binding.txtPrice.text = itemView.context.getString(R.string.price, data.price.toString())
            binding.labelImgNotAvailable.isVisible = data.image.isBlank()
            val image = data.image.ifBlank { R.drawable.shape_img_not_available }
            Glide.with(binding.root.context)
                .load(image)
                .into(binding.imgProduct)
        }
    }
}

interface OnItemClickCallback {
    fun onItemClicked(data: ProductEntity)
}