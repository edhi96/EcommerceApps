package tia.sarwoedhi.ecommerce.core.remote.source.product

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import tia.sarwoedhi.ecommerce.core.remote.model.product.response.ProductDto

interface ProductApi {

    @GET("/products")
    suspend fun getListProduct(): Response<List<ProductDto>>

    @GET("/products/categories")
    suspend fun getListCategory(): Response<List<String>>

    @GET("/products")
    suspend fun getProductDetail(
        @Path("id") id: Int
    ): Response<ProductDto>


}