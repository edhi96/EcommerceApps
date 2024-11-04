package tia.sarwoedhi.ecommerce.core.base


import retrofit2.Response

abstract class BaseDataSourceImpl {

    protected inline fun <reified T> executeWithResponse(block: () -> Response<T>): DataResult<T & Any> {
        try {
            val response = block()
            if (!response.isSuccessful) {
                return DataResult.Error(httpCode = response.code(), Exception(response.message()))
            }
            val body = response.body()
            return DataResult.Success(body)
        } catch (e: Exception) {
            return DataResult.Error(exception = e)
        }
    }

}