package machine
class CoffeeMaker {
    private enum class IngredientsForCoffeeCup(
        val nameCoffee: String,
        val water: Int,
        val milk: Int,
        val beans: Int,
        val cups: Int,
        val invertedPrice: Int
    ) {
        ESPRESSO("espresso", 250, 0, 16, 1, -4),
        LATTE("latte", 350, 75, 20, 1, -7),
        CAPPUCCINO("cappuccino", 200, 100, 12, 1, -6);
        val requiredIngredients = mutableListOf(water, milk, beans, cups, invertedPrice)
    }

    private enum class Ingredients(val nameOfIngredient: String, var quantity: Int, val measure: String) {
        WATER("water", 0, " ml"),
        MILK("milk", 0, " ml"),
        COFFEE_BEANS("coffee beans", 0, " grams"),
        CUPS("disposable cups", 0, ""),
        MONEY("money", 0, "")
    }

    companion object {
        fun setStartIngredients(water: Int, milk: Int, beans: Int, cups: Int, money: Int) {
            val startIngredients = mutableListOf(water, milk, beans, cups, money)
            for (ingredients in Ingredients.values()) {
                ingredients.quantity = startIngredients[ingredients.ordinal]
            }
        }

        fun startMenu() {
            println("Write action (buy, fill, take, remaining, exit):")
            when (readln().toString()) {
                "buy" -> buy()
                "fill" -> fill()
                "take" -> take()
                "remaining" -> remaining()
                "exit" -> return
                else -> println("incorrect action!")
            }
            startMenu()
        }

        private fun buy() {
            println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
            when (readln()) {
                "1" -> makeCoffee(IngredientsForCoffeeCup.ESPRESSO)
                "2" -> makeCoffee(IngredientsForCoffeeCup.LATTE)
                "3" -> makeCoffee(IngredientsForCoffeeCup.CAPPUCCINO)
                "back" -> return
                else -> println("incorrect input")

            }
        }

        private fun makeCoffee(coffeeCup: IngredientsForCoffeeCup) {
            val notEnoughMessage = checkIngredients(coffeeCup)
            if (notEnoughMessage == "") {
                for (ingredients in Ingredients.values()) {
                    ingredients.quantity -= coffeeCup.requiredIngredients[ingredients.ordinal]
                }
                println("I have enough resources, making you a coffee!")
            }
            else println("Sorry, not enough $notEnoughMessage")
        }

        private fun checkIngredients(coffeeCup: IngredientsForCoffeeCup): String {
            var message = ""
            for (ingredients in Ingredients.values()) {
                if (ingredients.quantity < coffeeCup.requiredIngredients[ingredients.ordinal]) {
                    message += "${ingredients.nameOfIngredient}, "
                }
            }
            return message.removeSuffix(", ")
        }

        private fun fill() {
            for (ingredients in Ingredients.values()) {
                if (ingredients.nameOfIngredient != "money") {
                    println("Write how many${ingredients.measure} of ${ingredients.nameOfIngredient} you want to add:")
                    ingredients.quantity += readln().toInt()
                }
            }
        }

        private fun take() {
            println("I gave you $${Ingredients.MONEY.quantity}")
            Ingredients.MONEY.quantity = 0
        }

        private fun remaining() {
            println("The coffee machine has:")
            for (ingredients in Ingredients.values()) {
                println("${ingredients.quantity}${ingredients.measure} of ${ingredients.nameOfIngredient}")
            }
        }
    }
}

fun main() {
    CoffeeMaker.setStartIngredients(400, 540, 120, 9, 550)
    CoffeeMaker.startMenu()
}

