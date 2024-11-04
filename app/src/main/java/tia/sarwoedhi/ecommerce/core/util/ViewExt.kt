package tia.sarwoedhi.ecommerce.core.util

import android.view.View


fun View.setAlpha(isLoading: Boolean){
    this.alpha = if (isLoading) 1.0f else 0.0f
}

fun View.setEnabled(isLoading : Boolean) {
    this.isEnabled = isLoading == false
}