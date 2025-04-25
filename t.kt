un main() = runBlocking {
    val channel = Channel<Int>() // Création d'un Channel

    // Producteur
    launch {
        for (i in 1..5) {
            println("Envoi : $i")
            channel.send(i) // Envoi de données dans le channel
        }
        channel.close() // Fermeture du channel
    }

    // Consommateur
    for (value in channel) { // Réception des données
        println("Reçu : $value")
    }

    println("Fin du programme")
}