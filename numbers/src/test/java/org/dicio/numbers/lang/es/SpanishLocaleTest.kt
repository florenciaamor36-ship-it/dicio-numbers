package org.dicio.numbers.lang.es

import org.dicio.numbers.ParserFormatter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale

class SpanishLocaleTest {
    private fun parser(): ParserFormatter {
        return try {
            ParserFormatter(Locale.forLanguageTag("es-AR"))
        } catch (exception: Exception) {
            throw AssertionError("Could not initialize Spanish number configuration: ${exception.cause ?: exception}", exception)
        }
    }

    @Test fun parsesSpanishCardinalNumbers() {
        assertEquals(23L, parser().extractNumber("veintitrés").parseFirst()!!.integerValue())
        assertEquals(125L, parser().extractNumber("ciento veinticinco").parseFirst()!!.integerValue())
        assertEquals(201L, parser().extractNumber("doscientos uno").parseFirst()!!.integerValue())
        assertEquals(325L, parser().extractNumber("trescientos veinticinco").parseFirst()!!.integerValue())
        assertEquals(2026L, parser().extractNumber("dos mil veintiséis").parseFirst()!!.integerValue())
        assertEquals(1_000_000L, parser().extractNumber("un millón").parseFirst()!!.integerValue())
        assertEquals(32L, parser().extractNumber("treinta y dos").parseFirst()!!.integerValue())
    }

    @Test fun parsesSpanishOrdinalNumbersAndPluralForms() {
        val cases = mapOf(
            "primero" to 1L,
            "segunda" to 2L,
            "terceros" to 3L,
            "cuarto" to 4L,
            "cuartos" to 4L,
            "cuartas" to 4L,
            "quinto" to 5L,
            "quintos" to 5L,
            "quintas" to 5L,
            "sexto" to 6L,
            "sextos" to 6L,
            "séptimo" to 7L,
            "séptimos" to 7L,
            "octava" to 8L,
            "octavas" to 8L,
            "noveno" to 9L,
            "novenos" to 9L,
            "décimo" to 10L,
            "décimos" to 10L
        )
        for ((word, expected) in cases) {
            val number = parser().extractNumber(word).parseFirst()
            assertNotNull("Expected an ordinal value for '$word'", number)
            assertEquals("Wrong value for '$word'", expected, number!!.integerValue())
            assertEquals("'$word' should remain ordinal", true, number.isOrdinal)
        }
    }

    @Test fun parsesSpanishTimerDurations() {
        val duration = parser().extractDuration("cinco minutos y diez segundos").parseFirst()
        assertNotNull(duration)
        assertEquals(310L, duration!!.toJavaDuration().seconds)
        val hours = parser().extractDuration("dos horas").parseFirst()
        assertNotNull(hours)
        assertEquals(7200L, hours!!.toJavaDuration().seconds)
    }

    @Test fun parsesSpanishRelativeDatesAndWeekdays() {
        val now = LocalDateTime.of(2026, 11, 19, 10, 0)
        val tomorrow = parser().extractDateTime("mañana a las cinco").now(now).parseFirst()
        assertNotNull(tomorrow)
        assertEquals(LocalDate.of(2026, 11, 20), tomorrow!!.toLocalDate())
        assertEquals(5, tomorrow.hour)
        assertEquals(LocalDate.of(2026, 11, 20), parser().extractDateTime("mañana").now(now).parseFirst()!!.toLocalDate())
        assertEquals(LocalDate.of(2026, 11, 19), parser().extractDateTime("hoy").now(now).parseFirst()!!.toLocalDate())
        assertEquals(2, parser().extractDateTime("martes").now(now).parseFirst()!!.dayOfWeek.value)
    }

    @Test fun pronouncesCommonSpanishNumbers() {
        assertEquals("veintitrés", parser().pronounceNumber(23.0).get())
        assertEquals("ciento veinticinco", parser().pronounceNumber(125.0).get())
        assertEquals("menos cinco", parser().pronounceNumber(-5.0).get())
    }
}
