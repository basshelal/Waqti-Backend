package uk.whitecrescent.waqti.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Concurrency Tests")
class ConcurrencyTests {

    @DisplayName("Test")
    @Test
    fun test() {
        assertEquals(2 + 2, 4)
    }

}