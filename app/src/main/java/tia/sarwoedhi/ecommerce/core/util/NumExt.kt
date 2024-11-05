package tia.sarwoedhi.ecommerce.core.util

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.toFormatDecimal(): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this)
}