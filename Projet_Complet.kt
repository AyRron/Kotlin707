import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

data class User(
    val id: String,
    val name: String,
    val role: String,
    val authorizedBuildings: Set<String>
)

data class Building(
    val id: String,
    val name: String,
    val accessLog: MutableList<String> = mutableListOf(),
    var isEmergency: Boolean = false
)

object Database {
    val users = mutableMapOf<String, User>()
    val buildings = mutableMapOf<String, Building>()
}

object CommandRoom {
    fun issueBadge(user: User) {
        Database.users[user.id] = user
        println("Badge issued to ${user.name} with ID: ${user.id}")
    }

    fun deactivateBadge(userId: String) {
        Database.users.remove(userId)
        println("Badge $userId deactivated.")
    }

    fun listPresentUsers(buildingId: String) {
        val building = Database.buildings[buildingId]
        if (building != null) {
            println("Access log for building ${building.name}:")
            building.accessLog.forEach { println(it) }
        } else {
            println("Building not found!")
        }
    }
}

object AccessControlSystem {
    fun swipeBadge(userId: String, buildingId: String): Boolean {
        val user = Database.users[userId]
        val building = Database.buildings[buildingId]

        if (user == null) {
            println("Badge not recognized.")
            return false
        }

        if (building == null || !user.authorizedBuildings.contains(buildingId)) {
            println("Access denied for ${user.name}.")
            return false
        }

        println("Access granted for ${user.name} to building ${building.name}.")
        building.accessLog.add("Entry: ${user.name} at ${System.currentTimeMillis()}")
        return true
    }

    fun simulateExit(userId: String, buildingId: String) {
        val building = Database.buildings[buildingId]
        val user = Database.users[userId]

        if (building != null && user != null) {
            building.accessLog.add("Exit: ${user.name} at ${System.currentTimeMillis()}")
            println("${user.name} exited building ${building.name}.")
        }
    }

    fun triggerEmergency(buildingId: String) {
        val building = Database.buildings[buildingId]
        if (building != null) {
            building.isEmergency = true
            println("Emergency in building ${building.name}! Doors unlocked.")
            println("List of present users:")
            building.accessLog.forEach { println(it) }
        }
    }
}

fun main() {
    val serverSocket = ServerSocket(9999)
    println("Server is running and waiting for clients to connect on port 9999...")

    // Initialize buildings for testing
    initializeBuildings()

    while (true) {
        val clientSocket = serverSocket.accept()
        println("Client connected: ${clientSocket.inetAddress}")

        // Handle the client connection in a separate thread
        Thread {
            handleClient(clientSocket)
        }.start()
    }
}

fun handleClient(clientSocket: Socket) {
    val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
    val output = PrintWriter(clientSocket.getOutputStream(), true)

    output.println("Welcome to the Access Control System! Type 'exit' to disconnect.")

    var running = true
    while (running) {
        output.println("\n--- Menu ---")
        output.println("1. Add a user")
        output.println("2. Deactivate a badge")
        output.println("3. Simulate an entry")
        output.println("4. Simulate an exit")
        output.println("5. List users in a building")
        output.println("6. Trigger an emergency")
        output.println("0. Quit")
        output.print("Enter your choice: ")
        output.flush()

        val choice = input.readLine()

        when (choice) {
            "1" -> addUser(input, output)
            "2" -> deactivateBadge(input, output)
            "3" -> simulateEntry(input, output)
            "4" -> simulateExit(input, output)
            "5" -> listUsersInBuilding(input, output)
            "6" -> triggerEmergency(input, output)
            "0" -> {
                output.println("Exiting...")
                running = false
            }
            else -> output.println("Invalid choice, please try again.")
        }
    }

    clientSocket.close()
}

fun addUser(input: BufferedReader, output: PrintWriter) {
    val id = IdGenerator.generateId()
    output.println("Enter user name: ")
    val name = input.readLine()
    output.println("Enter user role (Teacher/Student/Staff): ")
    val role = input.readLine()
    output.println("Enter building IDs the user has access to (separate with space): ")
    val buildingAccess = input.readLine().split(" ").toSet()

    val user = User(id, name, role, buildingAccess)
    CommandRoom.issueBadge(user)
    output.println("User added with ID: $id")
}

fun deactivateBadge(input: BufferedReader, output: PrintWriter) {
    output.print("Enter the ID of the badge to deactivate: ")
    val id = input.readLine()
    CommandRoom.deactivateBadge(id)
}

fun simulateEntry(input: BufferedReader, output: PrintWriter) {
    output.print("Enter user ID: ")
    val userId = input.readLine()
    output.print("Enter building ID: ")
    val buildingId = input.readLine()
    AccessControlSystem.swipeBadge(userId, buildingId)
}

fun simulateExit(input: BufferedReader, output: PrintWriter) {
    output.print("Enter user ID: ")
    val userId = input.readLine()
    output.print("Enter building ID: ")
    val buildingId = input.readLine()
    AccessControlSystem.simulateExit(userId, buildingId)
}

fun listUsersInBuilding(input: BufferedReader, output: PrintWriter) {
    output.print("Enter building ID: ")
    val buildingId = input.readLine()
    CommandRoom.listPresentUsers(buildingId)
}

fun triggerEmergency(input: BufferedReader, output: PrintWriter) {
    output.print("Enter building ID: ")
    val buildingId = input.readLine()
    AccessControlSystem.triggerEmergency(buildingId)
}

// Initialize buildings for the example
fun initializeBuildings() {
    Database.buildings["B1"] = Building("B1", "Biology Lab")
    Database.buildings["B2"] = Building("B2", "Physics Lab")
    Database.buildings["B3"] = Building("B3", "Chemistry Lab")
    Database.buildings["B4"] = Building("B4", "Computer Science Lab")
    Database.buildings["B5"] = Building("B5", "Mechanical Workshop")
}

// ID generator for users
object IdGenerator {
    private var counter = 0
    fun generateId(): String {
        counter++
        return "U$counter"
    }
}
