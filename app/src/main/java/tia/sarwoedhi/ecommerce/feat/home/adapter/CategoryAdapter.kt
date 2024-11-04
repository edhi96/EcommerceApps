package tia.sarwoedhi.ecommerce.feat.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import tia.sarwoedhi.ecommerce.databinding.ItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList: MutableList<String> = mutableListOf()

    private lateinit var onCategoryClickCallback: OnCategoryClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnCategoryClickCallback) {
        this.onCategoryClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    fun clearData(){
        categoryList.clear()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)
        holder.itemView.setOnClickListener {
            onCategoryClickCallback.onCategoryClicked(category)
        }
    }

    override fun getItemCount() = categoryList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<String>) {
        categoryList.addAll(list)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.txtCategoryName.text = data
        }
    }

}

interface OnCategoryClickCallback {
    fun onCategoryClicked(data: String)
}