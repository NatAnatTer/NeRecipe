package ru.netology.nerecipe.db

import ru.netology.nerecipe.dto.*

fun RecipeEntity.toModel() = Recipe(
    recipeId = recipeId,
    recipeName = recipeName,
    authorId = authorId,
    categoryId = categoryId

)

fun Recipe.toEntity() = RecipeEntity(
    recipeId = recipeId,
    recipeName = recipeName,
    authorId = authorId,
    categoryId = categoryId
)

fun UserEntity.toModel() = User(
    userId = userId,
    userName = userName

)

fun User.toEntity() = UserEntity(
    userId = userId,
    userName = userName
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

fun FavoritesEntity.toModel() = Favorites(
    recipeId = recipeId,
    userId = userId

)

fun Favorites.toEntity() = FavoritesEntity(
    recipeId = recipeId,
    userId = userId
)

fun CategoryOfRecipeEntity.toModel() = CategoryOfRecipe(
    categoryId = categoryId,
    categoryName = categoryName

)

fun CategoryOfRecipe.toEntity() = CategoryOfRecipeEntity(
    categoryId = categoryId,
    categoryName = categoryName
)