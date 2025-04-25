import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    // Création d'un MutableSharedFlow
    val sharedFlow = MutableSharedFlow<Int>()

    // Premier collecteur
    launch {
        sharedFlow.collect { value ->
            println("Collecteur 1 : $value")
        }
    }

    // Émission de valeurs dans le SharedFlow
    launch {
        for (i in 1..3) {
            println("Émission : $i")
            sharedFlow.emit(i) // Envoi d'une valeur dans le SharedFlow
            delay(100) // Pause pour simuler un délai
        }
    }

    delay(500) // Laisser le temps aux collecteurs de recevoir les valeurs
}



