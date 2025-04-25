//import kotlinx.coroutines.*
//import java.util.Scanner
//
//class Door(val building: Building) {
//    private var isUnlocked = false
//
//    fun swipeBadge(user: User): Boolean {
//        return if (user.authorizedBuildings.contains(building.id)) {
//            println("Access granted for ${user.name} to building ${building.name}.")
//            building.accessLog.add("Entry: ${user.name} at ${System.currentTimeMillis()}")
//            unlock()
//            true
//        } else {
//            println("Access denied for ${user.name}.")
//            false
//        }
//    }
//
//    fun unlock() {
//        isUnlocked = true
//        println("Door to ${building.name} unlocked.")
//        GlobalScope.launch {
//            delay(30000) // Door remains unlocked for 30 seconds
//            lock()
//        }
//    }
//
//    fun lock() {
//        isUnlocked = false
//        println("Door to ${building.name} locked.")
//    }
//
//    fun process() = GlobalScope.launch {
//        while (true) {
//            delay(1000) // Simulate continuous door process
//            // Additional door logic can be added here
//        }
//    }
//}