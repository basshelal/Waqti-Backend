package uk.whitecrescent.waqti.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Checklist
import uk.whitecrescent.waqti.code.ListItem

class OtherTests {

    @DisplayName("Test")
    @Test
    fun test() {

    }

    @DisplayName("Checklist")
    @Test
    fun testChecklist() {
        val checkList = Checklist("Zero", "One", "Two", "Three", "Four")
        assertEquals(5, checkList.size())

        checkList.addItem("Five")
        assertEquals(6, checkList.size())

        checkList.checkItem(5)
        assertTrue(checkList[5].isChecked)
        assertEquals(6, checkList.size())

        checkList.deleteItem(5)
        assertEquals(5, checkList.size())

        checkList.addItem(ListItem("FiveAgain", true))
        assertEquals(6, checkList.size())

        checkList.uncheckItem(5)
        assertFalse(checkList[5].isChecked)

    }
    
}
