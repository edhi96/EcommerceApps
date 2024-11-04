package tia.sarwoedhi.ecommerce.core.mapper

import tia.sarwoedhi.ecommerce.core.local.product.model.TableProduct
import tia.sarwoedhi.ecommerce.core.remote.model.product.request.ProductDetailParam
import tia.sarwoedhi.ecommerce.core.remote.model.product.response.ProductDto
import tia.sarwoedhi.ecommerce.domain.product.model.request.ProductDetailReq
import tia.sarwoedhi.ecommerce.domain.product.model.response.ProductEntity


fun ProductDto?.toDomain() = ProductEntity(
    id = this?.id ?: 0,
    category = this?.category.orEmpty(),
    image = this?.image.orEmpty(),
    description = this?.description.orEmpty(),
    price = this?.price ?: 0.0,
    title = this?.title.orEmpty()
)

fun List<ProductDto>?.toDomain() = this?.map(ProductDto::toDomain)

fun ProductDto?.toTableProduct() = TableProduct(
    id = (this?.id ?: 0).toString(),
    category = this?.category.orEmpty(),
    image = this?.image.orEmpty(),
    description = this?.description.orEmpty(),
    price = this?.price ?: 0.0,
    title = this?.title.orEmpty()
)

fun List<ProductDto>?.toTableProduct() = this?.map(ProductDto::toTableProduct)

fun TableProduct?.toDomainProduct() = ProductEntity(
    id = this?.id?.toIntOrNull() ?: 0,
    category = this?.category.orEmpty(),
    image = this?.image.orEmpty(),
    description = this?.description.orEmpty(),
    price = this?.price ?: 0.0,
    title = this?.title.orEmpty()
)

fun List<TableProduct>?.toDomainProduct() = this?.map(TableProduct::toDomainProduct)


fun ProductDetailReq.toParam(): ProductDetailParam {
    return ProductDetailParam(
        id = id,
    )
}