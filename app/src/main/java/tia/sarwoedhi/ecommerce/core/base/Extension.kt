package tia.sarwoedhi.ecommerce.core.base

import org.json.JSONObject
import retrofit2.Response


fun <In, Out> Response<In>.toCallResult(
    mapData: (response: In) -> Out
): DataResult<Out> {
    return try {
        if (this.isSuccessful) {
            val data = this.body()
            if (data is Unit) {
                DataResult.Success(mapData(data))
            } else if (data != null) {
                DataResult.Success(mapData(data))
            } else {
                DataResult.Error(
                    httpCode = this.code(),
                    exception = Exception("Invalid Response Body")
                )
            }
        } else {
            val message = this.errorBody()?.string()?.let {
                JSONObject(it).getString("error")
            }
            DataResult.Error(
                httpCode = this.code(),
                exception = Exception(message)
            )
        }
    } catch (e: Exception) {
        DataResult.Error(
            httpCode = this.code(),
            exception = Exception(e)
        )
    }
}