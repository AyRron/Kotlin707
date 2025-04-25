//
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//import java.util.Scannera
//
//
//data class User(
//    val id: String,
//    val name: String,
//    val role: String,
//    val authorizedBuildings: Set<String>
//)
//
//data class Building(
//    val id: String,
//    val name: String,
//    val accessLog: MutableList<String> = mutableListOf(),
//    var isEmergency: Boolean = falsea
//) {
//    val door = Door(this)
//}
//
//object Database {
//    val users = mutableMapOf<String, User>()
//    val buildings = mutableMapOf<String, Building>()
//}
//
//object CommandRoom {
//    fun issueBadge(user: User) {
//        Database.users[user.id] = user
//        println("Badge issued to ${user.name} with ID: ${user.id}")
//    }
//
//    fun deactivateBadge(userId: String) {
//        Database.users.remove(userId)
//        println("Badge $userId deactivated.")
//    }
//
//    fun listPresentUsers(buildingId: String) {
//        val building = Database.buildings[buildingId]
//        if (building != null) {
//            println("Access log for building ${building.name}:")
//            building.accessLog.forEach { println(it) }
//        } else {
//            println("Building not found!")
//        }
//    }
//}
//
//object AccessControlSystem {
//    fun swipeBadge(userId: String, buildingId: String): Boolean {
//        val user = Database.users[userId]
//        val building = Database.buildings[buildingId]
//
//        if (user == null) {
//            println("Badge not recognized.")
//            simulateRedLight()
//            return false
//        }
//
//        if (building == null) {
//            println("Building not found.")
//            simulateRedLight()
//            return false
//        }
//
//        val accessGranted = building.door.swipeBadge(user)
//        if (accessGranted) {
//            simulateGreenLight()
//        } else {
//            simulateRedLight()
//        }
//        return accessGranted
//    }
//
//    fun simulateExit(userId: String, buildingId: String) {
//        val building = Database.buildings[buildingId]
//        val user = Database.users[userId]
//
//        if (building != null && user != null) {
//            building.accessLog.add("Exit: ${user.name} at ${System.currentTimeMillis()}")
//            println("${user.name} exited building ${building.name}.")
//        }
//    }
//
//    fun triggerEmergency(buildingId: String) {
//        val building = Database.buildings[buildingId]
//        if (building != null) {
//            building.isEmergency = true
//            building.door.unlock()
//            println("Emergency in building ${building.name}! Doors unlocked.")
//            println("List of present users:")
//            building.accessLog.forEach { println(it) }
//        }
//    }
//
//    private fun simulateGreenLight() {
//        println("Green light ON for 5 seconds.")
//    }
//
//    private fun simulateRedLight() {
//        println("Red light ON for 10 seconds.")
//    }
//}
//
//
//fun main() = runBlocking {
//    val scanner = Scanner(System.`in`)
//
//    // Initialize buildings
//    initializeBuildings()
//
//    // Start door processes
//    Database.buildings.values.forEach { it.door.process() }
//
//    println("Access control system initialized.")
//    var running = true
//
//    while (running) {
//        println("\n--- Main Menu ---")
//        println("1. Add a user")
//        println("2. Deactivate a badge")
//        println("3. Simulate an entry")
//        println("4. Simulate an exit")
//        println("5. List users in a building")
//        println("6. Trigger an emergency")
//        println("0. Quit")
//        print("Enter your choice: ")
//
//        when (scanner.nextLine()) {
//            "1" -> launch { addUser(scanner) }
//            "2" -> launch { deactivateBadge(scanner) }
//            "3" -> launch { simulateEntry(scanner) }
//            "4" -> launch { simulateExit(scanner) }
//            "5" -> launch { listUsersInBuilding(scanner) }
//            "6" -> launch { triggerEmergency(scanner) }
//            "0" -> {
//                println("Shutting down the system...")
//                running = false
//            }
//            else -> println("Invalid choice, please try again.")
//        }
//    }
//}
//
//// Initialisation des bâtiments
//fun initializeBuildings() {
//    Database.buildings["B1"] = Building("B1", "Biology Lab")
//    Database.buildings["B2"] = Building("B2", "Physics Lab")
//    Database.buildings["B3"] = Building("B3", "Chemistry Lab")
//    Database.buildings["B4"] = Building("B4", "Computer Science Lab")
//    Database.buildings["B5"] = Building("B5", "Mechanical Workshop")
//    println("5 bâtiments initialisés :")
//    Database.buildings.values.forEach { println("- ${it.name} (ID: ${it.id})") }
//}
//
//// Générateur d'ID unique
//object IdGenerator {
//    private var counter = 0
//    fun generateId(): String {
//        counter++
//        return "U$counter"
//    }
//}
//
//fun addUser(scanner: Scanner) {
//    println("Ajout d'un nouvel utilisateur.")
//    val id = IdGenerator.generateId()
//    print("Entrez le nom de l'utilisateur : ")
//    val name = scanner.nextLine()
//    print("Entrez le rôle de l'utilisateur (Teacher/Student/Staff) : ")
//    val role = scanner.nextLine()
//    print("Entrez les IDs des bâtiments auxquels l'utilisateur a accès (séparés par des espaces) : ")
//    val buildingAccess = scanner.nextLine().split(" ").toSet()
//
//    val user = User(id, name, role, buildingAccess)
//    CommandRoom.issueBadge(user)
//    println("Utilisateur ajouté avec succès ! ID généré : $id")
//}
//
//fun deactivateBadge(scanner: Scanner) {
//    print("Entrez l'ID du badge à désactiver : ")
//    val id = scanner.nextLine()
//    CommandRoom.deactivateBadge(id)
//}
//
//fun simulateEntry(scanner: Scanner) {
//    print("Entrez l'ID de l'utilisateur : ")
//    val userId = scanner.nextLine()
//    print("Entrez l'ID du bâtiment : ")
//    val buildingId = scanner.nextLine()
//    AccessControlSystem.swipeBadge(userId, buildingId)
//}
//
//fun simulateExit(scanner: Scanner) {
//    print("Entrez l'ID de l'utilisateur : ")
//    val userId = scanner.nextLine()
//    print("Entrez l'ID du bâtiment : ")
//    val buildingId = scanner.nextLine()
//    AccessControlSystem.simulateExit(userId, buildingId)
//}
//
//fun listUsersInBuilding(scanner: Scanner) {
//    print("Entrez l'ID du bâtiment : ")
//    val buildingId = scanner.nextLine()
//    CommandRoom.listPresentUsers(buildingId)
//}
//
//fun triggerEmergency(scanner: Scanner) {
//    print("Entrez l'ID du bâtiment : ")
//    val buildingId = scanner.nextLine()
//    AccessControlSystem.triggerEmergency(buildingId)
//}





//fun main() = runBlocking {
//    val channel = Channel<Int>() // Création d'un Channel
//
//    // Producteur
//    launch {
//        for (i in 1..5) {
//            println("Envoi : $i")
//            channel.send(i) // Envoi de données dans le channel
//        }
//        channel.close() // Fermeture du channel
//    }
//
//    // Consommateur
//    for (value in channel) { // Réception des données
//        println("Reçu : $value")
//    }
//
//    println("Fin du programme")
//}
