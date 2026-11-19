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
            "primera" to 1L,
            "primer" to 1L,
            "segundo" to 2L,
            "segunda" to 2L,
            "tercero" to 3L,
            "tercera" to 3L,
            "terceros" to 3L,
            "terceras" to 3L,
            "undecimo" to 11L,
            "undecima" to 11L,
            "undecimos" to 11L,
            "undecimas" to 11L,
            "undécimo" to 11L,
            "undécima" to 11L,
            "undécimos" to 11L,
            "undécimas" to 11L,
            "duodecimo" to 12L,
            "duodecima" to 12L,
            "duodecimos" to 12L,
            "duodecimas" to 12L,
            "duodécimo" to 12L,
            "duodécima" to 12L,
            "duodécimos" to 12L,
            "duodécimas" to 12L,
            "decimotercero" to 13L,
            "decimotercera" to 13L,
            "decimoterceros" to 13L,
            "decimoterceras" to 13L,
            "décimotercero" to 13L,
            "décimotercera" to 13L,
            "décimoterceros" to 13L,
            "décimoterceras" to 13L,
            "decimocuarto" to 14L,
            "decimocuarta" to 14L,
            "decimocuartos" to 14L,
            "decimocuartas" to 14L,
            "décimocuarto" to 14L,
            "décimocuarta" to 14L,
            "décimocuartos" to 14L,
            "décimocuartas" to 14L,
            "decimoquinto" to 15L,
            "decimoquinta" to 15L,
            "decimoquintos" to 15L,
            "decimoquintas" to 15L,
            "décimoquinto" to 15L,
            "décimoquinta" to 15L,
            "décimoquintos" to 15L,
            "décimoquintas" to 15L,
            "decimosexto" to 16L,
            "decimosexta" to 16L,
            "decimosextos" to 16L,
            "decimosextas" to 16L,
            "décimosexto" to 16L,
            "décimosexta" to 16L,
            "décimosextos" to 16L,
            "décimosextas" to 16L,
            "decimoseptimo" to 17L,
            "decimoseptima" to 17L,
            "decimoseptimos" to 17L,
            "decimoseptimas" to 17L,
            "decimoséptimo" to 17L,
            "decimoséptima" to 17L,
            "decimoséptimos" to 17L,
            "decimoséptimas" to 17L,
            "decimoctavo" to 18L,
            "decimoctava" to 18L,
            "decimoctavos" to 18L,
            "decimoctavas" to 18L,
            "décimoctavo" to 18L,
            "décimoctava" to 18L,
            "décimoctavos" to 18L,
            "décimoctavas" to 18L,
            "decimonoveno" to 19L,
            "decimonovena" to 19L,
            "decimonovenos" to 19L,
            "decimonovenas" to 19L,
            "décimonoveno" to 19L,
            "décimonovena" to 19L,
            "décimonovenos" to 19L,
            "décimonovenas" to 19L,
            "vigesimo" to 20L,
            "vigesima" to 20L,
            "vigesimos" to 20L,
            "vigesimas" to 20L,
            "vigésimo" to 20L,
            "vigésima" to 20L,
            "vigésimos" to 20L,
            "vigésimas" to 20L,
            "trigesimo" to 30L,
            "trigesima" to 30L,
            "trigesimos" to 30L,
            "trigesimas" to 30L,
            "trigésimo" to 30L,
            "trigésima" to 30L,
            "trigésimos" to 30L,
            "trigésimas" to 30L,
            "cuadragesimo" to 40L,
            "cuadragesima" to 40L,
            "cuadragesimos" to 40L,
            "cuadragesimas" to 40L,
            "cuadragésimo" to 40L,
            "cuadragésima" to 40L,
            "cuadragésimos" to 40L,
            "cuadragésimas" to 40L,
            "quincuagesimo" to 50L,
            "quincuagesima" to 50L,
            "quincuagesimos" to 50L,
            "quincuagesimas" to 50L,
            "quincuagésimo" to 50L,
            "quincuagésima" to 50L,
            "quincuagésimos" to 50L,
            "quincuagésimas" to 50L,
            "sexagesimo" to 60L,
            "sexagesima" to 60L,
            "sexagesimos" to 60L,
            "sexagesimas" to 60L,
            "sexagésimo" to 60L,
            "sexagésima" to 60L,
            "sexagésimos" to 60L,
            "sexagésimas" to 60L,
            "septuagesimo" to 70L,
            "septuagesima" to 70L,
            "septuagesimos" to 70L,
            "septuagesimas" to 70L,
            "septuagésimo" to 70L,
            "septuagésima" to 70L,
            "septuagésimos" to 70L,
            "septuagésimas" to 70L,
            "octogesimo" to 80L,
            "octogesima" to 80L,
            "octogesimos" to 80L,
            "octogesimas" to 80L,
            "octogésimo" to 80L,
            "octogésima" to 80L,
            "octogésimos" to 80L,
            "octogésimas" to 80L,
            "nonagesimo" to 90L,
            "nonagesima" to 90L,
            "nonagesimos" to 90L,
            "nonagesimas" to 90L,
            "nonagésimo" to 90L,
            "nonagésima" to 90L,
            "nonagésimos" to 90L,
            "nonagésimas" to 90L,
            "centesimo" to 100L,
            "centesima" to 100L,
            "centesimos" to 100L,
            "centesimas" to 100L,
            "centésimo" to 100L,
            "centésima" to 100L,
            "centésimos" to 100L,
            "centésimas" to 100L,
            "ducentesimo" to 200L,
            "ducentesima" to 200L,
            "ducentesimos" to 200L,
            "ducentesimas" to 200L,
            "ducentésimo" to 200L,
            "ducentésima" to 200L,
            "ducentésimos" to 200L,
            "ducentésimas" to 200L,
            "tricentesimo" to 300L,
            "tricentesima" to 300L,
            "tricentesimos" to 300L,
            "tricentesimas" to 300L,
            "tricentésimo" to 300L,
            "tricentésima" to 300L,
            "tricentésimos" to 300L,
            "tricentésimas" to 300L,
            "quadringentesimo" to 400L,
            "quadringentesima" to 400L,
            "quadringentesimos" to 400L,
            "quadringentesimas" to 400L,
            "cuadringentésimo" to 400L,
            "cuadringentésima" to 400L,
            "cuadringentésimos" to 400L,
            "cuadringentésimas" to 400L,
            "quingentesimo" to 500L,
            "quingentesima" to 500L,
            "quingentesimos" to 500L,
            "quingentesimas" to 500L,
            "quingentésimo" to 500L,
            "quingentésima" to 500L,
            "quingentésimos" to 500L,
            "quingentésimas" to 500L,
            "sexcentesimo" to 600L,
            "sexcentesima" to 600L,
            "sexcentesimos" to 600L,
            "sexcentesimas" to 600L,
            "sexcentésimo" to 600L,
            "sexcentésima" to 600L,
            "sexcentésimos" to 600L,
            "sexcentésimas" to 600L,
            "septingentesimo" to 700L,
            "septingentesima" to 700L,
            "septingentesimos" to 700L,
            "septingentesimas" to 700L,
            "septingentésimo" to 700L,
            "septingentésima" to 700L,
            "septingentésimos" to 700L,
            "septingentésimas" to 700L,
            "octingentesimo" to 800L,
            "octingentesima" to 800L,
            "octingentesimos" to 800L,
            "octingentesimas" to 800L,
            "octingentésimo" to 800L,
            "octingentésima" to 800L,
            "octingentésimos" to 800L,
            "octingentésimas" to 800L,
            "noningentesimo" to 900L,
            "noningentesima" to 900L,
            "noningentesimos" to 900L,
            "noningentesimas" to 900L,
            "noningentésimo" to 900L,
            "noningentésima" to 900L,
            "noningentésimos" to 900L,
            "noningentésimas" to 900L
        )
        for ((word, expected) in cases) {
            val number = parser().extractNumber(word).preferOrdinal(true).parseFirst()
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
