package tia.sarwoedhi.ecommerce.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateExt {
    private const val DATE_FORMAT_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss"
    fun getDateDDMMYYYYHHMMSS(dateTime: Date): String {
        return try {
            val date =
                SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY_HH_MM_SS, Locale.getDefault()).format(
                    dateTime.time
                )
            date
        } catch (e: Exception) {
            ""
        }
    }
}

