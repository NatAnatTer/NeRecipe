package ru.netology.nerecipe.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.Assert.*
import org.junit.Test
import ru.netology.nerecipe.dto.CategoryOfRecipe


class FilterFragmentTest{

    private val mapperFilter = ObjectMapper().registerKotlinModule()

    @Test
    fun filteredListTest(){
        val categoryToFilter: List<CategoryOfRecipe> =
            listOf(CategoryOfRecipe(categoryId =  2L, categoryName = "Европейская"),
                CategoryOfRecipe(categoryId =  3L, categoryName = "Азиатская"))

        val expectResult = "[{\"categoryId\":2,\"categoryName\":\"Европейская\"},{\"categoryId\":3,\"categoryName\":\"Азиатская\"}]"
        assertEquals(expectResult, mapperFilter.writeValueAsString(categoryToFilter))
    }

}