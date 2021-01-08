package day21
typealias Allergen = String
typealias Ingredient = String

data class Food(val ingredients : Set<Ingredient>, val allergens: List<Allergen>) {
    fun hasAllergen(a : String) = allergens.contains(a)
    
    companion object {
        fun toFood(s : String) : Food {
            val (ingredients, allergens) = s.replace(")","").split(" (contains ")
            return Food(ingredients.split(" ").toSet(), allergens.split(", "))
        }
    }
}

fun part1(foods : List<Food>) : Int {
    val ingredients = foods.flatMap { it.ingredients }
    val possibles = possibles(foods)
    val notAllergens = ingredients.subtract(possibles.values.flatten())
    return foods.sumBy { notAllergens.intersect(it.ingredients).size }
}

private fun possibles(foods: List<Food>): Map<Allergen, Set<Ingredient>> {
    return foods.flatMap { it.allergens }.associateWith { a ->
        foods.filter { it.hasAllergen(a) }.map { it.ingredients }.reduce { i1, i2 -> i1.intersect(i2) }
    }
}

fun part2(foods: List<Food>): String =
    findDangerousIngredients(possibles(foods), emptyMap())
        .entries
        .sortedBy { it.key }.joinToString(separator = ",") { it.value }

fun findDangerousIngredients(possibles: Map<Allergen, Set<Ingredient>>, state : State ) : State {
    return when (possibles.size) {
        0 -> state
        else -> possibles.entries.find { it.value.size ==1 }?.let { found ->
            val foundIngredient = found.value.first()
            val newPossibles = possibles.filter { it != found }
                .map { Pair(it.key, it.value.filter { it != foundIngredient }.toSet()) }.toMap()
            findDangerousIngredients(newPossibles, state + Pair(found.key, foundIngredient))
        }!!
    }
}

typealias State = Map<Allergen, Ingredient>