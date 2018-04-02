package uk.whitecrescent.waqti.tests.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
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

    @DisplayName("List Update")
    @Test
    fun testListUpdate() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        list.update("ONE", "1")
        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("1", list[1])
        assertEquals("TWO", list[2])

        list.clear()
        list.addAll(
                "E",
                "E",
                "E",
                "W"
        )

        list.update("E", "X")
        assertEquals(4, list.size)
        assertEquals("X", list[0])
        assertEquals("E", list[1])
        assertEquals("E", list[2])
        assertEquals("W", list[3])

        assertThrows(ElementNotFoundException::class.java, { list.update("NULL", "NEW") })

    }

    @DisplayName("List Update At")
    @Test
    fun testListUpdateAt() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        list.updateAt(1, "1")
        assertEquals(3, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("1", list[1])
        assertEquals("TWO", list[2])

        assertThrows(IndexOutOfBoundsException::class.java, { list.updateAt(6, "NEW") })
    }

    @DisplayName("List Update All to")
    @Test
    fun testListUpdateAllTo() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "A",
                        "A",
                        "A",
                        "B",
                        "C",
                        "D"
                ) as AbstractWaqtiListDummy

        list.updateAllTo(listOf("B", "C", "D"), "A")

        assertEquals(6, list.size)
        list.forEach { assertTrue(it == "A") }

        list.clear().addAll(
                "A",
                "A",
                "A",
                "B",
                "C",
                "D"
        )

        list.updateAllTo(listOf("B", "C", "D", "E", "F"), "A")
        assertEquals(6, list.size)
        list.forEach { assertTrue(it == "A") }

        list.clear().addAll(
                "A",
                "A",
                "A"
        )

        list.updateAllTo(listOf("B", "C", "D", "E", "F"), "X")
        assertEquals(3, list.size)
        list.forEach { assertTrue(it == "A") }
    }

    @DisplayName("List Remove")
    @Test
    fun testListRemove() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        list.remove("ONE")
        assertEquals(2, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("TWO", list[1])

        list.addAll("NEW", "NEW", "NEW")
        list.remove("NEW")
        assertEquals(4, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("TWO", list[1])
        assertEquals("NEW", list[2])
        assertEquals("NEW", list[3])

        assertAll({ list.remove("NULL") })
    }

    @DisplayName("List Remove At")
    @Test
    fun testListRemoveAt() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        list.removeAt(1)
        assertEquals(2, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("TWO", list[1])

        assertThrows(IndexOutOfBoundsException::class.java, { list.removeAt(5) })
    }

    @DisplayName("List Remove All vararg")
    @Test
    fun testListRemoveAllVararg() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        list.removeAll()
        assertEquals(3, list.size)

        list.removeAll("ZERO", "ONE", "TWO")
        assertEquals(0, list.size)
        assertTrue(list.isEmpty())

        assertAll({ list.removeAll("ZERO", "ONE", "NULL") })
        assertAll({ list.removeAll() })
    }

    @DisplayName("List Remove All collection")
    @Test
    fun testListRemoveAllCollection() {
        val list = AbstractWaqtiListDummy()
                .addAll(listOf(
                        "ZERO",
                        "ONE",
                        "TWO")
                ) as AbstractWaqtiListDummy
        list.removeAll(listOf())
        assertEquals(3, list.size)

        list.removeAll(listOf("ZERO", "ONE", "TWO"))
        assertEquals(0, list.size)
        assertTrue(list.isEmpty())

        assertAll({ list.removeAll(listOf("ZERO", "ONE", "NULL")) })
        assertAll({ list.removeAll(listOf()) })
    }

    @DisplayName("List Remove If")
    @Test
    fun testListRemoveIf() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "Box", "Bag", "Car", "Dog", "Bro"
                ) as AbstractWaqtiListDummy

        list.removeIf { it[0] == 'B' }
        assertEquals(2, list.size)
        assertEquals("Car", list[0])
        assertEquals("Dog", list[1])

        assertAll({ list.removeIf { it[0] == 'X' } })
        assertEquals(2, list.size)
    }

    @DisplayName("List Clear")
    @Test
    fun testListClear() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        list.clear()
        assertEquals(0, list.size)
        assertTrue(list.isEmpty())
        assertAll({ list.clear() })
    }

    @DisplayName("List Remove Range")
    @Test
    fun testListRemoveRange() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR"
                ) as AbstractWaqtiListDummy
        list.removeRange(1, 4)
        assertEquals(2, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("FOUR", list[1])

        assertThrows(IndexOutOfBoundsException::class.java, { list.removeRange(5, 7) })

        list.removeRange(0, 0)
        assertEquals(2, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("FOUR", list[1])

        list.clear().addAll(
                "ZERO",
                "ONE",
                "TWO",
                "THREE",
                "FOUR")
        list.removeRange(4, 1)

        assertEquals(2, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])

        assertThrows(IndexOutOfBoundsException::class.java, { list.removeRange(5, 0) })

        assertAll({ list.removeRange(0, 0) })
        assertEquals(2, list.size)
        assertEquals("ZERO", list[0])
        assertEquals("ONE", list[1])
    }

    @DisplayName("List Get All vararg")
    @Test
    fun testListGetAllVararg() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR"
                ) as AbstractWaqtiListDummy

        assertEquals(listOf("ZERO", "TWO", "FOUR"), list.getAll("ZERO", "TWO", "FOUR"))
        assertEquals(5, list.size)

        assertEquals(listOf("ZERO", "FOUR"), list.getAll("ZERO", "FOUR", "FIVE", "NULL"))

        assertEquals(listOf<String>(), list.getAll("NULL"))

        assertEquals(listOf<String>(), list.getAll())
    }

    @DisplayName("List Get All collection")
    @Test
    fun testListGetAllCollection() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR"
                ) as AbstractWaqtiListDummy

        assertEquals(listOf("ZERO", "TWO", "FOUR"), list.getAll(listOf("ZERO", "TWO", "FOUR")))
        assertEquals(5, list.size)

        assertEquals(listOf("ZERO", "FOUR"), list.getAll(listOf("ZERO", "FOUR", "FIVE", "NULL")))

        assertEquals(listOf<String>(), list.getAll(listOf("NULL")))

        assertEquals(listOf<String>(), list.getAll(listOf()))
    }

    @DisplayName("List to List")
    @Test
    fun testListToList() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        assertEquals(listOf("ZERO", "ONE", "TWO"), list.toList())
    }

    @DisplayName("List Contains All vararg")
    @Test
    fun testListContainsAllVararg() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR"
                ) as AbstractWaqtiListDummy
        assertTrue(list.containsAll("ZERO", "ONE", "THREE"))
        assertTrue(list.containsAll("ZERO", "ZERO", "ZERO"))
        assertTrue(list.containsAll())
        assertFalse(list.containsAll("FIVE", "NULL"))
        assertFalse(list.containsAll("NULL"))
    }

    @DisplayName("List Contains All collection")
    @Test
    fun testListContainsAllCollection() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR"
                ) as AbstractWaqtiListDummy
        assertTrue(list.containsAll(listOf("ZERO", "ONE", "THREE")))
        assertTrue(list.containsAll(listOf("ZERO", "ZERO", "ZERO")))
        assertTrue(list.containsAll(listOf()))
        assertFalse(list.containsAll(listOf("FIVE", "NULL")))
        assertFalse(list.containsAll(listOf("NULL")))
    }

    @DisplayName("List Is Empty")
    @Test
    fun testListIsEmpty() {
        val list = AbstractWaqtiListDummy()
        assertTrue(list.isEmpty())
        assertTrue(list.size == 0)
        list.add("ZERO")
        assertFalse(list.isEmpty())
        assertTrue(list.size != 0)
    }

    @DisplayName("List Index Of")
    @Test
    fun testListIndexOf() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        assertEquals(0, list.indexOf("ZERO"))

        assertThrows(ElementNotFoundException::class.java, { list.indexOf("NULL") })

        list + "ZERO" + "ZERO"
        assertEquals(0, list.indexOf("ZERO"))
        assertEquals(3, list.count { it == "ZERO" })
    }

    @DisplayName("List Last Index Of")
    @Test
    fun testListLastIndexOf() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        assertEquals(0, list.lastIndexOf("ZERO"))

        assertThrows(ElementNotFoundException::class.java, { list.lastIndexOf("NULL") })

        list + "ZERO" + "ZERO"
        assertEquals(4, list.lastIndexOf("ZERO"))
        assertEquals(3, list.count { it == "ZERO" })
    }

    @DisplayName("List All Indexes Of")
    @Test
    fun testListAllIndexesOf() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO"
                ) as AbstractWaqtiListDummy
        assertEquals(listOf(0), list.allIndexesOf("ZERO"))
        assertEquals(listOf<Int>(), list.allIndexesOf("NULL"))

        list + "ZERO" + "ZERO"
        assertEquals(3, list.allIndexesOf("ZERO").size)
        assertEquals(listOf(0, 3, 4), list.allIndexesOf("ZERO"))
    }

    @DisplayName("List sublist")
    @Test
    fun testListSubList() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy

        assertEquals(listOf("ZERO", "ONE"), list.subList(0, 2))
        assertEquals(listOf("TWO", "THREE", "FOUR"), list.subList(4, 1))
        assertEquals(listOf<String>(), list.subList(3, 3))

        assertThrows(IndexOutOfBoundsException::class.java, { list.subList(0, 7) })
    }

    @DisplayName("List Move Index")
    @Test
    fun testListMoveIndex() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy

        list.move(0, 3)
        assertEquals(listOf("ONE", "TWO", "THREE", "ZERO", "FOUR", "FIVE"), list.toList())

        list.move(3, 0)
        assertEquals(listOf("ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE"), list.toList())

        list.move(4, 4)
        assertEquals(listOf("ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE"), list.toList())

        assertThrows(IndexOutOfBoundsException::class.java, { list.move(7, 3) })
    }

    @DisplayName("List Move Element")
    @Test
    fun testListMoveElement() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy

        list.move("ZERO", "THREE")
        assertEquals(listOf("ONE", "TWO", "THREE", "ZERO", "FOUR", "FIVE"), list.toList())

        list.move("FOUR", "ONE")
        assertEquals(listOf("FOUR", "ONE", "TWO", "THREE", "ZERO", "FIVE"), list.toList())

        list.move("FOUR", "FOUR")
        assertEquals(listOf("FOUR", "ONE", "TWO", "THREE", "ZERO", "FIVE"), list.toList())

        assertThrows(ElementNotFoundException::class.java, { list.move("SEVEN", "ZERO") })
    }

    @DisplayName("List Swap Index")
    @Test
    fun testListSwapIndex() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy

        list.swap(0, 3)
        assertEquals(listOf("THREE", "ONE", "TWO", "ZERO", "FOUR", "FIVE"), list.toList())

        list.swap(3, 0)
        assertEquals(listOf("ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE"), list.toList())

        list.swap(4, 4)
        assertEquals(listOf("ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE"), list.toList())

        assertThrows(IndexOutOfBoundsException::class.java, { list.swap(7, 1) })
    }

    @DisplayName("List Swap Element")
    @Test
    fun testListSwapElement() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy

        list.swap("ZERO", "THREE")
        assertEquals(listOf("THREE", "ONE", "TWO", "ZERO", "FOUR", "FIVE"), list.toList())

        list.swap("THREE", "ZERO")
        assertEquals(listOf("ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE"), list.toList())

        list.swap("FOUR", "FOUR")
        assertEquals(listOf("ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE"), list.toList())

        assertThrows(ElementNotFoundException::class.java, { list.swap("NULL", "ONE") })
    }

    @DisplayName("List Move All To")
    @Test
    fun testListMoveAllTo() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        list.moveAllTo(listOf("FOUR", "FIVE"), 3)
        assertEquals(listOf("ZERO", "ONE", "TWO", "FOUR", "FIVE", "THREE"), list.toList())

        list.clear().addAll(
                "ZERO",
                "ONE",
                "TWO",
                "THREE",
                "FOUR",
                "FIVE"
        )

        list.moveAllTo(listOf("THREE", "FOUR", "NULL"), 1)
        assertEquals(listOf("ZERO", "THREE", "FOUR", "ONE", "TWO", "FIVE"), list.toList())

        list.moveAllTo(listOf("NULL"), 4)
        assertEquals(listOf("ZERO", "THREE", "FOUR", "ONE", "TWO", "FIVE"), list.toList())

        list.moveAllTo(listOf(), 4)
        assertEquals(listOf("ZERO", "THREE", "FOUR", "ONE", "TWO", "FIVE"), list.toList())

        assertThrows(IndexOutOfBoundsException::class.java, { list.moveAllTo(listOf("ONE"), 7) })
    }

    @DisplayName("List Sort")
    @Test
    fun testListSort() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "C",
                        "A",
                        "F",
                        "E",
                        "D",
                        "B"
                ) as AbstractWaqtiListDummy
        list.sort(Comparator { o1, o2 -> o1.compareTo(o2) })
        assertEquals(listOf("A", "B", "C", "D", "E", "F"), list.toList())
    }

    @DisplayName("List Grow To")
    @Test
    fun testListGrowTo() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        assertEquals(6, list.size)
        assertAll({ list.growTo(-20) })
        assertEquals(6, list.size)
        assertAll({ list.growTo(50000) })
        assertEquals(6, list.size)
    }

    @DisplayName("List Iterator")
    @Test
    fun testListIterator() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        val checkList = arrayListOf("ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE")

        val iterator = list.iterator()
        val checkIterator = checkList.iterator()

        for (i in list.indices) {
            assertTrue(list[i] == checkList[i])
            assertTrue(iterator.next() == checkIterator.next())
        }

    }

    @DisplayName("List Parallel Stream")
    @Test
    fun testListParallelStream() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        assertEquals("ZERO", list.parallelStream().findFirst().get())
    }

    @DisplayName("List Spliterator")
    @Test
    fun testListSpliterator() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        list.spliterator()
    }

    @DisplayName("List Stream")
    @Test
    fun testListStream() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        assertEquals("ZERO", list.stream().findFirst().get())
    }

    @DisplayName("List List Iterator")
    @Test
    fun testListListIterator() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        list.listIterator().add("SIX")
        assertEquals(7, list.size)
    }

    @DisplayName("List List Iterator Indexed")
    @Test
    fun testListListIteratorIndexed() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        list.listIterator(4).add("SIX")
        assertEquals(7, list.size)
        assertEquals("SIX", list[4])
    }

    @DisplayName("List For Each")
    @Test
    fun testListForEach() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "A",
                        "A",
                        "A",
                        "A"
                ) as AbstractWaqtiListDummy
        list.forEach({ assertTrue(it == "A") })
    }

    @DisplayName("List Join")
    @Test
    fun testListJoin() {
        val list = AbstractWaqtiListDummy()
                .addAll(
                        "ZERO",
                        "ONE",
                        "TWO",
                        "THREE",
                        "FOUR",
                        "FIVE"
                ) as AbstractWaqtiListDummy
        assertEquals(list.toList(), list.join(listOf("THIS SHOULD BE OVERRIDDEN")).toList())
    }

    @DisplayName("Test")
    @Test
    fun test() {

    }
}