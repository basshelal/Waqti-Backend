package uk.whitecrescent.waqti.tests.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.collections.AbstractWaqtiList
import uk.whitecrescent.waqti.collections.ElementNotFoundException

@DisplayName("Abstract Waqti List Tests")
class AbstractWaqtiListTests {

    private class AbstractWaqtiListDummy : AbstractWaqtiList<String>()

    @DisplayName("Empty List")
    @Test
    fun testEmptyList() {
        val list = AbstractWaqtiListDummy()
        assertTrue(list.isEmpty())
        assertTrue(list.size == 0)
        assertTrue(list.nextIndex == 0)
    }

    @DisplayName("List Plus and Minus Operators")
    @Test
    fun testListPlusMinusOperators() {
        val list = AbstractWaqtiListDummy()
        list + "ZERO" + "ONE" + "TWO"
        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list - "ONE"
        assertEquals(2, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("TWO", list[1])

        val three = "THREE"

        list + three + three
        assertEquals(4, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("TWO", list[1])
        assertEquals("THREE", list[2])
        assertEquals("THREE", list[3])

        list - "FIVE"
        list - 5
        list - StringBuilder()

        assertEquals(4, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("TWO", list[1])
        assertEquals("THREE", list[2])
        assertEquals("THREE", list[3])
    }

    @DisplayName("List Set Operator Index")
    @Test
    fun testListSetOperatorIndex() {
        val list = AbstractWaqtiListDummy()
        list[0] = "ZERO"
        list[1] = "ONE"
        list[2] = "TWO"
        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list[1] = "NEW ONE"
        assertEquals(4, list.size)
        assertEquals("NEW ONE", list[1])

        assertThrows(IndexOutOfBoundsException::class.java, { list[6] = "SIX" })
    }

    @DisplayName("List Set Operator Element")
    @Test
    fun testListSetOperatorElement() {
        val list = AbstractWaqtiListDummy()
        list[0] = "ZERO"
        list[1] = "ONE"
        list[2] = "TWO"
        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list["ONE"] = "NEW ONE"
        assertEquals(3, list.size)
        assertEquals("NEW ONE", list[1])

        assertThrows(ElementNotFoundException::class.java, { list["FIVE"] = "NEW FIVE" })
    }

    @DisplayName("List Get Operator Index")
    @Test
    fun testListGetOperatorIndex() {
        val list = AbstractWaqtiListDummy()
        list[0] = "ZERO"
        list[1] = "ONE"
        list[2] = "TWO"
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        assertThrows(IndexOutOfBoundsException::class.java, { list[6].length })
    }

    @DisplayName("List Get Operator Element")
    @Test
    fun testListGetOperatorElement() {
        val list = AbstractWaqtiListDummy()
        list[0] = "ZERO"
        list[1] = "ONE"
        list[2] = "TWO"
        assertEquals("ZERO", list["ZERO"])
        assertEquals("ONE", list["ONE"])
        assertEquals("TWO", list["TWO"])

        assertThrows(ElementNotFoundException::class.java, { list["FIVE"] })
    }

    @DisplayName("List Contains Operator")
    @Test
    fun testListContainsOperator() {
        val list = AbstractWaqtiListDummy()
        list[0] = "ZERO"
        list[1] = "ONE"
        list[2] = "TWO"
        assertTrue("ONE" in list)
        assertTrue("FIVE" !in list)
        assertTrue("" !in list)

        list + "EXTRA" + "EXTRA" + "EXTRA"
        assertTrue("EXTRA" in list)
        list - "EXTRA"
        assertTrue("EXTRA" in list)
        list - "EXTRA"
        assertTrue("EXTRA" in list)
        list - "EXTRA"
        assertTrue("EXTRA" !in list)
    }

    @DisplayName("List Add")
    @Test
    fun testListAdd() {
        val list = AbstractWaqtiListDummy()
                .add("ZERO")
                .add("ONE")
                .add("TWO") as AbstractWaqtiListDummy

        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list.add("TWO").add("TWO")
        assertEquals(5, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])
        assertEquals("TWO", list[3])
        assertEquals("TWO", list[4])
    }

    @DisplayName("List Add At")
    @Test
    fun testListAddAt() {
        val list = AbstractWaqtiListDummy()
                .addAt(0, "ZERO")
                .addAt(1, "ONE")
                .addAt(2, "TWO") as AbstractWaqtiListDummy

        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list.addAt(0, "NEW ZERO").addAt(1, "NEW ONE")
        assertEquals(5, list.size)
        assertEquals("NEW ZERO", list[0])
        assertEquals("NEW ONE", list[1])
        assertEquals("ZERO", list[2])
        assertEquals("ONE", list[3])
        assertEquals("TWO", list[4])
    }

    @DisplayName("List Add All vararg")
    @Test
    fun testListAddAllVararg() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy

        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list.addAll("TWO", "TWO")
        assertEquals(5, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])
        assertEquals("TWO", list[3])
        assertEquals("TWO", list[4])
    }

    @DisplayName("List Add All collection")
    @Test
    fun testListAddAllCollection() {
        val list = AbstractWaqtiListDummy()
                .addAll(listOf(
                        "ZERO",
                        "ONE",
                        "TWO")
                ) as AbstractWaqtiListDummy

        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list.addAll(arrayListOf("TWO", "TWO"))
        assertEquals(5, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])
        assertEquals("TWO", list[3])
        assertEquals("TWO", list[4])
    }

    @DisplayName("List Add All At vararg")
    @Test
    fun testListAddAllAtVararg() {
        val list = AbstractWaqtiListDummy()
                .addAllAt(0,
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy

        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list.addAllAt(1, "NEW ONE", "NEW TWO")
        assertEquals(5, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("NEW ONE", list[1])
        assertEquals("NEW TWO", list[2])
        assertEquals("ONE", list[3])
        assertEquals("TWO", list[4])
    }

    @DisplayName("List Add All At collection")
    @Test
    fun testListAddAllAtCollection() {
        val list = AbstractWaqtiListDummy()
                .addAllAt(0,
                        listOf("ZERO",
                                "ONE",
                                "TWO")
                ) as AbstractWaqtiListDummy

        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
        assertEquals("TWO", list[2])

        list.addAllAt(1, hashSetOf("NEW ONE", "NEW TWO"))
        assertEquals(5, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("NEW ONE", list[1])
        assertEquals("NEW TWO", list[2])
        assertEquals("ONE", list[3])
        assertEquals("TWO", list[4])
    }

    @DisplayName("List Add If")
    @Test
    fun testListAddIf() {
        val list = AbstractWaqtiListDummy()
                .addIf(
                        listOf("A", "B", "BB", "C", "CCC", "D"),
                        { it.length == 1 }
                )
                as AbstractWaqtiListDummy

        assertEquals(4, list.size)
        assertEquals("A", list[0])
        assertEquals("B", list[1])
        assertEquals("C", list[2])
        assertEquals("D", list[3])

        list.addIf(
                listOf("Box", "Car", "Bag", "Food", "Bag"),
                { it[0] == 'B' }
        )
        assertEquals(7, list.size)
        assertEquals("Box", list[4])
        assertEquals("Bag", list[5])
        assertEquals("Bag", list[6])
    }

}