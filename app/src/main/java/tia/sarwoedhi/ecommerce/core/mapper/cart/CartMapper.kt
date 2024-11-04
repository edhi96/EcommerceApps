package tia.sarwoedhi.ecommerce.core.mapper.cart

import tia.sarwoedhi.ecommerce.core.local.cart.model.response.TableCart
import tia.sarwoedhi.ecommerce.core.local.product.model.response.TableProduct
import tia.sarwoedhi.ecommerce.core.remote.model.cart.request.CartProductDto
import tia.sarwoedhi.ecommerce.core.remote.model.cart.request.CartRequestDto
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartProductEntity
import tia.sarwoedhi.ecommerce.domain.cart.model.request.CartRequestEntity
import tia.sarwoedhi.ecommerce.domain.cart.model.response.CartEntity

fun TableCart?.toCartDomain() = CartEntity(
    productId = this?.productId,
    category = this?.category.orEmpty(),
    image = this?.image.orEmpty(),
    title = this?.title.orEmpty(),
    price = this?.price,
    quantity = this?.quantity,
)

fun List<TableCart>?.toDomain() = this?.map(TableCart::toCartDomain)


fun CartProductEntity?.toRequest() = CartProductDto(
    productId = this?.productId ?: 0,
    quantity = this?.quantity ?: 0
)

fun TableProduct?.toDomainProduct(qty: Int) = TableCart(
    id = this?.id ?: 0,
    category = this?.category.orEmpty(),
    image = this?.image.orEmpty(),
    price = this?.price ?: 0.0,
    title = this?.title.orEmpty(),
    productId = this?.id ?: 0,
    quantity = qty
)

fun List<CartProductEntity>?.toRequest() = this?.map(CartProductEntity::toRequest)

fun CartRequestEntity?.toRequest() = CartRequestDto(
    date = this?.date.orEmpty(),
    products = this?.products?.toRequest() ?: emptyList(),
    userId = this?.userId ?: 0
)