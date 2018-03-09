package uk.whitecrescent.waqti.tests

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.whitecrescent.waqti.code.Checklist
import uk.whitecrescent.waqti.code.ListItem

@DisplayName("Properties Tests")
@Nested
class PropertiesTests {

    //region Checklist

    @DisplayName("Checklist")
    @Test
    fun testChecklist() {
        val checkList = Checklist("Zero", "One", "Two", "Three", "Four")
        Assertions.assertEquals(5, checkList.size())

        checkList.addItem("Five")
        Assertions.assertEquals(6, checkList.size())

        checkList.checkItem(5)
        Assertions.assertTrue(checkList[5].isChecked)
        Assertions.assertEquals(6, checkList.size())

        checkList.deleteItem(5)
        Assertions.assertEquals(5, checkList.size())

        checkList.addItem(ListItem("FiveAgain", true))
        Assertions.assertEquals(6, checkList.size())

        checkList.uncheckItem(5)
        Assertions.assertFalse(checkList[5].isChecked)

    }

    //endregion

}