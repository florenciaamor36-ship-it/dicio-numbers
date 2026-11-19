package org.dicio.numbers.lang.es

import org.dicio.numbers.formatter.Formatter
import org.dicio.numbers.unit.MixedFraction
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

/** Spanish formatter for the Dicio number library. */
class SpanishFormatter : Formatter("config/es-es") {
    override fun niceNumber(mixedFraction: MixedFraction, speech: Boolean): String {
        if (!speech) return niceNumberNotSpeech(mixedFraction)
        val sign = if (mixedFraction.negative) "menos " else ""
        if (mixedFraction.numerator == 0) return sign + pronounceNumber(mixedFraction.whole.toDouble(), 0, true, false, false)
        val whole = if (mixedFraction.whole == 0L) "" else pronounceNumber(mixedFraction.whole.toDouble(), 0, true, false, false) + " y "
        return sign + whole + pronounceNumber(mixedFraction.numerator.toDouble(), 0, true, false, false) + " sobre " + pronounceNumber(mixedFraction.denominator.toDouble(), 0, true, false, false)
    }

    override fun pronounceNumber(number: Double, places: Int, shortScale: Boolean, scientific: Boolean, ordinal: Boolean): String {
        if (number.isNaN()) return "no es un número"
        if (number == Double.POSITIVE_INFINITY) return "infinito"
        if (number == Double.NEGATIVE_INFINITY) return "menos infinito"
        if (scientific || abs(number) > 9_223_372_036_854_775_807.0) {
            val scientificValue = String.format(Locale.US, "%E", number).split("E")
            val mantissa = scientificValue[0].toDouble()
            val exponent = scientificValue[1].toInt()
            return pronounceNumber(mantissa, places, shortScale, false, false) + " por diez elevado a " + pronounceNumber(exponent.toDouble(), 0, shortScale, false, false)
        }
        val negative = number < 0
        val magnitude = abs(number)
        val precision = places.coerceIn(0, 10)
        val rounded = String.format(Locale.US, "% .${precision}f", magnitude).trim().toDouble()
        val whole = rounded.toLong()
        val result = if (ordinal && rounded == whole.toDouble()) ordinalName(whole) ?: cardinal(whole) else cardinal(whole)
        val decimalDigits = if (precision == 0) "" else String.format(Locale.US, "% .${precision}f", rounded).trim().substringAfter('.', "").trimEnd('0')
        val spoken = if (decimalDigits.isEmpty()) result else result + " coma " + decimalDigits.map { digitName(it.digitToInt()) }.joinToString(" ")
        return (if (negative && (rounded != 0.0 || precision > 0)) "menos " else "") + spoken
    }

    override fun niceTime(time: LocalTime, speech: Boolean, use24Hour: Boolean, showAmPm: Boolean): String {
        if (!speech) return if (use24Hour) time.format(DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT)) else time.format(DateTimeFormatter.ofPattern(if (showAmPm) "h:mm a" else "h:mm", Locale.ROOT))
        if (time.hour == 0 && time.minute == 0) return "medianoche"
        if (time.hour == 12 && time.minute == 0) return "mediodía"
        val hour = if (use24Hour) time.hour else (time.hour % 12).let { if (it == 0) 12 else it }
        val hourText = if (hour == 1) "la una" else "las ${cardinal(hour.toLong())}"
        val minutes = when (time.minute) {
            0 -> "en punto"
            15 -> "y cuarto"
            30 -> "y media"
            45 -> "menos cuarto"
            else -> "y ${cardinal(time.minute.toLong())}"
        }
        return "$hourText $minutes"
    }

    private fun ordinalName(n: Long): String? = when (n) {
        1L -> "primero"; 2L -> "segundo"; 3L -> "tercero"; 4L -> "cuarto"; 5L -> "quinto"
        6L -> "sexto"; 7L -> "séptimo"; 8L -> "octavo"; 9L -> "noveno"; 10L -> "décimo"
        11L -> "undécimo"; 12L -> "duodécimo"; else -> null
    }

    private fun digitName(n: Int) = CARDINAL[n.toLong()] ?: "cero"

    private fun cardinal(n: Long): String {
        if (n < 0) return "menos " + cardinal(-n)
        if (n <= 999) return underThousand(n)
        if (n < 1_000_000) {
            val thousands = n / 1000
            val remainder = n % 1000
            val prefix = if (thousands == 1L) "mil" else underThousand(thousands) + " mil"
            return join(prefix, if (remainder == 0L) "" else underThousand(remainder))
        }
        if (n < 1_000_000_000_000L) {
            val millions = n / 1_000_000
            val remainder = n % 1_000_000
            val prefix = if (millions == 1L) "un millón" else cardinal(millions) + " millones"
            return join(prefix, if (remainder == 0L) "" else cardinal(remainder))
        }
        val billions = n / 1_000_000_000_000L
        val remainder = n % 1_000_000_000_000L
        val prefix = if (billions == 1L) "un billón" else cardinal(billions) + " billones"
        return join(prefix, if (remainder == 0L) "" else cardinal(remainder))
    }

    private fun underThousand(n: Long): String {
        if (n < 30) return CARDINAL[n] ?: "cero"
        if (n < 100) {
            if (n % 10 == 0L) return TENS[n] ?: ""
            if (n < 30) return CARDINAL[n] ?: ""
            return (TENS[n / 10 * 10] ?: "") + " y " + underThousand(n % 10)
        }
        if (n == 100L) return "cien"
        if (n < 200) return "ciento " + underThousand(n % 100)
        val hundreds = HUNDREDS[n / 100] ?: ""
        return if (n % 100 == 0L) hundreds else hundreds + " " + underThousand(n % 100)
    }

    private fun join(a: String, b: String) = if (b.isEmpty()) a else "$a $b"

    companion object {
        private val CARDINAL = mapOf<Long, String>(
            0L to "cero", 1L to "uno", 2L to "dos", 3L to "tres", 4L to "cuatro", 5L to "cinco", 6L to "seis", 7L to "siete", 8L to "ocho", 9L to "nueve",
            10L to "diez", 11L to "once", 12L to "doce", 13L to "trece", 14L to "catorce", 15L to "quince", 16L to "dieciséis", 17L to "diecisiete", 18L to "dieciocho", 19L to "diecinueve",
            20L to "veinte", 21L to "veintiuno", 22L to "veintidós", 23L to "veintitrés", 24L to "veinticuatro", 25L to "veinticinco", 26L to "veintiséis", 27L to "veintisiete", 28L to "veintiocho", 29L to "veintinueve"
        )
        private val TENS = mapOf(30L to "treinta", 40L to "cuarenta", 50L to "cincuenta", 60L to "sesenta", 70L to "setenta", 80L to "ochenta", 90L to "noventa")
        private val HUNDREDS = mapOf(2L to "doscientos", 3L to "trescientos", 4L to "cuatrocientos", 5L to "quinientos", 6L to "seiscientos", 7L to "setecientos", 8L to "ochocientos", 9L to "novecientos")
    }
}
