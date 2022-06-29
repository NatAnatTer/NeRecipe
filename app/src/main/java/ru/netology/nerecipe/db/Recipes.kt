package ru.netology.nerecipe.db

import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.RecipeWithInfo
import ru.netology.nerecipe.dto.Steps

fun RecipeEntity.toModel() = Recipe(
    recipeId = recipeId,
    recipeName = recipeName,
    authorName = authorName,
    categoryId = categoryId,
    isFavorites = isFavorites
)

fun Recipe.toEntity() = RecipeEntity(
    recipeId = recipeId,
    recipeName = recipeName,
    authorName = authorName,
    categoryId = categoryId,
    isFavorites = isFavorites
)

fun StepsEntity.toModel() = Steps(
    stepId = stepId,
    numberOfStep = numberOfStep,
    contentOfStep = contentOfStep,
    recipeId = recipeId,
    imageUrl = imageUrl
)

fun Steps.toEntity() = StepsEntity(
    stepId = stepId,
    numberOfStep = numberOfStep,
    contentOfStep = contentOfStep,
    recipeId = recipeId,
    imageUrl = imageUrl
)


fun CategoryOfRecipeEntity.toModel() = CategoryOfRecipe(
    categoryId = categoryId,
    categoryName = categoryName
)

fun CategoryOfRecipe.toEntity() = CategoryOfRecipeEntity(
    categoryId = categoryId,
    categoryName = categoryName
)

fun RecipeWithInfoEntity.toModel(): RecipeWithInfo {
    val recipeTarget: Recipe = recipe.toModel()
    val categoryTarget: CategoryOfRecipe = category.toModel()
    val stepsTarget: List<Steps> = steps.map { it.toModel() }
    return RecipeWithInfo(
        recipe = recipeTarget,
        category = categoryTarget,
        steps = stepsTarget,
    )
}


