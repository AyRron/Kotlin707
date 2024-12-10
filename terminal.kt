//import java.io.IOException
//
//fun openInteractiveTerminal() {
//    try {
//        // Commande pour ouvrir un terminal et exécuter un programme interactif
//        val command = arrayOf(
//            "cmd.exe", "/c", "start cmd.exe /k java -jar path_to_your_program.jar"
//        )
//
//        // Crée le processus
//        val process = ProcessBuilder(*command).start()
//        process.waitFor() // Optionnel : attendre la fin du processus, mais ici ce n'est pas nécessaire
//    } catch (e: IOException) {
//        e.printStackTrace()
//        println("Erreur : Impossible d'ouvrir le terminal.")
//    }
//}
//
//fun main() {
//    openInteractiveTerminal()
//}
