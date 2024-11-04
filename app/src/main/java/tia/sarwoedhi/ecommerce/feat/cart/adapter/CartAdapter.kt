package tia.sarwoedhi.ecommerce.feat.cart.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.databinding.ItemCartBinding
import tia.sarwoedhi.ecommerce.domain.cart.model.response.CartEntity

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val cartList: MutableList<CartEntity> = mutableListOf()

    private lateinit var onPlusItemClickCallback: OnItemClickCallback

    fun setOnPlusItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onPlusItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = cartList[position]
        holder.bind(cart)
    }

    fun updateData(list: List<CartEntity>) {
        cartList.clear()
        cartList.addAll(list)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: CartEntity) {
            binding.txtTitle.text = data.title
            binding.txtCategoryName.text = data.category
            binding.txtQuantity.text = data.quantity.toString()
            binding.txtPrice.text =
                itemView.context.getString(R.string.price, data.price.toString())
            binding.labelImgNotAvailable.isVisible = data.image?.isBlank() == true
            val image = data.image?.ifBlank { R.drawable.shape_img_not_available }
            Glide.with(binding.imgProduct.context)
                .load(image)
                .placeholder(R.drawable.shape_img_not_available)
                .into(binding.imgProduct)
            binding.btnPlusCart.setOnClickListener {
                onPlusItemClickCallback.onPlusItem(data)
            }
            binding.btnMinusCart.setOnClickListener {
                onPlusItemClickCallback.onMinusItem(data)
            }
        }
    }
}

interface OnItemClickCallback {
    fun onPlusItem(data: CartEntity)
    fun onMinusItem(data: CartEntity)
}